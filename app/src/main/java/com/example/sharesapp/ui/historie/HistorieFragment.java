package com.example.sharesapp.ui.historie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.R;

import java.util.ArrayList;

public class HistorieFragment extends Fragment {

    private HistorieViewModel historieViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historieViewModel =
                ViewModelProviders.of(this).get(HistorieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_historie, container, false);
        final ListView listView = root.findViewById(R.id.receiptlist);
        historieViewModel.mTrades.observe(getViewLifecycleOwner(), new Observer<ArrayList<Trade>>() {
            @Override
            public void onChanged(ArrayList<Trade> trades) {

            }
        }
        return root;
    }
}