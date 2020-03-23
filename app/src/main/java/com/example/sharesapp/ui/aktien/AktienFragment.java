package com.example.sharesapp.ui.aktien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import java.util.Arrays;

public class AktienFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    private Model model = new Model();
    private RecyclerView recyclerView = null;
    private View root;
    private TextView emptyPortfolioTextView;
    private ArrayList<String> previousAvailableTypes = null;
    private TabLayout tabLayout;
    private int selectedTabsCounter = 0;
    private int numberOfTabs = 2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_aktien, container, false);
        tabLayout = root.findViewById(R.id.category_tab_layout);
        emptyPortfolioTextView = root.findViewById(R.id.empty_portfolio_text);

        final TabLayout finalTabLayout = tabLayout;

        final Observer<ArrayList<Aktie>> listObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                addTabsAndStocksToCurrentlySelectedCategory(finalTabLayout);
            }
        };

        final Observer<Integer> resetObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                addTabsAndStocksToCurrentlySelectedCategory(finalTabLayout);
            }
        };

        final Observer<ArrayList<Aktie>> depotObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> depotList) {
                setCategory(0);
            }
        };

        model.getData().getAktienList().observe(getViewLifecycleOwner(), listObserver);
        model.getData().getResetCounter().observe(getViewLifecycleOwner(), resetObserver);
        model.getData().getDepot().getAktienImDepot().observe(getViewLifecycleOwner(), depotObserver);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (selectedTabsCounter != 0) {
                    model.getData().setPreviouslySelectedTabIndex(tabLayout.getSelectedTabPosition());
                }
                setCategory(tab.getPosition());
                scrollToCategoryScrollState(tab.getPosition());
                selectedTabsCounter++;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                saveCategoryScrollState(tab.getPosition());
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
        addTabsAndStocksToCurrentlySelectedCategory(tabLayout);
        super.onResume();
        scrollToCategoryScrollState(tabLayout.getSelectedTabPosition());
        scrollToSelectedTabAfterLayout();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveCategoryScrollState(tabLayout.getSelectedTabPosition());
    }

    private void addTabsAndStocksToCurrentlySelectedCategory(TabLayout tabLayout) {
        String[] availableTypes = model.getData().getAvailType().getAvailableTypes();
        if (availableTypes != null) {
            addTabs(tabLayout, availableTypes);
            selectPreviouslySelectedTab(tabLayout);
            setCategory(tabLayout.getSelectedTabPosition());
        }
    }

    private void addTabs(TabLayout tabLayout, String[] availableTypes) {
        boolean differentCategories = false;
        if (previousAvailableTypes != null) {
            for (String str : availableTypes) {
                if (!previousAvailableTypes.contains(str)) {
                    for (int i = 0; i < previousAvailableTypes.size(); i++) {
                        System.out.println(previousAvailableTypes.get(i));
                    }
                    System.out.println(str);
                    differentCategories = true;
                }
            }
        }
        if (previousAvailableTypes == null || differentCategories) {
            previousAvailableTypes = new ArrayList<>(Arrays.asList(availableTypes));
            selectedTabsCounter = 0;

            // remove all Tabs
            tabLayout.removeAllTabs();

            // add Portfolio and Alles Tab
            addTabWithString(tabLayout, "portfolio");
            addTabWithString(tabLayout, "alles");

            // add Tabs for existing StockTypes
            for (String category : availableTypes) {
                addTabWithString(tabLayout, category);
            }

            numberOfTabs = availableTypes.length + 2;
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
                position = 0;
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

    private void scrollToSelectedTabAfterLayout() {
        if (getView() != null) {
            final ViewTreeObserver observer = tabLayout.getViewTreeObserver();

            if (observer.isAlive()) {
                observer.dispatchOnGlobalLayout(); // In case a previous call is waiting when this call is made
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        observer.removeOnGlobalLayoutListener(this);
                        selectPreviouslySelectedTab(tabLayout);
                    }
                });
            }
        }
    }

    private void selectPreviouslySelectedTab(TabLayout tabLayout) {
        tabLayout.selectTab(tabLayout.getTabAt(model.getData().getPreviouslySelectedTabIndex()));
    }

    private void saveCategoryScrollState(int tabPosition) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int scrollState = 0;
        if (linearLayoutManager != null) {
            scrollState = linearLayoutManager.findFirstVisibleItemPosition();
        }
        ArrayList<Integer> categoryScrollPositions = model.getData().getCategoryScrollPositions();
        if (categoryScrollPositions == null || categoryScrollPositions.size() < numberOfTabs) {
            model.getData().createCategoryScrollPositions(numberOfTabs);
        }
        if (scrollState != -1) {
            categoryScrollPositions.set(tabPosition, scrollState);
        }
    }

    private void scrollToCategoryScrollState(int tabPosition) {
        int scrollState = 0;
        ArrayList<Integer> categoryScrollPositions = model.getData().getCategoryScrollPositions();
        if (categoryScrollPositions != null && tabPosition < categoryScrollPositions.size()) {
            scrollState = categoryScrollPositions.get(tabPosition);
        }
        recyclerView.scrollToPosition(scrollState);
    }
}