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

import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.ui.depot.uebersicht.UebersichtFragment;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class AktienFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener{

    private AktienViewModel aktienViewModel;

    RecyclerView recyclerView = null;
    View root;
    StockRecyclerViewAdapter adapter = null;
    Model model = new Model();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_aktien, container, false);
        TabLayout tabLayout = root.findViewById(R.id.category_tab_layout);

        addTabs(tabLayout);

        final TabLayout finalTabLayout = tabLayout;

        final Observer<ArrayList<Aktie>> observer = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                setCategory(finalTabLayout.getSelectedTabPosition());
            }
        };

        model.getDaten().getAktienList().observe(getViewLifecycleOwner(), observer);

        if (tabLayout != null) {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    setCategory(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        return root;
    }

    private void addTabs(TabLayout tabLayout) {
        for (String category: Constants.TYPE_LIST) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(category);
            tabLayout.addTab(tab);
        }
    }

    private void setCategory(int position) {
        if (position == 0) {
            // TODO: portfolio einfügen
            System.out.println("Portfolio einfügen");
        } else {
            position--;

            ArrayList<Aktie> aktien = model.getDaten().getAktienList().getValue();
            if (aktien != null) {
                String type = Constants.TYPE_ABBRE_LIST[position];
                ArrayList<Aktie> filtered_aktien = new ArrayList<>();
                for (Aktie aktie: aktien) {
                    if (aktie.getType().equals(type)) {
                        filtered_aktien.add(aktie);
                    }
                }

                setAdapter(filtered_aktien);
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //todo bind to aktien
        Navigation.findNavController(view).navigate(R.id.aktienDetailsFragment);
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.aktien_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter(ArrayList<Aktie> aktienList) {
        System.out.println("Called setAdapter " + aktienList);
        if (aktienList != null) {
            initRecyclerView();
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