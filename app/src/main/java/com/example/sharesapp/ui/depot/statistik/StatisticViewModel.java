package com.example.sharesapp.ui.depot.statistik;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StatisticViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Statistik fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
