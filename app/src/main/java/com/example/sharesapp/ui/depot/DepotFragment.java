package com.example.sharesapp.ui.depot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.R;
import com.google.android.material.tabs.TabLayout;

public class DepotFragment extends Fragment {

    private DepotViewModel depotViewModel;
    TabLayout tabs;
    TabLayout.Tab tab_uebersicht;
    TabLayout.Tab tab_statistik;
    LinearLayout fragment_loader;

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

        fragment_loader = root.findViewById(R.id.fragment_loader_linear_layout);
        tabs = root.findViewById(R.id.depot_tab_layout);
        if (tabs == null) {
            System.out.println("NULL");
        }
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeFragment(tab.getPosition());
//                Intent myIntent = new Intent(DrawerActivity.this, DrawerActivity.class);
//                DrawerActivity.this.startActivity(myIntent);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    private void changeFragment(int position) {

        TextView test = new TextView(this.getContext());
        fragment_loader.addView(test);
        if (position == 0) {
            test.setText("Übersicht");

        }
        if (position == 1) {
            test.setText("Statistik");
        }
    }
}