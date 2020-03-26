package com.example.sharesapp.ui.depot.uebersicht;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Range;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class OverviewFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

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

        // initialize components
        notEmptyTextView = root.findViewById(R.id.not_empty_depot_text_view);
        emptyTextView = root.findViewById(R.id.empty_depot_text_view);
        stockValueTextView = root.findViewById(R.id.stock_value_text);
        overallValueTextView = root.findViewById(R.id.overall_value_text);

        initializeSwipeRefresh();

        initializeDepotObserver();

        setValueFields();
        setAdapter(model.getData().getDepot().getAktienImDepot().getValue());

        sendRequestsForDepot();

        if (new Model().getData().getDepot().getSchwierigkeitsgrad() == -1) {
            showDifficultyDialog(inflater);
        }
        if (new Model().getData().getDepot().getSchwierigkeitsgrad() == 0) {
            new Model().getData().getDepot().setSchwierigkeitsgrad(-1);
        }

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

    private void initializeDepotObserver() {
        final Observer<ArrayList<Aktie>> observer = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> depotList) {
                setValueFields();
                setAdapter(depotList);
            }
        };
        model.getData().getDepot().getAktienImDepot().observe(getViewLifecycleOwner(), observer);
    }

    private void setValueFields() {
        String cashValue = (new Anzeige()).makeItBeautiful(model.getData().getDepot().getGeldwert());
        TextView cashValueTextView = root.findViewById(R.id.cash_value_text);
        cashValueTextView.setText((cashValue + "€"));

        String stockValue = (new Anzeige()).makeItBeautiful(model.getData().getDepot().calculateStockValue()) + "€";
        stockValueTextView.setText(stockValue);

        String overallValue = (new Anzeige()).makeItBeautiful(model.getData().getDepot().calculateStockValue() + model.getData().getDepot().getGeldwert()) + "€";
        overallValueTextView.setText(overallValue);
    }

    private void sendRequestsForDepot() {
        Model model = new Model();
        ArrayList<Aktie> depotList = model.getData().getDepot().getAktienImDepot().getValue();
        Requests requests = new Requests();
        if (depotList != null) {
            for (Aktie stock: depotList) {
                Requests.quoteRequest(stock);
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
        stock.setType(model.getData().findTypeOfSymbol(symbol));
        model.getData().setCurrentStock(stock);
        Requests.quoteAndPriceRequest(stock);
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
                adapter = new StockRecyclerViewAdapter(OverviewFragment.this.getContext(), depotList);
                adapter.setClickListener(OverviewFragment.this);
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



    private void showDifficultyDialog(LayoutInflater inflater) {

        final Context context = OverviewFragment.this.getContext();
        if (context != null) {
            final View diffLevelDialog = inflater.inflate(R.layout.difficulty_level_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setPositiveButton("Fortfahren",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            model.getData().getDepot().applySchwierigkeitsgrad(true);
                            String cashValue = (new Anzeige()).makeItBeautiful(model.getData().getDepot().getGeldwert());
                            TextView cashValueTextView = root.findViewById(R.id.cash_value_text);
                            cashValueTextView.setText((cashValue + "€"));
                            overallValueTextView = root.findViewById(R.id.overall_value_text);
                            String overallValue = (new Anzeige()).makeItBeautiful(model.getData().getDepot().calculateStockValue() + model.getData().getDepot().getGeldwert()) + "€";
                            overallValueTextView.setText(overallValue);
                            View view = getView();
                            if (view != null) {
                                Navigation.findNavController(view).navigateUp(); } }});
            builder.setCancelable(false);
            builder.setView(diffLevelDialog);
            final AlertDialog dialog = builder.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            Button einfach = diffLevelDialog.findViewById(R.id.b_einfach);
            Button normal = diffLevelDialog.findViewById(R.id.b_normal);
            Button schwer = diffLevelDialog.findViewById(R.id.b_schwer);
            Button challenge = diffLevelDialog.findViewById(R.id.b_challenge);

            einfach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(1);
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(1)[1]);
                }
            });

            normal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(2);
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(2)[1]);
                }
            });

            schwer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(3);
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(3)[1]);
                }
            });

            challenge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Model().getData().getDepot().setSchwierigkeitsgrad(4);
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    TextView b = diffLevelDialog.findViewById(R.id.t_beschreibung);
                    b.setText(new Model().getData().getDepot().getSchwierigkeitsgrad(4)[1]);
                }
            });
        }
    }
}
