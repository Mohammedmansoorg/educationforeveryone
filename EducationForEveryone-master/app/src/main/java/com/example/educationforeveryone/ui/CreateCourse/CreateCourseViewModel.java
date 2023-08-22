package com.example.educationforeveryone.ui.CreateCourse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateCourseViewModel extends ViewModel {

    private MutableLiveData<String> liveData;

    public CreateCourseViewModel(){
        liveData = new MutableLiveData<>();
        Repository repository = new Repository();
        liveData = repository.getLiveData();
    }

    public MutableLiveData<String> getUserName(){

        return liveData;
    }
}
