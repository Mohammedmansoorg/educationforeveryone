package com.example.educationforeveryone.ui.UserProfile;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.educationforeveryone.ui.Profile.ProfileModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileRepository {

    private final MutableLiveData<UserProfileModel> liveData;
    private DatabaseReference reference;
    private List<String> skillList;

    public UserProfileRepository(String otherUser){
        liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        skillList = new ArrayList<>();
        setData(otherUser);
    }

    private void setData(String otherUser) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("username").getValue().toString();
                String bio = snapshot.child("bio").getValue().toString();
                for(DataSnapshot dataSnapshot : snapshot.child("skills").getChildren()){
                    skillList.add(dataSnapshot.getValue().toString());
                }
                UserProfileModel profileModel = new UserProfileModel(username, bio, skillList);
                liveData.setValue(profileModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        System.out.println("other user repos: "+otherUser);
        Query query = reference.child(otherUser); //todo: otherUser
        query.addListenerForSingleValueEvent(valueEventListener);

//
//        List<String> skills = new ArrayList<>();
//        skills.add("futbol");
//        skills.add("am");
//        skills.add("italyanca");
//        skills.add("fenerbahçe sikmek");
//        UserProfileModel profileModel = new UserProfileModel("Fatih Terim","Türkiyenin en taşşaklı tanrısı",skills);
//        liveData.setValue(profileModel);

    }

    public MutableLiveData<UserProfileModel> getLiveData(){
        return liveData;
    }

}
