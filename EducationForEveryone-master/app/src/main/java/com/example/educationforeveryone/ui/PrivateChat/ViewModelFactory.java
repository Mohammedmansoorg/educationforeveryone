package com.example.educationforeveryone.ui.PrivateChat;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private String currUser, otherUser;

    public ViewModelFactory(String currUser, String otherUser) {
        this.currUser = currUser;
        this.otherUser = otherUser;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MessageViewModel(currUser, otherUser);
    }
}
