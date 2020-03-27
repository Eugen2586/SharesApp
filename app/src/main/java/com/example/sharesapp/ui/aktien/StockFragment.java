package com.example.sharesapp.ui.aktien;

import android.os.Build;
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
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Enables the user to switch between his portfolio, Kryptowährungen, Aktien
 * Aktien is also divided into smaller categories
 * lists of stocks for the respective category are shown
 */
public class StockFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    private Model model = new Model();
    private RecyclerView recyclerView = null;
    private View root;
    private TextView emptyPortfolioTextView;
    private ArrayList<String> previousAvailableTypes = null;
    private TabLayout tabLayout;
    private int selectedTabsCounter = 0;
    private int numberOfTabs = 3;

    /**
     * observerInitialization is called
     * the tabLayout is initialized
     *
     * @param inflater           inflates the history_fragment
     * @param container          needed for the inflation
     * @param savedInstanceState not needed
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_aktien, container, false);
        tabLayout = root.findViewById(R.id.category_tab_layout);
        emptyPortfolioTextView = root.findViewById(R.id.empty_portfolio_text);

        initializeAllObservers();

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

    /**
     * initializes observers for stockList, resetCounter and depotStocks
     */
    private void initializeAllObservers() {
        final Observer<ArrayList<Aktie>> listObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                addTabsAndStocksToCurrentlySelectedCategory(tabLayout);
            }
        };
        model.getData().getAktienList().observe(getViewLifecycleOwner(), listObserver);

        final Observer<Integer> resetObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                addTabsAndStocksToCurrentlySelectedCategory(tabLayout);
            }
        };
        model.getData().getResetCounter().observe(getViewLifecycleOwner(), resetObserver);

        final Observer<ArrayList<Aktie>> depotObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> depotList) {
                setCategory(tabLayout.getSelectedTabPosition());
            }
        };
        model.getData().getDepot().getAktienImDepot().observe(getViewLifecycleOwner(), depotObserver);
    }

    /**
     * reinitialize tabs if needed
     * scroll to saved tab and position
     */
    @Override
    public void onResume() {
        previousAvailableTypes = null;
        addTabsAndStocksToCurrentlySelectedCategory(tabLayout);
        super.onResume();
        scrollToCategoryScrollState(tabLayout.getSelectedTabPosition());
        scrollToSelectedTabAfterLayout();
    }

    /**
     * save current tab and scrollPosition
     */
    @Override
    public void onPause() {
        super.onPause();
        saveCategoryScrollState(tabLayout.getSelectedTabPosition());
    }

    /**
     * reinitialize tabs if needed
     * select saved tab and fill the recyclerView
     *
     * @param tabLayout tabLayout for categories
     */
    private void addTabsAndStocksToCurrentlySelectedCategory(TabLayout tabLayout) {
        String[] availableTypes = model.getData().getAvailType().getAvailableTypes();
        if (availableTypes != null) {
            addTabs(tabLayout, availableTypes);
            selectPreviouslySelectedTab(tabLayout);
            setCategory(tabLayout.getSelectedTabPosition());
        }
    }

    /**
     * refills tabLayout with tabs if the availableTypes have changed
     * first three tabs: portfolio, Kryptowährungen, Aktien
     *
     * @param tabLayout      tabLayout for categories
     * @param availableTypes new availableTypes
     */
    private void addTabs(TabLayout tabLayout, String[] availableTypes) {
        boolean differentCategories = false;
        if (previousAvailableTypes != null) {
            for (String str : availableTypes) {
                if (!previousAvailableTypes.contains(str)) {
                    differentCategories = true;
                }
            }
        }
        if (previousAvailableTypes == null || differentCategories) {
            previousAvailableTypes = new ArrayList<>(Arrays.asList(availableTypes));
            selectedTabsCounter = 0;

            // remove all Tabs
            tabLayout.removeAllTabs();

            // add Portfolio and Aktien Tab
            addTabWithString(tabLayout, "portfolio");
            addTabWithString(tabLayout, "Kryptowährungen");
            addTabWithString(tabLayout, "Aktien");

            // add Tabs for existing StockTypes
            for (String category : availableTypes) {
                addTabWithString(tabLayout, category);
            }

            numberOfTabs = availableTypes.length + 3;
        }
    }

    /**
     * adds a tab with text text to tabLayout
     *
     * @param tabLayout tabLayout which is to be filled with tabs
     * @param text      text for the tab
     */
    private void addTabWithString(TabLayout tabLayout, String text) {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText(text);
        tabLayout.addTab(tab);
    }

    /**
     * calls setAdapter with different stockLists depending on the selected tabPosition position
     * if portfolio (0) was selected : setAdapter with portfolioList
     * if Kryptowährungen (1) was selected : setAdapter with stockList without type != crypto
     * if Aktien (2) was selected : setAdapter with stockList without type == crypto
     * if specificCategory (>=3) was selected : setAdapter with stockList with specificCategory only
     *
     * @param position the selected tabPosition
     */
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
            if (stockList != null) {
                String type = "crypto";
                ArrayList<Aktie> filteredStockList = new ArrayList<>();
                for (Aktie stock : stockList) {
                    if (stock.getType().equals(type)) {
                        filteredStockList.add(stock);
                    }
                }
                setAdapter(filteredStockList);
            }
        } else if (position == 2) {
            if (stockList != null) {
                String type = "crypto";
                ArrayList<Aktie> filteredStockList = new ArrayList<>();
                for (Aktie stock : stockList) {
                    if (!stock.getType().equals(type)) {
                        filteredStockList.add(stock);
                    }
                }
                setAdapter(filteredStockList);
            }
        } else {
            position -= 3;
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

    /**
     * from StockRecycleViewAdapter implemented
     * sends Quote and chart Requests
     * opens StockDetailView and sets currentStock
     *
     * @param view     view of one row_stock_item
     * @param position not needed
     */
    @Override
    public void onItemClick(View view, int position) {
        // opens stock details
        TextView symbolView = view.findViewById(R.id.stock_symbol_text);
        String symbol = (String) symbolView.getText();

        Aktie stock = new Aktie();
        stock.setSymbol(symbol);
        stock.setType(model.getData().findTypeOfSymbol(symbol));

        model.getData().setCurrentStock(stock);

        Requests.quoteAndPriceRequest(stock);

        Navigation.findNavController(view).navigate(R.id.aktienDetailsFragment);
    }

    /**
     * initialize the recyclerView
     */
    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.aktien_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * fills the recyclerView with the filtered stockList
     * if the SDK is high enough : sort the stockList
     *
     * @param stockList stockList which is to be shown in the recyclerView
     */
    private void setAdapter(ArrayList<Aktie> stockList) {
        emptyPortfolioTextView.setVisibility(View.GONE);
        if (stockList == null) {
            stockList = new ArrayList<>();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stockList.sort(new Comparator<Aktie>() {
                @Override
                public int compare(Aktie o1, Aktie o2) {
                    return o1.getSymbol().compareTo(o2.getSymbol());
                }
            });
        }
        initRecyclerView();
        StockRecyclerViewAdapter adapter = new StockRecyclerViewAdapter(StockFragment.this.getContext(), stockList);
        adapter.setClickListener(StockFragment.this);
        adapter.setAktien(stockList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * scrolls to the selectedTab after the layout is finished, so that the animation can be seen
     * the user is also scrolled on the tabLayout
     */
    private void scrollToSelectedTabAfterLayout() {
        if (getView() != null) {
            final ViewTreeObserver observer = tabLayout.getViewTreeObserver();

            if (observer.isAlive()) {
                observer.dispatchOnGlobalLayout();
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

    /**
     * select on tabLayout the saved tabPosition
     *
     * @param tabLayout tabLayout for categories
     */
    private void selectPreviouslySelectedTab(TabLayout tabLayout) {
        tabLayout.selectTab(tabLayout.getTabAt(model.getData().getPreviouslySelectedTabIndex()));
    }

    /**
     * saves the scrollState for the currentTabPosition tabPosition
     *
     * @param tabPosition position for which the current scrollState needs to be saved
     */
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
        if (categoryScrollPositions != null && scrollState != -1) {
            categoryScrollPositions.set(tabPosition, scrollState);
        }
    }

    /**
     * scroll on recyclerView to loaded scrollPosition of tabPosition
     *
     * @param tabPosition currently selected tab
     */
    private void scrollToCategoryScrollState(int tabPosition) {
        int scrollState = 0;
        ArrayList<Integer> categoryScrollPositions = model.getData().getCategoryScrollPositions();
        if (categoryScrollPositions != null && tabPosition < categoryScrollPositions.size()) {
            scrollState = categoryScrollPositions.get(tabPosition);
        }
        recyclerView.scrollToPosition(scrollState);
    }
}