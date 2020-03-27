package com.example.sharesapp.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Klasse, die das Search Fragment verwaltet.
 * Lässt den User die Suchfunktion benutzen.
 */
public class SearchFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    private Model model = new Model();
    private RecyclerView recyclerView = null;
    private View root;
    private TextView noSearchText;
    private TextView searchText;

    /**
     * Initalisiert Spinner mit categoryChanged Funktion.
     * Setzt observer für searchesFromServer
     * Setzt adapter für Recyclerview.
     *
     * @param inflater           Der Inflater.
     * @param container          Der Container.
     * @param savedInstanceState Nicht verwendet.
     * @return Root.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        noSearchText = root.findViewById(R.id.no_search_text);
        searchText = root.findViewById(R.id.search_text);

        Spinner spinner = root.findViewById(R.id.spinner);
        initCategorySpinner(spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changedSpinnerPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setAdapter(model.getData().getSearches());
            }
        });

        model.getData().searches.observe(getViewLifecycleOwner(), new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> searchRequests) {
                setAdapter(searchRequests);
            }
        });
        setAdapter(model.getData().getSearches());

        return root;
    }

    /**
     * Wenn Server (0) ausgewählt : setAdapter mit serverSearchList.
     * Wenn Kryptowährungen (1) ausgewählt : setAdapter mit symbol filtered list der cryptoStocks.
     * Wenn Aktien (2) ausgewählt : setAdapter mit symbol filtered list der nonCryptoStocks.
     *
     * @param position Die Position zu dem der spinner gewechselt wird.
     */
    private void changedSpinnerPosition(int position) {
        if (position == 0) {
            ArrayList<Aktie> searchList = model.getData().getSearches();
            if (searchList != null) {
                addSearchesToStockList(searchList);

                ArrayList<Aktie> existingStockList = model.getData().getAktienList().getValue();
                ArrayList<Aktie> referencedStockList = new ArrayList<>();
                if (existingStockList != null) {
                    for (Aktie stock : searchList) {
                        for (Aktie existingStock : existingStockList) {
                            if (stock.getSymbol().equals(existingStock.getSymbol())) {
                                referencedStockList.add(existingStock);
                                break;
                            }
                        }
                    }
                }

                setAdapter(referencedStockList);
            }
        } else if (position == 1) {
            ArrayList<Aktie> stockList = model.getData().getAktienList().getValue();
            if (stockList != null) {
                ArrayList<Aktie> cryptoList = new ArrayList<>();
                for (Aktie stock : stockList) {
                    if (stock.isCrypto()) {
                        cryptoList.add(stock);
                    }
                }
                setAdapter(getSearchFilteredStockList(cryptoList));
            }
        } else {
            ArrayList<Aktie> stockList = model.getData().getAktienList().getValue();
            if (stockList != null) {
                ArrayList<Aktie> notCryptoList = new ArrayList<>();
                for (Aktie stock : stockList) {
                    if (!stock.isCrypto()) {
                        notCryptoList.add(stock);
                    }
                }
                setAdapter(getSearchFilteredStockList(notCryptoList));
            }
        }
    }

    /**
     * Server, Kryptowährungen and Aktien werden als Komponenten des Spinners gesetzt.
     *
     * @param spinner spinner der initalisiert werden soll.
     */
    private void initCategorySpinner(Spinner spinner) {
        Context context = this.getContext();
        if (context != null) {
            String[] availableTypes = new String[3];
            availableTypes[0] = "Server";
            availableTypes[1] = "Kryptowährungen";
            availableTypes[2] = Constants.TYPES[0];

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                    android.R.layout.simple_spinner_dropdown_item, availableTypes);
            spinner.setAdapter(adapter);
        }
    }

    /**
     * Initalisiert Recyclerview.
     */
    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.search_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * recyclerview wird gefüllt mit filteredList
     * Ruft die showHideComponents Funktion auf.
     *
     * @param filteredList Liste mit aktien, die Recyclerview befüllen soll.
     */
    private void setAdapter(ArrayList<Aktie> filteredList) {
        if (filteredList != null) {
            initRecyclerView();
            StockRecyclerViewAdapter adapter = new StockRecyclerViewAdapter(SearchFragment.this.getContext(), filteredList);
            adapter.setClickListener(SearchFragment.this);
            adapter.setAktien(filteredList);
            recyclerView.setAdapter(adapter);
        }

        showHideComponents(filteredList);
    }

    /**
     * searchlist wird in stocklist integriert.
     *
     * @param searchList Suchergebnisse, die integriert werden sollen.
     */
    private void addSearchesToStockList(ArrayList<Aktie> searchList) {
        if (searchList != null) {
            // add stocks to existingStockList if not yet included
            ArrayList<Aktie> existingStockList = model.getData().getAktienList().getValue();
            ArrayList<Aktie> stocksToAdd = new ArrayList<>();
            if (existingStockList != null) {
                for (Aktie stock : searchList) {
                    String symbol = stock.getSymbol();
                    boolean found = false;
                    for (Aktie existingStock : existingStockList) {
                        if (symbol.equals(existingStock.getSymbol())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        stocksToAdd.add(stock);
                    }
                }
                model.getData().addAktienList(stocksToAdd);
            } else {
                model.getData().addAktienList(searchList);
            }
        }
    }

    /**
     * Sendet Quote and chart Requests.
     * Öffnet StockDetailView und setzt currentStock.
     *
     * @param view     View eines row_stock_item.
     * @param position Die Position.
     */
    @Override
    public void onItemClick(View view, int position) {
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
     * Zeigt oder versteckt die Komponenten.
     * @param stockList Aktienliste, die im RecyclerView angezeigt werden soll.
     */
    private void showHideComponents(ArrayList<Aktie> stockList) {
        if (stockList == null || stockList.size() == 0) {
            noSearchText.setVisibility(View.VISIBLE);
            searchText.setVisibility(View.GONE);
            if (recyclerView != null) {
                recyclerView.setVisibility(View.GONE);
            }
            if (model.getData().getCurrentSearchString() == null || model.getData().getCurrentSearchString().equals("")) {
                noSearchText.setText(R.string.suche_nach_einem_begriff_um_suchergebnisse_zu_erhalten);
            } else {
                noSearchText.setText(R.string.deine_suche_ergab_keine_treffer);
            }
        } else {
            noSearchText.setVisibility(View.GONE);
            searchText.setVisibility(View.VISIBLE);
            if (recyclerView != null) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Entfernt Aktien, die nicht den gesuchten Strig haben.
     *
     * @param stockList Die stockList, die gefiltert werden soll.
     * @return Die filtered stockList.
     */
    private ArrayList<Aktie> getSearchFilteredStockList(ArrayList<Aktie> stockList) {
        String searchString = model.getData().getCurrentSearchString();
        ArrayList<Aktie> filteredList = new ArrayList<>();
        if (searchString != null) {
            searchString = searchString.toLowerCase();
            for (Aktie stock : stockList) {
                if (stock.getSymbol().toLowerCase().contains(searchString)) {
                    filteredList.add(stock);
                }
            }
        }
        return filteredList;
    }
}