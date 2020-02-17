package com.example.sharesapp.ui.aktien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sharesapp.R;

public class AktienFragment extends Fragment {

    private AktienViewModel aktienViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aktienViewModel =
                ViewModelProviders.of(this).get(AktienViewModel.class);
        View root = inflater.inflate(R.layout.fragment_aktien, container, false);
        final TextView textView = root.findViewById(R.id.text_aktien);
        aktienViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}