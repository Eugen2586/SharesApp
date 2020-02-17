package com.example.sharesapp.ui.aktien;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AktienViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AktienViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is aktien fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}