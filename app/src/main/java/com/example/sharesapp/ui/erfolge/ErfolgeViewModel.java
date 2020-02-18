package com.example.sharesapp.ui.erfolge;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ErfolgeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ErfolgeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is erfolge fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}