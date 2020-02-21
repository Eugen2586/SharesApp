package com.example.sharesapp.ui.search;

import android.widget.SearchView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is newgame fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}