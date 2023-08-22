package com.example.educationforeveryone.ui.Chats;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatsViewModel extends ViewModel {

    private final MutableLiveData<List<ChatModel>> liveData;
    private final List<ChatModel> chatModelList;

    public ChatsViewModel() {
        chatModelList = new ArrayList<>();
        ChatRepository repository = new ChatRepository();
        liveData = repository.getChats();
    }

    public MutableLiveData<List<ChatModel>> getChats(){
        return liveData;
    }
}