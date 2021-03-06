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
 * Diese Klasse ermöglicht es dem Benutzer zwischen seinem Portfolio, Kryptowährungen und verschiedenen Aktien zu wechseln.
 * Hierbei sind die Aktien auch noch weiter unterteilt in die Typen der einzelnen Aktien.
 * Für die Wahl werden dem Benutzer die Aktien dieses Typs aufgelistet.
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
     * Observer und das tabLayout werden initialisiert.
     * @param inflater Layoutinflater
     * @param container der Container
     * @param savedInstanceState wird nicht benötigt
     * @return nicht benötigt
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
     * initialisiert Observer für aktienListe, resetCounter and depotAktien
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
     * Tabs werden bei Bedarf neu initialisiert.
     * Es wird zu den abgespeicherten Scroll und Tabpositionen gescrollt.
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
     * Speichert die derzeitige Tabposition und Scrollposition des currentTabs
     */
    @Override
    public void onPause() {
        super.onPause();
        saveCategoryScrollState(tabLayout.getSelectedTabPosition());
    }

    /**
     * Tabs werden bei Bedarf neu initialisiert.
     * Selektiere abgespeicherten Tab und fülle die RecyclerView.
     * @param tabLayout tabLayout für Kategorien
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
     * Setzt die Tabs des tabLayouts wenn neue verfügbare Typen hinzugekommen sind.
     * Die ersten drei Tabs sind portfolio, Kryptowährungen und Aktien.
     * @param tabLayout tabLayout für Kategorien
     * @param availableTypes neue verfügbar Kategorien
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
     * Fügt einen Tab mit dem String text zum tabLayout hinzu.
     * @param tabLayout tabLayout für Kategorien
     * @param text text für den Tab
     */
    private void addTabWithString(TabLayout tabLayout, String text) {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText(text);
        tabLayout.addTab(tab);
    }

    /**
     * Ruft setAdapter mit unterschiedlichen Aktienlisten auf, abhängig vom selektierten Tab.
     * wenn portfolio (0) ausgewählt wurde : setAdapter mit portfolioList
     * wenn Kryptowährungen (1) ausgewählt wurde : setAdapter mit stockList welche den Typ crypto haben
     * wenn Aktien (2) ausgewählt wurde : setAdapter mit stockList welche nicht den Typ crypto haben
     * wenn specificCategory (>=3) ausgewählt wurde : setAdapter mit stockList mit der spezifischen Kategorie als Typ
     * @param position selektierte Tabposition
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
        } else if( position == 1) {
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
     * Vom StockRecycleViewAdapter implementiert
     * Sendet Quote und Chart Requests, setzt den currentStock und öffnet die AktienDetailansicht
     * @param view View eines row_stock_items
     * @param position position des row_stock_items
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
     * Initialisiert den recyclerView.
     */
    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.aktien_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * Füllt den RecyclerView mit den gefilterten Aktien.
     * Wenn die SDK hoch genug ist, sortiere zusätzlich die Aktienliste.
     * @param stockList gefilterte Aktienliste
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
     * Scrollt zu dem selektierten Tab wenn das Layout geladen wurde.
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
     * Selektiere auf dem tabLayout den abgespeicherten Tab.
     * @param tabLayout tabLayout für Kategorien
     */
    private void selectPreviouslySelectedTab(TabLayout tabLayout) {
        tabLayout.selectTab(tabLayout.getTabAt(model.getData().getPreviouslySelectedTabIndex()));
    }

    /**
     * Speichert die Scrollstelle für die derzeitig selektierte tabPosition
     * @param tabPosition position des selektierten Tabs
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
     * Scrolle auf der RecyclerView zu der gespeicherten ScrollPosition.
     * @param tabPosition position des derzeitig selektierten Tabs
     */
    private void scrollToCategoryScrollState(int tabPosition) {
        int scrollState = 0;
        ArrayList<Integer> categoryScrollPositions = model.getData().getCategoryScrollPositions();
        if (categoryScrollPositions != null && tabPosition < categoryScrollPositions.size()) {
            scrollState = categoryScrollPositions.get(tabPosition);
        }
        if (recyclerView == null) {
            initRecyclerView();
        }
        recyclerView.scrollToPosition(scrollState);
    }
}