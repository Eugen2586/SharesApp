package com.example.sharesapp.ui.historie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import java.util.ArrayList;

public class HistorieFragment extends Fragment {

    private HistorieViewModel historieViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historieViewModel =
                ViewModelProviders.of(this).get(HistorieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_historie, container, false);
        View listView = root.findViewById(R.id.receiptlist);
        final TextView cash = root.findViewById(R.id.cash);
        final TextView umsatz = root.findViewById(R.id.sum);
        final TextView aktienwert = root.findViewById(R.id.aktienwert);
        //Fill the Cash you have at the Moment.
        cash.setText(String.valueOf(new Model().getData().getGewinn()));

        float akwert = 0;
        if(new Model().getData().getDepot().getAktien() != null) {
            for (Aktie a : new Model().getData().getDepot().getAktien()) {
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
}