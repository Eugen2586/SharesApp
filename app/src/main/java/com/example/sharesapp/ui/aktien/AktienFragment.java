package com.example.sharesapp.ui.aktien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.R;

public class AktienFragment extends Fragment {

    private AktienViewModel aktienViewModel;
//    private String[] typeList = {"crypto", "oil", "us-market", "etfs", "bonds", "funding", "fx", "capital-raise"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aktienViewModel =
                ViewModelProviders.of(this).get(AktienViewModel.class);
        View root = inflater.inflate(R.layout.fragment_aktien, container, false);

        return root;
    }
}