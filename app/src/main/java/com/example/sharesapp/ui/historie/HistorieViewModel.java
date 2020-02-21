package com.example.sharesapp.ui.historie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;

public class HistorieViewModel extends ViewModel {

    private final MutableLiveData<Trade> mTrades;

    public HistorieViewModel() {
        mTrades = new MutableLiveData<>();
        for (Trade e: new Model().getData().getTrades()) {
            mTrades.postValue(e);
        }

    }

    public LiveData<Trade> getText() {
        return mTrades;
    }
}