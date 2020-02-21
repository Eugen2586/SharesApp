package com.example.sharesapp.ui.historie;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;

import java.util.ArrayList;

public class HistorieViewModel extends ViewModel {

    protected final MutableLiveData<ArrayList<Trade>> mTrades = new MutableLiveData<ArrayList<Trade>>();

    public HistorieViewModel() {
        mTrades.postValue(new Model().getData().getTrades());

    }

    public ArrayList<Trade> getTrades() {
        return mTrades.getValue();
    }
}