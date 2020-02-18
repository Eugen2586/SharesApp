package com.example.sharesapp.ui.newgame;

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

public class NewgameFragment extends Fragment {

    private NewgameViewModel newgameViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newgameViewModel =
                ViewModelProviders.of(this).get(NewgameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_newgame, container, false);
        return root;
    }
}