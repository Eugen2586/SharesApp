package com.example.sharesapp.ui.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StockRecyclerViewAdapter extends RecyclerView.Adapter<StockRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Aktie> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public StockRecyclerViewAdapter(Context context, ArrayList<Aktie> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_stock_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Aktie aktie = mData.get(position);
        holder.mySymbolView.setText(aktie.getSymbol());
        holder.myTextView.setText(aktie.getName());
        holder.myTypeView.setText(aktie.getType());

        // set value if stock in depot
        ArrayList<Aktie> depotList = (new Model()).getData().getDepot().getAktienImDepot();
        if (depotList != null) {
            for (Aktie depotStock : depotList) {
                if (depotStock.getSymbol().equals(aktie.getSymbol())) {
                    String text = depotStock.getAnzahl() + " x " + (new Anzeige()).makeItBeautiful(depotStock.getPreis()) + "€";
                    holder.myDepotValueView.setText(text);
                    break;
                }
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setAktien(ArrayList<Aktie> data) {
        mData = data;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView myTypeView;
        TextView mySymbolView;
        TextView myDepotValueView;

        ViewHolder(View itemView) {
            super(itemView);
            mySymbolView = itemView.findViewById(R.id.stock_symbol_text);
            myTextView = itemView.findViewById(R.id.stock_text);
            myTypeView = itemView.findViewById(R.id.stock_category_text);
            myDepotValueView = itemView.findViewById(R.id.depot_value_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            System.out.println(mData.get(getAdapterPosition()));

        }
    }

    // convenience method for getting data at click position
    Aktie getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
