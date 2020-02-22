package com.example.sharesapp.ui.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.FunktionaleKlassen.Waehrungen.Anzeige;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TradeRecyleViewAdapter extends RecyclerView.Adapter<TradeRecyleViewAdapter.ViewHolder> {

    private ArrayList<Trade> mData;
    private LayoutInflater mInflater;
    private TradeRecyleViewAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public TradeRecyleViewAdapter(Context context, ArrayList<Aktie> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new Model().getData().getTrades();
    }

    // inflates the row layout from xml when needed
    @NotNull
    @Override
    public TradeRecyleViewAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_trade_item, parent, false);
        return new TradeRecyleViewAdapter.ViewHolder(view);
    }

    //Binds data to the Gui
    @Override
    public void onBindViewHolder(@NonNull TradeRecyleViewAdapter.ViewHolder holder, int position) {
        Trade trade = mData.get(position);
        //holder.view.set;
        if(trade.isKauf()) {
            holder.name.setText( trade.getAktie().getName());
            holder.anzahl.setText(" + " +String.valueOf(trade.getAnzahl())+" x");
            holder.umsatz.setText(String.valueOf(Float.parseFloat( new Anzeige().makeItBeautiful(trade.getPreis() * trade.getAnzahl()))));
        }else{
            holder.name.setText(trade.getAktie().getName());
            holder.anzahl.setText(" - " +String.valueOf(trade.getAnzahl())+" x");
            holder.umsatz.setText(String.valueOf(Float.parseFloat(String.valueOf(new Anzeige().makeItBeautiful(trade.getPreis() * trade.getAnzahl())))));
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setTrades(ArrayList<Trade> data) {
        mData = data;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;
        TextView name;
        TextView anzahl;
        TextView umsatz;
        TextView art;

        ViewHolder(View itemView) {
            super(itemView);
//            view = itemView.findViewById(R.id.view);
            name = itemView.findViewById(R.id.name);
            anzahl = itemView.findViewById(R.id.anzahl);
            umsatz = itemView.findViewById(R.id.umsatz);
//            art = itemView.findViewById(R.id.art);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            System.out.println(mData.get(getAdapterPosition()));

        }
    }

    // convenience method for getting data at click position
    Trade getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(TradeRecyleViewAdapter.ItemClickListener itemClickListener) {

        //this.mClickListener = itemClickListener;

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
