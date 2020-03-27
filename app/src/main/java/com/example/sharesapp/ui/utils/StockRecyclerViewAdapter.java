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
        if (aktie.getCompanyName() == null || aktie.isCrypto()) {
            holder.myTextView.setText("");
        } else {
            holder.myTextView.setText(aktie.getCompanyName());
        }
        holder.myTypeView.setText(aktie.getType());

        // set value if stock in depot
        String text = "";
        ArrayList<Aktie> depotList = (new Model()).getData().getDepot().getAktienImDepot().getValue();
        if (depotList != null) {
            boolean notFoundInDepot = true;
            for (Aktie depotStock : depotList) {
                if (depotStock.getSymbol().equals(aktie.getSymbol())) {
                    text = depotStock.getAnzahl() + " x " + (new Anzeige()).makeItBeautifulEuro(depotStock.getPrice());
                    holder.myDepotValueView.setText(text);
                    notFoundInDepot = false;
                    break;
                }
            }
            if (notFoundInDepot) {
                if (aktie.getPrice() == 0) {
                    holder.myDepotValueView.setText("");
                } else {
                    holder.myDepotValueView.setText(new Anzeige().makeItBeautifulEuro(aktie.getPrice()));
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
        if (!data.equals(mData)) {
            mData = data;
            notifyDataSetChanged();
        }
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
