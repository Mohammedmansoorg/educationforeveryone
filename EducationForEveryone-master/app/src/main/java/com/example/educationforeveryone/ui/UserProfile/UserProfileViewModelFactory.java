package com.example.educationforeveryone.ui.UserProfile;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class UserProfileViewModelFactory implements ViewModelProvider.Factory {

    private String otherUser;

    public UserProfileViewModelFactory(String otherUser) {
        this.otherUser = otherUser;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new UserProfileViewModel(otherUser);
    }
}
