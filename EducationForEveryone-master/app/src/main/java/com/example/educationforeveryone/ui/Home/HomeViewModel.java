package com.example.educationforeveryone.ui.Home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<HomeModel> liveData;

    public HomeViewModel(){
        liveData = new MutableLiveData<>();
        setData();
    }

    private void setData(){
        String tyt = "17 Haziran";
        String ayt = "18 Haziran";
        HomeModel homeModel = new HomeModel(tyt, ayt);
        liveData.setValue(homeModel);
    }

    public MutableLiveData<HomeModel> getData(){
        return liveData;
    }
}
