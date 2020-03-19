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

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
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
    TextView notEmptyTextView;
    TextView emptyTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_depot_uebersicht, container, false);

        notEmptyTextView = root.findViewById(R.id.not_empty_depot_text_view);
        emptyTextView = root.findViewById(R.id.empty_depot_text_view);

        Data data = new Model().getData();
        String wert = (new Anzeige()).makeItBeautiful(data.getDepot().getGeldwert());
        TextView cash = root.findViewById(R.id.stock_value_text);
        cash.setText((wert + "€"));

        final Observer<ArrayList<Trade>> observer = new Observer<ArrayList<Trade>>() {
            @Override
            public void onChanged(ArrayList<Trade> tradesList) {
                setAdapter(tradesList);
            }
        };

        data.getTradesMutable().observe(getViewLifecycleOwner(), observer);
        setAdapter(data.getTradesMutable().getValue());

        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        //todo bind to aktien
        Navigation.findNavController(view).navigate(R.id.aktienDetailsFragment);
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    //to bind the uebersicht und aktien from tradelist

    private void setAdapter(ArrayList<Trade> tradesList) {
        if (recyclerView == null) {
            initRecyclerView();
        }
        if (tradesList != null) {
            ArrayList<Aktie> trades = new ArrayList<>();
            for (Trade t : tradesList) {
                trades.add(t.getAktie());
            }
            if (adapter == null) {
                adapter = new StockRecyclerViewAdapter(UebersichtFragment.this.getContext(), trades);
                adapter.setClickListener(UebersichtFragment.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setAktien(trades);
            }
        }
        if (tradesList == null || tradesList.size() == 0) {
            notEmptyTextView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            notEmptyTextView.setVisibility(View.VISIBLE);
        }
    }
}
