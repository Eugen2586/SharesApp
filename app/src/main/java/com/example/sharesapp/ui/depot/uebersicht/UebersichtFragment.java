package com.example.sharesapp.ui.depot.uebersicht;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Range;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class UebersichtFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    private View root;
    private StockRecyclerViewAdapter adapter = null;
    private RecyclerView recyclerView = null;
    private Model model = new Model();
    private TextView notEmptyTextView;
    private TextView emptyTextView;
    private TextView stockValueTextView;
    private TextView overallValueTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_depot_uebersicht, container, false);

        initializeSwipeRefresh();

        notEmptyTextView = root.findViewById(R.id.not_empty_depot_text_view);
        emptyTextView = root.findViewById(R.id.empty_depot_text_view);
        stockValueTextView = root.findViewById(R.id.stock_value_text);
        overallValueTextView = root.findViewById(R.id.overall_value_text);

        String cashValue = (new Anzeige()).makeItBeautiful(model.getData().getDepot().getGeldwert());
        TextView cashValueTextView = root.findViewById(R.id.cash_value_text);
        cashValueTextView.setText((cashValue + "€"));

        final Observer<ArrayList<Aktie>> observer = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> depotList) {
                setStockAndOverallValue();
                setAdapter(depotList);
            }
        };
        model.getData().getDepot().getAktienImDepot().observe(getViewLifecycleOwner(), observer);

        setStockAndOverallValue();
        setAdapter(model.getData().getDepot().getAktienImDepot().getValue());

        sendRequestsForDepot();

        return root;
    }

    private void initializeSwipeRefresh() {
        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                sendRequestsForDepot();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void sendRequestsForDepot() {
        Model model = new Model();
        ArrayList<Aktie> depotList = model.getData().getDepot().getAktienImDepot().getValue();
        Requests requests = new Requests();
        if (depotList != null) {
            for (Aktie stock: depotList) {
                try {
                    requests.asyncRun(RequestsBuilder.getQuote(stock.getSymbol()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        // opens stock details
        TextView symbolView = view.findViewById(R.id.stock_symbol_text);
        String symbol = (String) symbolView.getText();
        Aktie stock = new Aktie();
        stock.setSymbol(symbol);
        model.getData().setCurrentStock(stock);
        Requests requests = new Requests();
        try {
            requests.asyncRun(RequestsBuilder.getQuote(symbol));
            requests.asyncRun(RequestsBuilder.getHistoricalQuotePrices(symbol, Range.oneMonth));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Navigation.findNavController(view).navigate(R.id.aktienDetailsFragment);
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    //to bind the uebersicht und aktien from depotlist
    private void setAdapter(ArrayList<Aktie> depotList) {
        initRecyclerView();
        if (depotList != null) {
            if (adapter == null) {
                adapter = new StockRecyclerViewAdapter(UebersichtFragment.this.getContext(), depotList);
                adapter.setClickListener(UebersichtFragment.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setAktien(depotList);
            }
        }
        if (depotList == null || depotList.size() == 0) {
            notEmptyTextView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            notEmptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void setStockAndOverallValue() {
        String stockValue = (new Anzeige()).makeItBeautiful(model.getData().getDepot().calculateStockValue()) + "€";
        stockValueTextView.setText(stockValue);

        String overallValue = (new Anzeige()).makeItBeautiful(model.getData().getDepot().calculateStockValue() + model.getData().getDepot().getGeldwert()) + "€";
        overallValueTextView.setText(overallValue);
    }
}
