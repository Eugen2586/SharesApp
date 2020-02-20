package com.example.sharesapp.ui.aktien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.ui.depot.uebersicht.UebersichtFragment;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class AktienFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener{

    private AktienViewModel aktienViewModel;
//    private String[] typeList = {"crypto", "oil", "us-market", "etfs", "bonds", "funding", "fx", "capital-raise"};

    RecyclerView recyclerView = null;
    View root;
    StockRecyclerViewAdapter adapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_aktien, container, false);
        Model model = new Model();

        final Observer<ArrayList<Aktie>> observer = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                setAdapter(aktienList);
            }
        };

        model.getDaten().getAktienList().observe(getViewLifecycleOwner(), observer);
        setAdapter(model.getDaten().getAktienList().getValue());

        TabLayout tabs = root.findViewById(R.id.category_tab_layout);

        if (tabs != null) {
            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    changeCategory(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } else {
            System.out.println("TABS NULL");
        }

        return root;
    }

    private void changeCategory(int position) {
        switch(position) {
            case 0:

        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //todo bind to aktien
        Navigation.findNavController(view).navigate(R.id.aktienDetailsFragment);
    }

    public void initRecyclerView() {
        recyclerView = root.findViewById(R.id.aktien_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(ArrayList<Aktie> aktienList) {
        System.out.println("Called setAdapter");
        if (recyclerView == null) {
            initRecyclerView();
        }
        if (aktienList != null) {
            if (adapter == null) {
                adapter = new StockRecyclerViewAdapter(AktienFragment.this.getContext(), aktienList);
                adapter.setClickListener(AktienFragment.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setAktien(aktienList);
            }
        }
    }
}