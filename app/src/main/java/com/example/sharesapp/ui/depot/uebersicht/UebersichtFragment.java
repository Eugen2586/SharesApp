package com.example.sharesapp.ui.depot.uebersicht;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import java.util.ArrayList;
import java.util.List;

import com.example.sharesapp.ui.aktien.details.*;

public class UebersichtFragment extends Fragment implements StockRecyclerViewAdapter.ItemClickListener {

    StockRecyclerViewAdapter adapter;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Model model = new Model();
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_depot_uebersicht, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        ArrayList<Aktie> aktienList = model.getDaten().getAktienList();
//        aktienList = new ArrayList<>();
//        Aktie aktie1 = new Aktie();
//        aktie1.setName("Name1");
//        Aktie aktie2 = new Aktie();
//        aktie2.setName("Name2");
//        aktienList.add(aktie1);
//        aktienList.add(aktie2);
        if (aktienList != null) {
            // set up the RecyclerView
            RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new StockRecyclerViewAdapter(this.getContext(), aktienList);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                    ((LinearLayoutManager) layoutManager).getOrientation());
//            recyclerView.addItemDecoration(dividerItemDecoration);
        }


        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this.getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Fragment details = new AktienDetailsFragment(); //todo bind to aktien
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, details).commit();

    }
}
