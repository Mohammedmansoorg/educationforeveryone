package com.example.educationforeveryone.ui.CourseInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.educationforeveryone.ui.Course.CourseModel;
import com.example.educationforeveryone.ui.UserProfile.UserProfileModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseInfoRepository {

    private final MutableLiveData<CourseInfoModel> liveData;
    private DatabaseReference reference;

    public CourseInfoRepository(String courseId){
        liveData = new MutableLiveData<>();
        reference = FirebaseDatabase.getInstance().getReference();
        setData(courseId);
    }

    private void setData(String courseId) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("snapshot values   :" + snapshot.toString());
                CourseInfoModel courseInfoModel = snapshot.getValue(CourseInfoModel.class);

                liveData.setValue(courseInfoModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        Query query = reference.child("Course").child(courseId);
        query.addListenerForSingleValueEvent(valueEventListener);




//        CourseInfoModel course = new CourseInfoModel("fatih terim", "tanrı",
//                "how to fuck fenerbahçe properly",
//                "31.31.3131","adult", "w7dBn3NPEhdGC94yWNmeN1vnt7I3");
//
//        liveData.setValue(course);
    }

    public MutableLiveData<CourseInfoModel> getLiveData(){
        return liveData;
    }

}
