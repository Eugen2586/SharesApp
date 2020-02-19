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

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;

import java.util.ArrayList;

public class UebersichtFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    View root;
    StockRecyclerViewAdapter adapter = null;
    RecyclerView recyclerView = null;

    Data d;
    TextView cash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Model model = new Model();
        root = inflater.inflate(R.layout.fragment_depot_uebersicht, container, false);

        d = new Model().getDaten();
        cash = root.findViewById(R.id.stock_value_text);
        cash.setText(String.valueOf(d.getDepot().getGeldwert()) + "€");

        final Observer<ArrayList<Aktie>> aktienObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                setAdapter(aktienList);
            }
        };

        model.getDaten().getAktienList().observe(getViewLifecycleOwner(), aktienObserver);

        setAdapter(model.getDaten().getAktienList().getValue());

        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        //todo bind to aktien
        Navigation.findNavController(view).navigate(R.id.aktienDetailsFragment);
    }

    public void initRecyclerView() {
        recyclerView = root.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(ArrayList<Aktie> aktienList) {
        if (recyclerView == null) {
            initRecyclerView();
        }
        if (aktienList != null) {
            if (adapter == null) {
                adapter = new StockRecyclerViewAdapter(UebersichtFragment.this.getContext(), aktienList);
                adapter.setClickListener(UebersichtFragment.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setAktien(aktienList);
            }
        }
    }
}
