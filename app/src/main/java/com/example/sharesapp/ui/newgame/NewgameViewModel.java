package com.example.sharesapp.ui.newgame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewgameViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewgameViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is newgame fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}