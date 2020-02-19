package com.example.sharesapp.ui.depot.uebersicht;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import java.util.ArrayList;

public class UebersichtFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    View root;
    StockRecyclerViewAdapter adapter = null;
    RecyclerView recyclerView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Model model = new Model();
        root = inflater.inflate(R.layout.fragment_depot_uebersicht, container, false);

        final Observer<ArrayList<Aktie>> aktienObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                System.out.println("CHANGED: " + aktienList);
                System.out.println("RECYLERVIEW: " + recyclerView);
                if (recyclerView == null) {
                    initRecyclerview();
                }
                if (adapter == null) {
                    adapter = new StockRecyclerViewAdapter(UebersichtFragment.this.getContext(), aktienList);
                    adapter.setClickListener(UebersichtFragment.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.setAktien(aktienList);
                }
            }
        };

        // set up the RecyclerView
        if (recyclerView == null) {
            initRecyclerview();
        }

        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        //todo bind to aktien
        Navigation.findNavController(view).navigate(R.id.aktienDetailsFragment);
    }

    public void initRecyclerview() {
        recyclerView = root.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }
}
