package com.example.sharesapp.ui.order.buyorder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BuyOrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BuyOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Statistik fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
