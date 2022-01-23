package com.example.configurableui.universal;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UniversalBuildViewModel extends ViewModel {

    private MutableLiveData<Groups> groupsMutableLiveData;

    public UniversalBuildViewModel(){

        this.groupsMutableLiveData = new MutableLiveData<>();

    }

    public MutableLiveData<Groups> getGroupsMutableLiveData() {
        return groupsMutableLiveData;
    }

    public void readFileLocal(Context context){

        groupsMutableLiveData.setValue(UniversalBuildRepositiory.getUniversauildData( context ));
    }

}