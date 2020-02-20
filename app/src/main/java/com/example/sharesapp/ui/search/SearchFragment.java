package com.example.sharesapp.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    private Model model = new Model();
    private String searchString = null;
    private RecyclerView recyclerView = null;
    private View root;
    private StockRecyclerViewAdapter adapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        initCategorieSpinner();

        return root;
    }

    private void initCategorieSpinner() {
        String[] availableTypes = model.getData().getAvailType().getType_list();

        Spinner dropdown = root.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_dropdown_item, availableTypes);
        dropdown.setAdapter(adapter);
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    private void initRecyclerView() {
        recyclerView = root.findViewById(R.id.search_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter(ArrayList<Aktie> aktienList) {
        if (aktienList != null) {
            initRecyclerView();
            if (adapter == null) {
                adapter = new StockRecyclerViewAdapter(SearchFragment.this.getContext(), aktienList);
                adapter.setClickListener(SearchFragment.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setAktien(aktienList);
            }
            showSearchTextView();
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
}