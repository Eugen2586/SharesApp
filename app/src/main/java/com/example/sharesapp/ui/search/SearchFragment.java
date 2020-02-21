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
    private String searchString = null;
    private RecyclerView recyclerView = null;
    private View root;
    private StockRecyclerViewAdapter adapter = null;
    private int searchIndex = 0;
    private String[] availableTypes = null;
    private String[] availableTypeAbbreviations = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        getAvailableTypes();

        initCategorieSpinner();

        return root;
    }

    private void getAvailableTypes() {
        availableTypes = model.getData().getAvailType().getAvailableTypes();
        availableTypeAbbreviations = model.getData().getAvailType().getAvailableTypeAbbreviations();

        // not yet loaded -> set to "Alles"
        if (availableTypes == null) {
            availableTypes = new String[1];
            availableTypes[0] = Constants.TYPE_LIST[0];
        }
    }

    private void initCategorieSpinner() {
        final Spinner dropdown = root.findViewById(R.id.spinner);
        dropdown.setOnItemSelectedListener(this);
        Context context = this.getContext();
        if (context != null && availableTypes != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                    android.R.layout.simple_spinner_dropdown_item, availableTypes);
            dropdown.setAdapter(adapter);
        }
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public void setSearchIndex(int searchIndex) {
        this.searchIndex = searchIndex;
//        setAdapter(model.getData().getAktienList().getValue());
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.search_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

//    private void setAdapter(ArrayList<Aktie> aktienList) {
//        if (aktienList != null) {
//            initRecyclerView();
//
//            // filter aktienList
//            ArrayList<Aktie> stockList = model.getData().getAktienList().getValue();
//            if (stockList != null) {
//                if (!searchCategory.equals(availableCategories[0])) {
//                    String[] avail_types = model.getData().getAvailType().getType_list();
//                    String searchType = null;
//                    for (int categoryIndex = 1; categoryIndex < avail_types.length; categoryIndex++) {
//                        if (searchCategory.equals(avail_types[categoryIndex])) {
//                            searchType = avail_types[categoryIndex];
//                            break;
//                        }
//                    }
//                    // TODO: Search Request
//                    ArrayList<Aktie> filteredStockList = new ArrayList<>();
//                    for (Aktie stock : stockList) {
//                        if (stock.getType().equals(searchType)) {
//                            filteredStockList.add(stock);
//                        }
//                    }
//                    stockList = filteredStockList;
//                }
//
//                // set adapter on typeFiltered List
//                if (adapter == null) {
//                    adapter = new StockRecyclerViewAdapter(SearchFragment.this.getContext(), stockList);
//                    adapter.setClickListener(SearchFragment.this);
//                    recyclerView.setAdapter(adapter);
//                } else {
//                    adapter.setAktien(stockList);
//                }
//                showSearchTextView();
//            }
//        }
//    }

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

    }
}