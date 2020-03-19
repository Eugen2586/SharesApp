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
import com.example.sharesapp.REST.RequestsBuilder;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class SearchFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    private Model model = new Model();
    private RecyclerView recyclerView = null;
    private View root;
    private int searchIndex = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        final Spinner spinner = root.findViewById(R.id.spinner);
        initCategorieSpinner(spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSearchIndex(position);
                setAdapter(model.getData().getSearches());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setSearchIndex(0);
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

    private void initCategorieSpinner(Spinner spinner) {
        Context context = this.getContext();
        if (context != null) {
            String[] availableTypes = model.getData().getAvailType().getAvailableTypes();
            if (availableTypes == null || availableTypes.length == 0) {
                // not yet available -> set Alles as default value
                availableTypes = new String[1];
                availableTypes[0] = Constants.TYPES[0];
            } else {
                // available -> set Alles as first value
                String[] types = new String[availableTypes.length + 1];
                types[0] = Constants.TYPES[0];
                for (int i = 0; i < availableTypes.length; i++) {
                    types[i + 1] = availableTypes[i];
                }
                availableTypes = types.clone();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                    android.R.layout.simple_spinner_dropdown_item, availableTypes);
            spinner.setAdapter(adapter);
        }
    }

    private void setSearchIndex(int searchIndex) {
        this.searchIndex = searchIndex;
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.search_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter(ArrayList<Aktie> stockList) {
        if (stockList != null) {
            ArrayList<Aktie> filteredStockList;
            if (searchIndex == 0) {
                // do not filter
                filteredStockList = stockList;
            } else {
                // filter for searched type
                String searchType = model.getData().getAvailType().getAvailableTypeAbbreviations()[searchIndex - 1];
                filteredStockList = new ArrayList<>();
                for (Aktie stock : stockList) {
                    if (stock.getType().equals(searchType)) {
                        filteredStockList.add(stock);
                    }
                }
            }

            boolean stocksFound = filteredStockList.size() != 0;
            showSearchTextView(stocksFound);

            initRecyclerView();
            StockRecyclerViewAdapter adapter = new StockRecyclerViewAdapter(SearchFragment.this.getContext(), filteredStockList);
            adapter.setClickListener(SearchFragment.this);
            adapter.setAktien(filteredStockList);
            recyclerView.setAdapter(adapter);

            // add stocks to existingStockList if not yet included
            ArrayList<Aktie> existingStockList = model.getData().getAktienList().getValue();
            ArrayList<Aktie> stocksToAdd = new ArrayList<>();
            if (existingStockList != null) {
                for (Aktie stock : stockList) {
                    String symbol = stock.getSymbol();
                    boolean found = false;
                    for (Aktie existingStock: existingStockList) {
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
                model.getData().addAktienList(stockList);
            }
        }
    }

    private void showSearchTextView(boolean stocksFound) {
        TextView searchTextView = root.findViewById(R.id.search_text);
        if (stocksFound) {
            searchTextView.setText(R.string.deine_suche_ergab_folgende_treffer);
        } else {
            searchTextView.setText(R.string.deine_suche_ergab_keine_treffer);
        }
        searchTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {
        // opens stock details copied from AktienFragment
        TextView symbolView = view.findViewById(R.id.stock_text);
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
}