package com.example.educationforeveryone.ui.Chats;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatRepository {

    DatabaseReference reference;
    private MutableLiveData<List<ChatModel>> liveData;
    private List<ChatModel> chatModelList;
    FirebaseUser user;
//    private List<String> userKeyList;

    public ChatRepository(){
        chatModelList = new ArrayList<>();
        liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
//        userKeyList = new ArrayList<>();
    }

    public MutableLiveData<List<ChatModel>> getChats(){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("burası çalıştı");
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    userKeyList.add(dataSnapshot.getKey());
                    String otherUser = dataSnapshot.getKey();
                    System.out.println("other: "+otherUser);
//                    getUsername();

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(otherUser) //todo: otherUser
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String username = snapshot.child("username")
                                            .getValue().toString();
                                    ChatModel chatModel = new ChatModel(username, otherUser);
                                    chatModelList.add(chatModel);
                                    liveData.setValue(chatModelList);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("neden..");
            }
        };

        Query query = reference.child("Messages").child(user.getUid()); //todo: user.getUid()
        System.out.println("burası olsun çalıştı de aq");
        query.addListenerForSingleValueEvent(valueEventListener);
        System.out.println("ama neden benn..");

        //todo: db den veri indirme kısmını yazcaz

//        ChatModel chatModel = new ChatModel("Azim","fatih");
//        ChatModel chatModel2 = new ChatModel("Ramazan","fatih");
//        ChatModel chatModel3 = new ChatModel("Fatih terim","fatih");
//        ChatModel chatModel1 = new ChatModel("üçlü priz", "fatih");
//
//        chatModelList.add(chatModel);
//        chatModelList.add(chatModel2);
//        chatModelList.add(chatModel3);
//        chatModelList.add(chatModel);
//        chatModelList.add(chatModel2);
//        chatModelList.add(chatModel1);
//        chatModelList.add(chatModel3);
//        chatModelList.add(chatModel);
//        chatModelList.add(chatModel2);
//        chatModelList.add(chatModel3);
//        chatModelList.add(chatModel);
//        chatModelList.add(chatModel2);
//        chatModelList.add(chatModel3);
//        chatModelList.add(chatModel);
//        chatModelList.add(chatModel2);
//        chatModelList.add(chatModel3);
//        chatModelList.add(chatModel);
//        chatModelList.add(chatModel2);
//        chatModelList.add(chatModel3);

//        liveData.setValue(chatModelList);

        return liveData;
    }

//    private void getUsername(){
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//
//        Query query = reference2.child()
//    }
}
