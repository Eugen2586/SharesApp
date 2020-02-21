package com.example.sharesapp.ui.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TradeRecyleViewAdapter extends RecyclerView.Adapter<TradeRecyleViewAdapter.ViewHolder> {

    private ArrayList<Aktie> mData;
    private LayoutInflater mInflater;
    private TradeRecyleViewAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public TradeRecyleViewAdapter(Context context, ArrayList<Aktie> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
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
        Aktie aktie = mData.get(position);
        holder.myTextView.setText(aktie.getName());
        holder.mySymbolView.setText(aktie.getSymbol());
        holder.myTextView.setText(aktie.getName());
        holder.myTypeView.setText(aktie.getType());
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

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.stock_text);
            mySymbolView = itemView.findViewById(R.id.stock_symbol_text);
            myTypeView = itemView.findViewById(R.id.stock_percentage_text);
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
    public void setClickListener(TradeRecyleViewAdapter.ItemClickListener itemClickListener) {

        //this.mClickListener = itemClickListener;

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
