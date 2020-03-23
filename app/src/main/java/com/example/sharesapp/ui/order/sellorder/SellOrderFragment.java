package com.example.sharesapp.ui.order.sellorder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;
import com.example.sharesapp.ui.utils.OrderRecyclerViewAdapter;
import com.example.sharesapp.ui.utils.StockRecyclerViewAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class SellOrderFragment extends Fragment implements OrderRecyclerViewAdapter.ItemClickListener {

    private Model model = new Model();
    private RecyclerView recyclerView;
    private View root;
    private StockRecyclerViewAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_order_sell, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);

        final Observer<ArrayList<Order>> observer = new Observer<ArrayList<Order>>() {
            @Override
            public void onChanged(ArrayList<Order> orderList) {
                setAdapter(orderList);
            }
        };

        model.getData().getSellOrderList().observe(getViewLifecycleOwner(), observer);

        setAdapter(model.getData().getSellOrderList().getValue());
        return root;
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
        recyclerView = root.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    //to bind the uebersicht und aktien from depotlist
    private void setAdapter(ArrayList<Order> orderList) {
        if (orderList != null) {
            initRecyclerView();
            OrderRecyclerViewAdapter adapter = new OrderRecyclerViewAdapter(SellOrderFragment.this.getContext(), orderList);
            adapter.setClickListener(SellOrderFragment.this);
            adapter.setAktien(orderList);
            recyclerView.setAdapter(adapter);
        }
        showHideComponents(orderList);
    }

    private void showHideComponents(ArrayList<Order> orderList) {
        if (orderList == null || orderList.size() == 0) {
            root.findViewById(R.id.no_sellorder_text).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            root.findViewById(R.id.no_sellorder_text).setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
