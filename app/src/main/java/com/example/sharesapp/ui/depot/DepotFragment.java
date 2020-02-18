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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.R;
import com.google.android.material.tabs.TabLayout;
import com.example.sharesapp.ui.depot.statistik.*;
import com.example.sharesapp.ui.depot.uebersicht.*;

public class DepotFragment extends Fragment {

    private DepotViewModel depotViewModel;
    TabLayout tabs;
    TabLayout.Tab tab_uebersicht;
    TabLayout.Tab tab_statistik;
    LinearLayout fragment_loader;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    Fragment uebersicht;
    Fragment statistik;

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
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        uebersicht = new UebersichtFragment();
        statistik = new StatistikFragment();

        fragmentTransaction.replace(R.id.fragment_loader, uebersicht).commit();

        fragment_loader = root.findViewById(R.id.fragment_loader);
        tabs = root.findViewById(R.id.tabs);
        if (tabs == null) {
            System.out.println("NULL");
        }
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeFragment(tab.getPosition());
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

        fragmentTransaction = fragmentManager.beginTransaction();


        if (position == 0) {

            fragmentTransaction.replace(R.id.fragment_loader, uebersicht);
        }
        if (position == 1) {

            fragmentTransaction.replace(R.id.fragment_loader, statistik);

        }

        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}