package com.example.educationforeveryone.ui.Course;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository {


    private final MutableLiveData<List<CourseModel>> liveData;
    private final List<CourseModel> courseModelList;

    private DatabaseReference reference;

    public CourseRepository() {
        this.liveData = new MutableLiveData<>();
        this.courseModelList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();
    }


    public MutableLiveData<List<CourseModel>> getCourses(){

        // courseName ve publisher

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String publisher = dataSnapshot.child("publisher").getValue().toString();
                    String courseName = dataSnapshot.child("courseName").getValue().toString();
                    CourseModel courseModel = new CourseModel(publisher, courseName, dataSnapshot.getKey());
                    courseModelList.add(courseModel);
                }liveData.setValue(courseModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        Query query = reference.child("Course");
        query.addListenerForSingleValueEvent(valueEventListener);




        return liveData;


    }
}
