package com.example.sharesapp.ui.depot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.R;

public class DepotFragment extends Fragment {

    private DepotViewModel depotViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        depotViewModel =
                ViewModelProviders.of(this).get(DepotViewModel.class);
        View root = inflater.inflate(R.layout.fragment_depot, container, false);
        final TextView textView = root.findViewById(R.id.text_depot);
        depotViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}