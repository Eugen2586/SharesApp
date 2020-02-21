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

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.ui.depot.uebersicht.UebersichtFragment;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;
import com.example.sharesapp.ui.utils.TradeRecyleViewAdapter;

import java.util.ArrayList;

public class HistorieFragment extends Fragment {
    private RecyclerView recyclerView;
    private TradeRecyleViewAdapter adapter;
    private HistorieViewModel historieViewModel;
    private View root = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historieViewModel =
                ViewModelProviders.of(this).get(HistorieViewModel.class);
        root = inflater.inflate(R.layout.fragment_historie, container, false);
        setAdapter(new Model().getData().getTrades());
        final TextView cash = root.findViewById(R.id.cash);
        final TextView umsatz = root.findViewById(R.id.sum);
        final TextView aktienwert = root.findViewById(R.id.aktienwert);
        //Fill the Cash you have at the Moment.
        cash.setText(String.valueOf(new Model().getData().getGewinn()));

        float akwert = 0;
        if(new Model().getData().getDepot().getAktien() != null) {
            for (Aktie a : new Model().getData().getDepot().getAktien().getValue()) {
                akwert += a.getPreis() * a.getAnzahl();
            }
        }
        aktienwert.setText(String.valueOf(akwert));
        String l_s = String.valueOf(akwert + new Model().getData().getGewinn());
        umsatz.setText( l_s );


        historieViewModel.mTrades.observe(getViewLifecycleOwner(), new Observer<ArrayList<Trade>>() {
            @Override
            public void onChanged(ArrayList<Trade> trades) {

            }
        });

        return root;
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.receiptlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    //to bind the uebersicht und aktien from tradelist

    private void setAdapter(ArrayList<Trade> tradesList) {
        System.out.println("Called setAdapter");
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
//                adapter.setClickListener((TradeRecyleViewAdapter.ItemClickListener) HistorieFragment.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setTrades(new Model().getData().getTrades());
            }
        }
    }
}