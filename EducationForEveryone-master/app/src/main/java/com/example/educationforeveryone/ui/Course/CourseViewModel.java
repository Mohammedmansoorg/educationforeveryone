package com.example.educationforeveryone.ui.Course;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CourseViewModel extends ViewModel {

    private MutableLiveData<List<CourseModel>> liveData;
    private final CourseRepository repository;

    public CourseViewModel() {
        super();
        liveData = new MutableLiveData<>();
        repository = new CourseRepository();
        liveData = repository.getCourses();
    }

    public MutableLiveData<List<CourseModel>> getCourses() {
        System.out.println("livedata: "+liveData.getValue());
        return liveData;
    }

}