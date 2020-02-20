package com.example.sharesapp.ui.aktien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;

public class AktienFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    private Model model = new Model();
    private RecyclerView recyclerView = null;
    private View root;
    private StockRecyclerViewAdapter adapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_aktien, container, false);
        TabLayout tabLayout = root.findViewById(R.id.category_tab_layout);

        addTabs(tabLayout);

        final TabLayout finalTabLayout = tabLayout;

        final Observer<ArrayList<Aktie>> listObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                addTabs(finalTabLayout);
                setCategory(finalTabLayout.getSelectedTabPosition());
            }
        };

        model.getData().getAktienList().observe(getViewLifecycleOwner(), listObserver);

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

        return root;
    }

    private void addTabs(TabLayout tabLayout) {
        for (String category : Constants.TYPE_LIST) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(category);
            tabLayout.addTab(tab);
        }
        // remove all Tabs
        tabLayout.removeAllTabs();

        // add Portfolio Tab
        addTabWithString(tabLayout, "portfolio");

        // add Tabs for existing StockTypes
        String[] availableTypes = model.getData().getAvailType().getType_list();
        for (String category : availableTypes) {
            addTabWithString(tabLayout, category);
        }
    }

    private void addTabWithString(TabLayout tabLayout, String text) {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText(text);
        tabLayout.addTab(tab);
    }

    private void setCategory(int position) {
        if (position == 0) {
            System.out.println("Portfolio einfügen");
        } else {
            position--;

            ArrayList<Aktie> aktien = model.getData().getAktienList().getValue();
            if (aktien != null) {
                String type = model.getData().getAvailType().getType_abbr_list()[position];
                ArrayList<Aktie> filtered_aktien = new ArrayList<>();
                for (Aktie aktie : aktien) {
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
        // opens stock details
        TextView symbolView = view.findViewById(R.id.stock_symbol_text);
        String symbol = (String) symbolView.getText();
        Aktie stock = new Aktie();
        stock.setSymbol(symbol);
        model.getData().setCurrentStock(stock);
        Requests requests = new Requests();
        try {
            requests.asyncRun(RequestsBuilder.getQuote(symbol));
        } catch (IOException e) {
            e.printStackTrace();
        }
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