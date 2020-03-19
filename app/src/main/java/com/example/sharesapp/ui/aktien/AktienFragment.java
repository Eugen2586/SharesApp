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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AktienFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    private Model model = new Model();
    private RecyclerView recyclerView = null;
    private View root;
    private TextView emptyPortfolioTextView;
    private String[] previousAvailableTypes = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_aktien, container, false);
        TabLayout tabLayout = root.findViewById(R.id.category_tab_layout);
        emptyPortfolioTextView = root.findViewById(R.id.empty_portfolio_text);

        addTabs(tabLayout);

        final TabLayout finalTabLayout = tabLayout;

        final Observer<ArrayList<Aktie>> listObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                addTabs(finalTabLayout);
                setCategory(finalTabLayout.getSelectedTabPosition());
            }
        };

        final Observer<Integer> resetObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                addTabs(finalTabLayout);
                setCategory(finalTabLayout.getSelectedTabPosition());
            }
        };

        model.getData().getAktienList().observe(getViewLifecycleOwner(), listObserver);
        model.getData().getResetCounter().observe(getViewLifecycleOwner(), resetObserver);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                model.getData().setPreviouslySelectedTabIndex(tab.getPosition());
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

    @Override
    public void onResume() {
        previousAvailableTypes = null;
        super.onResume();
    }

    private void addTabs(TabLayout tabLayout) {
        String[] availableTypes = model.getData().getAvailType().getAvailableTypes();

        if(previousAvailableTypes != null) {
            Arrays.sort(availableTypes);
            Arrays.sort(previousAvailableTypes);
        }
        if (previousAvailableTypes == null || !Arrays.equals(availableTypes, previousAvailableTypes)) {
            // remove all Tabs
            tabLayout.removeAllTabs();

            // add Portfolio and Alles Tab
            addTabWithString(tabLayout, "portfolio");
            addTabWithString(tabLayout, "alles");

            // add Tabs for existing StockTypes
            if (availableTypes != null) {
                System.out.println("insert Types");
                for (String category : availableTypes) {
                    addTabWithString(tabLayout, category);
                }
            }
            previousAvailableTypes = availableTypes;

            selectPreviouslySelectedTab(tabLayout);
        }
    }

    private void addTabWithString(TabLayout tabLayout, String text) {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText(text);
        tabLayout.addTab(tab);
    }

    private void setCategory(int position) {
        ArrayList<Aktie> stockList = model.getData().getAktienList().getValue();
        if (position == 0) {
            ArrayList<Aktie> portfolioList = model.getData().getPortfolioList().getValue();
            if (portfolioList == null || portfolioList.size() == 0) {
                setAdapter(new ArrayList<Aktie>());
                emptyPortfolioTextView.setVisibility(View.VISIBLE);
            } else {
                setAdapter(portfolioList);
            }
        } else if (position == 1) {
            setAdapter(stockList);
        } else {
            position -= 2;
            if (position < 0) {
                position = model.getData().getPreviouslySelectedTabIndex();
            }

            if (stockList != null) {
                String type = model.getData().getAvailType().getAvailableTypeAbbreviations()[position];
                ArrayList<Aktie> filtered_aktien = new ArrayList<>();
                for (Aktie stock : stockList) {
                    if (stock.getType().equals(type)) {
                        filtered_aktien.add(stock);
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
        emptyPortfolioTextView.setVisibility(View.GONE);
        if (aktienList == null) {
            aktienList = new ArrayList<>();
        }
        initRecyclerView();
        StockRecyclerViewAdapter adapter = new StockRecyclerViewAdapter(AktienFragment.this.getContext(), aktienList);
        adapter.setClickListener(AktienFragment.this);
        adapter.setAktien(aktienList);
        recyclerView.setAdapter(adapter);
    }

    private void selectPreviouslySelectedTab(TabLayout tabLayout) {
        System.out.println(model.getData().getPreviouslySelectedTabIndex());
        tabLayout.selectTab(tabLayout.getTabAt(model.getData().getPreviouslySelectedTabIndex()));
    }
}