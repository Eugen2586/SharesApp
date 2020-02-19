package com.example.sharesapp.ui.depot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.R;
import com.example.sharesapp.ui.depot.statistik.StatistikFragment;
import com.example.sharesapp.ui.depot.uebersicht.UebersichtFragment;
import com.google.android.material.tabs.TabLayout;

public class DepotFragment extends Fragment {

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private Fragment uebersicht;
    private Fragment statistik;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_depot, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        uebersicht = new UebersichtFragment();
        statistik = new StatistikFragment();

        fragmentTransaction.replace(R.id.fragment_loader_linear_layout, uebersicht).commit();
        TabLayout tabs = root.findViewById(R.id.depot_tab_layout);

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

            fragmentTransaction.replace(R.id.fragment_loader_linear_layout, uebersicht);
        }
        if (position == 1) {

            fragmentTransaction.replace(R.id.fragment_loader_linear_layout, statistik);

        }

        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}