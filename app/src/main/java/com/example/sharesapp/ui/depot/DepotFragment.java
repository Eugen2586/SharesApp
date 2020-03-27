package com.example.sharesapp.ui.depot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.ui.depot.statistik.StatisticFragment;
import com.example.sharesapp.ui.depot.uebersicht.OverviewFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

/**
 * Enables the user to switch between the depotOverview and the statistics
 */
public class DepotFragment extends Fragment {

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private TabLayout tabLayout = null;
    private Model model = new Model();

    /**
     * initialization of tabLayout and fragmentManager
     * fragmentManager loads OverviewFragment
     *
     * @param inflater           nflates the depot fragment
     * @param container          needed for the inflation
     * @param savedInstanceState not needed
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_depot, container, false);

        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        if (model.getData().getPreviouslySelectedDepotTabIndex() == 0) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.depot_fragment_loader_linear_layout, new OverviewFragment()).commit();
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

    /**
     * on tab change called, loads the different fragments for overview and statistics depending on position
     *
     * @param position position of the tab that was selected
     */
    private void changeFragment(int position) {
        fragmentTransaction = fragmentManager.beginTransaction();
        if (position == 0) {
            fragmentTransaction.replace(R.id.depot_fragment_loader_linear_layout, new OverviewFragment());
        } else if (position == 1) {
            fragmentTransaction.replace(R.id.depot_fragment_loader_linear_layout, new StatisticFragment());
        }

        fragmentTransaction.commitNow();
    }

    /**
     * saved tabPosition is selected
     */
    @Override
    public void onResume() {
        super.onResume();
        if (tabLayout != null) {
            tabLayout.selectTab(tabLayout.getTabAt(model.getData().getPreviouslySelectedDepotTabIndex()));
        }
    }

    /**
     * currently selected tabPosition is saved
     */
    @Override
    public void onPause() {
        super.onPause();
        model.getData().setPreviouslySelectedDepotTabIndex(tabLayout.getSelectedTabPosition());
    }
}