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

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;
import com.example.sharesapp.ui.depot.statistik.StatistikFragment;
import com.example.sharesapp.ui.depot.uebersicht.UebersichtFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DepotFragment extends Fragment {

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private TabLayout tabLayout = null;
    private Model model = new Model();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_depot, container, false);

        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        if (model.getData().getPreviouslySelectedDepotTabIndex() == 0) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.depot_fragment_loader_linear_layout, new UebersichtFragment()).commit();
        }

        tabLayout = root.findViewById(R.id.depot_tab_layout);

        if (tabLayout != null) {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        }

        return root;
    }

    private void changeFragment(int position) {
        fragmentTransaction = fragmentManager.beginTransaction();
        if (position == 0) {
            fragmentTransaction.replace(R.id.depot_fragment_loader_linear_layout, new UebersichtFragment());
        } else if (position == 1) {
            fragmentTransaction.replace(R.id.depot_fragment_loader_linear_layout, new StatistikFragment());
        }

        fragmentTransaction.commitNow();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tabLayout != null) {
            tabLayout.selectTab(tabLayout.getTabAt(model.getData().getPreviouslySelectedDepotTabIndex()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        model.getData().setPreviouslySelectedDepotTabIndex(tabLayout.getSelectedTabPosition());
    }
}