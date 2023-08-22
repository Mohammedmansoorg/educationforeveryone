package com.example.educationforeveryone.ui.CreateCourse;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.educationforeveryone.ui.Profile.ProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

// kullanıcı bir kere app i açtığında birden fazla kez kurs eklemek isteyebilir
// ve kurs eklerken kullanıcının db deki username i gerekli, her seferinde yeniden
// indirme yapmamak adına ViewModel kullanacağız
public class Repository {

    private MutableLiveData<String> liveData;

    public Repository(){
        liveData = new MutableLiveData<>();
        getData();
    }

    public MutableLiveData<String> getLiveData(){
        return liveData;
    }

    private void getData(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("username").getValue().toString();
                liveData.setValue(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(valueEventListener);
    }
}
