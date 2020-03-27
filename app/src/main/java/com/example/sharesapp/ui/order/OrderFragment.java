package com.example.sharesapp.ui.order;

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
import com.example.sharesapp.ui.order.buyorder.BuyOrderFragment;
import com.example.sharesapp.ui.order.sellorder.SellOrderFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class OrderFragment extends Fragment {

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private Model model = new Model();
    private TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        if (model.getData().getPreviouslySelectedOrderTabIndex() == 0) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.order_fragment_loader_linear_layout, new BuyOrderFragment()).commit();
        }
        tabLayout = root.findViewById(R.id.order_tab_layout);

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
            fragmentTransaction.replace(R.id.order_fragment_loader_linear_layout, new BuyOrderFragment());
        } else if (position == 1) {
            fragmentTransaction.replace(R.id.order_fragment_loader_linear_layout, new SellOrderFragment());
        }

        fragmentTransaction.commitNow();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tabLayout != null) {
            tabLayout.selectTab(tabLayout.getTabAt(model.getData().getPreviouslySelectedOrderTabIndex()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        model.getData().setPreviouslySelectedOrderTabIndex(tabLayout.getSelectedTabPosition());
    }
}