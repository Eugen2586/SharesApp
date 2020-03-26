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
import com.example.sharesapp.REST.Range;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class SearchFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    private Model model = new Model();
    private RecyclerView recyclerView = null;
    private View root;
    private int searchIndex = 0;
    private TextView noSearchText;
    private TextView searchText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        noSearchText = root.findViewById(R.id.no_search_text);
        searchText = root.findViewById(R.id.search_text);

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

    @Override
    public void onPause() {
        super.onPause();
        if (recyclerView != null) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int scrollState = 0;
            if (linearLayoutManager != null) {
                scrollState = linearLayoutManager.findFirstVisibleItemPosition();
            }
            model.getData().setSearchScrollPosition(scrollState);
        }
    }


    private void initCategorieSpinner(Spinner spinner) {
        Context context = this.getContext();
        if (context != null) {
//            String[] availableTypes = model.getData().getAvailType().getAvailableTypes();
//            if (availableTypes == null || availableTypes.length == 0) {
            // not yet available -> set Alles as default value
            String[] availableTypes = new String[3];
            availableTypes[0] = Constants.TYPES[0];
            availableTypes[1] = "Fremdwährungen";
            availableTypes[2] = "Kryptowährungen";
//            } else {
//                // available -> set Alles as first value
//                String[] types = new String[availableTypes.length + 3];
//                types[0] = Constants.TYPES[0];
//                for (int i = 0; i < availableTypes.length; i++) {
//                    types[i + 1] = availableTypes[i];
//                }
//                availableTypes = types.clone();
//            }
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
                model.getData().addAktienList(stockList);
            }

            recyclerView.scrollToPosition(model.getData().getSearchScrollPosition());
        }

        showHideComponents(stockList);
    }

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
}