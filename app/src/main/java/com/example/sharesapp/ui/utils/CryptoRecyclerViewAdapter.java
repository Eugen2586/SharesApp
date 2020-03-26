package com.example.sharesapp.ui.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Crypto;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CryptoRecyclerViewAdapter extends RecyclerView.Adapter<CryptoRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Crypto> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CryptoRecyclerViewAdapter(Context context, ArrayList<Crypto> data) {
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
        Crypto crypto = mData.get(position);
        System.out.println("crypto: " + crypto.getSymbol());
        holder.mySymbolView.setText(crypto.getSymbol());
        holder.myTypeView.setText(crypto.getType());

        // set value if stock in depot
//        ArrayList<Crypto> cryptoDepotList = (new Model()).getData().getDepot().getAktienImDepot().getValue();
//        if (cryptoDepotList != null) {
//            boolean notFoundInDepot = true;
//            for (Crypto depotStock : cryptoDepotList) {
//                if (depotStock.getSymbol().equals(crypto.getSymbol())) {
//                    String text = depotStock.getAnzahl() + " x " + (new Anzeige()).makeItBeautiful(depotStock.getPreis()) + "€";
//                    holder.myDepotValueView.setText(text);
//                    notFoundInDepot = false;
//                    break;
//                }
//            }
//            if (notFoundInDepot) {
//                holder.myDepotValueView.setText("");
//            }
//        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setCrypto(ArrayList<Crypto> data) {
        if (!data.equals(mData)) {
            mData = data;
            notifyDataSetChanged();
        }
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mySymbolView;
        TextView myDepotValueView;
        TextView myTypeView;

        ViewHolder(View itemView) {
            super(itemView);
            mySymbolView = itemView.findViewById(R.id.crypto_symbol_text);
            myTypeView = itemView.findViewById(R.id.crypto_category_text);
            myDepotValueView = itemView.findViewById(R.id.depot_value_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onCryptoItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Crypto getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onCryptoItemClick(View view, int position);
    }
}
