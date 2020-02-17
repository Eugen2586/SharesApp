package com.example.sharesapp.ui.historie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistorieViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HistorieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is historie fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}