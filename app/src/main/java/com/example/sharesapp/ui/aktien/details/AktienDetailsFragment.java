package com.example.sharesapp.ui.aktien.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharesapp.R;

import androidx.fragment.app.Fragment;

public class AktienDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_depot_uebersicht, container, false);
    }
}
