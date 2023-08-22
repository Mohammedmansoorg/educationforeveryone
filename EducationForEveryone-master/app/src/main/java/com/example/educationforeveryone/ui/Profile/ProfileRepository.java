package com.example.educationforeveryone.ui.Profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileRepository {

    private final MutableLiveData<ProfileModel> liveData;
    private List<String> skillList;

    public ProfileRepository(){
        liveData = new MutableLiveData<>();
        skillList = new ArrayList<>();
        setData();
    }

    private void setData() {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("username").getValue().toString();
                String bio = snapshot.child("bio").getValue().toString();
                for(DataSnapshot dataSnapshot : snapshot.child("skills").getChildren()){
                    skillList.add(dataSnapshot.getValue().toString());
                }
                ProfileModel profileModel = new ProfileModel(username, bio, skillList);
                liveData.setValue(profileModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(valueEventListener);
//        query.removeEventListener(valueEventListener);


    }

    public MutableLiveData<ProfileModel> getLiveData(){
        return liveData;
    }

}
