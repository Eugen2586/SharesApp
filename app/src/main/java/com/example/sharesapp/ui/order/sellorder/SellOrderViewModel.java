package com.example.sharesapp.ui.order.sellorder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellOrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SellOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is übersicht fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
