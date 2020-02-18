package com.example.sharesapp.ui.erfolge;

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

public class ErfolgeFragment extends Fragment {

    private ErfolgeViewModel erfolgeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        erfolgeViewModel =
                ViewModelProviders.of(this).get(ErfolgeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_erfolge, container, false);
        final TextView textView = root.findViewById(R.id.text_erfolge);
        erfolgeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}