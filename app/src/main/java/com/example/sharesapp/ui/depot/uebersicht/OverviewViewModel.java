package com.example.sharesapp.ui.depot.uebersicht;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OverviewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OverviewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is übersicht fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
