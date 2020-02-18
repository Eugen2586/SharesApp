package com.example.sharesapp.ui.depot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DepotViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DepotViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is depot fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}