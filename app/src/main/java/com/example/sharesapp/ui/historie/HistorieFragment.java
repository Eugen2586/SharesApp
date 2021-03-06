package com.example.sharesapp.ui.historie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.ui.utils.TradeRecyleViewAdapter;

import java.util.ArrayList;

/**
 * Klasse, mit der die Historie der Käufe und Verkäufe dargestellt wird.
 */
public class HistorieFragment extends Fragment {
    private RecyclerView recyclerView;
    private TradeRecyleViewAdapter adapter;
    private View root = null;

    /**
     * currentMoney, stockValue and profit wird gesetzt.
     * Observer für tradeList wird initalisiert.
     *
     * @param inflater           Inflator des history_fragments.
     * @param container          Der Container.
     * @param savedInstanceState Nicht verwendet.
     * @return Root.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistorieViewModel historieViewModel = ViewModelProviders.of(this).get(HistorieViewModel.class);
        root = inflater.inflate(R.layout.fragment_historie, container, false);
        Model model = new Model();
        setAdapter(new Model().getData().getTrades());
        final TextView cash = root.findViewById(R.id.cash);
        final TextView umsatz = root.findViewById(R.id.sum);
        final TextView aktienwert = root.findViewById(R.id.aktienwert);
        //Fill the Cash you have at the Moment.
        cash.setText(String.valueOf(new Anzeige().makeItBeautifulEuro(new Model().getData().getDepot().getGeldwert())));

        float stockValue = model.getData().getDepot().calculateStockValue();
        aktienwert.setText(new Anzeige().makeItBeautifulEuro(stockValue));
        umsatz.setText(new Anzeige().makeItBeautifulEuro(stockValue + new Model().getData().getDepot().getGeldwert()));


        historieViewModel.mTrades.observe(getViewLifecycleOwner(), new Observer<ArrayList<Trade>>() {
            @Override
            public void onChanged(ArrayList<Trade> trades) {
                setAdapter(trades);
            }
        });

        return root;
    }

    /**
     * recyclerView wird mit Lister der Handel gefüllt.
     *
     * @param tradesList Lister der Handel.
     */
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
                adapter = new TradeRecyleViewAdapter(HistorieFragment.this.getContext(), trades);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setTrades(new Model().getData().getTrades());
            }
        }
    }

    /**
     * Initalisiert den recyclerView
     */
    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.receiptlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }
}