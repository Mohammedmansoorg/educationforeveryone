package com.example.educationforeveryone.ui.UserProfile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.educationforeveryone.ui.Profile.ProfileModel;
import com.example.educationforeveryone.ui.Profile.ProfileRepository;

public class UserProfileViewModel extends ViewModel {

    private final MutableLiveData<UserProfileModel> liveData;

    public UserProfileViewModel(String otherUser){
        UserProfileRepository repository = new UserProfileRepository(otherUser);
        liveData = repository.getLiveData();
    }

    public MutableLiveData<UserProfileModel> getData(){
        return liveData;
    }
}
