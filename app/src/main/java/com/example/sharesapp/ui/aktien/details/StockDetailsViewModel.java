package com.example.sharesapp.ui.aktien.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StockDetailsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StockDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is aktien details fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
