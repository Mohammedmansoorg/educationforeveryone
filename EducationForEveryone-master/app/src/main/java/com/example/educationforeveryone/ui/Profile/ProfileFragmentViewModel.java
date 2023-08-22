package com.example.educationforeveryone.ui.Profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileFragmentViewModel extends ViewModel {

    private final MutableLiveData<ProfileModel> liveData;

    public ProfileFragmentViewModel(){
        ProfileRepository repository = new ProfileRepository();
        liveData = repository.getLiveData();
    }

    public MutableLiveData<ProfileModel> getData(){
        return liveData;
    }

}