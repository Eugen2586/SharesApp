package com.example.sharesapp.ui.aktien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Quote;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;
import com.example.sharesapp.ui.depot.uebersicht.UebersichtFragment;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AktienFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    private AktienViewModel aktienViewModel;
    private MutableLiveData<Aktie> currentQuote = new MutableLiveData<>();
//    private String[] typeList = {"crypto", "oil", "us-market", "etfs", "bonds", "funding", "fx", "capital-raise"};

    RecyclerView recyclerView = null;
    View root;
    StockRecyclerViewAdapter adapter = null;
    Model model = new Model();
    private RecyclerView recyclerView = null;
    private View root;
    private StockRecyclerViewAdapter adapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_aktien, container, false);
        TabLayout tabLayout = root.findViewById(R.id.category_tab_layout);

        addTabs(tabLayout);

        final TabLayout finalTabLayout = tabLayout;

        final Observer<ArrayList<Aktie>> listObserver = new Observer<ArrayList<Aktie>>() {
            @Override
            public void onChanged(ArrayList<Aktie> aktienList) {
                setCurrentQuote();
                setAdapter(aktienList);
                setCategory(finalTabLayout.getSelectedTabPosition());
            }
        };

        model.getDaten().getAktienList().observe(getViewLifecycleOwner(), listObserver);

        final Observer<Aktie> currentQuoteObserver = new Observer<Aktie>() {
            @Override
            public void onChanged(Aktie aktie) {
                View aktienDetailsFrag = root.findViewById(R.id.aktienDetailsFragment);
                Aktie cq = currentQuote.getValue();
                assert cq != null;
                TextView symbolTV = aktienDetailsFrag.findViewById(R.id.symbol_field);
                symbolTV.setText(cq.getSymbol());
                TextView nameTV = aktienDetailsFrag.findViewById(R.id.name_field);
                nameTV.setText(cq.getName());
                // todo set all fields
            }
        };

        setAdapter(model.getDaten().getAktienList().getValue());
        model.getData().getAktienList().observe(getViewLifecycleOwner(), observer);

        if (tabLayout != null) {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    setCategory(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        return root;
    }

    private void setCurrentQuote() {
        if (currentQuote.getValue() != null) {
            Model model = new Model();
            ArrayList<Aktie> quotes = model.getDaten().getAktienList().getValue();
            int index = Objects.requireNonNull(quotes).indexOf(currentQuote.getValue());
            currentQuote.setValue(quotes.get(index));
        }

    }

    private void changeCategory(int position) {
        switch (position) {
            case 0:
    private void addTabs(TabLayout tabLayout) {
        for (String category: Constants.TYPE_LIST) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(category);
            tabLayout.addTab(tab);
        }
    }

    private void setCategory(int position) {
        if (position == 0) {
            // TODO: portfolio einfügen
            System.out.println("Portfolio einfügen");
        } else {
            position--;

            ArrayList<Aktie> aktien = model.getData().getAktienList().getValue();
            if (aktien != null) {
                String type = Constants.TYPE_ABBRE_LIST[position];
                ArrayList<Aktie> filtered_aktien = new ArrayList<>();
                for (Aktie aktie: aktien) {
                    if (aktie.getType().equals(type)) {
                        filtered_aktien.add(aktie);
                    }
                }

                setAdapter(filtered_aktien);
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //todo bind to aktien
        Requests requests = new Requests();
        TextView symbolView = root.findViewById(R.id.stock_symbol_text);
        String symbol = (String) symbolView.getText();
        Aktie quote = new Aktie();
        quote.setSymbol(symbol);
        currentQuote.setValue(quote);
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
        System.out.println("Called setAdapter " + aktienList);
        if (aktienList != null) {
            initRecyclerView();
            if (adapter == null) {
                adapter = new StockRecyclerViewAdapter(AktienFragment.this.getContext(), aktienList);
                adapter.setClickListener(AktienFragment.this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setAktien(aktienList);
            }
        }
    }
}