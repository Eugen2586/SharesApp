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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener, AdapterView.OnItemSelectedListener {

    private Model model = new Model();
    private RecyclerView recyclerView = null;
    private View root;
    private StockRecyclerViewAdapter adapter = null;
    private int searchIndex = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        initCategorieSpinner();

//        setAdapter();

        return root;
    }

    private void initCategorieSpinner() {
        Context context = this.getContext();
        if (context != null) {
            final Spinner dropdown = root.findViewById(R.id.spinner);
            dropdown.setOnItemSelectedListener(this);
            String[] availableTypes = model.getData().getAvailType().getAvailableTypes();
            if (availableTypes == null || availableTypes.length == 0) {
                // not yet available -> set Alles as default value
                availableTypes = new String[1];
                availableTypes[0] = Constants.TYPES[0];
            } else {
                // available -> set Alles as first value
                System.out.println(availableTypes.length);
                String[] types = new String[availableTypes.length + 1];
                types[0] = "Alles";
                for (int i = 0; i < availableTypes.length; i++) {
                    types[i + 1] = availableTypes[i];
                }
                availableTypes = types.clone();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                    android.R.layout.simple_spinner_dropdown_item, availableTypes);
            dropdown.setAdapter(adapter);
        }
    }

    public void setSearchIndex(int searchIndex) {
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

            showSearchTextView();

            initRecyclerView();
            adapter = new StockRecyclerViewAdapter(SearchFragment.this.getContext(), filteredStockList);
            adapter.setClickListener(SearchFragment.this);
            recyclerView.setAdapter(adapter);
            adapter.setAktien(filteredStockList);
            recyclerView.setAdapter(adapter);
        }
    }

    private void showSearchTextView() {
        TextView searchTextView = root.findViewById(R.id.search_text);
        searchTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {
        Navigation.findNavController(view).navigate(R.id.aktienDetailsFragment);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setSearchIndex(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("OnNothingSelectedCalled");
        setSearchIndex(0);
    }
}