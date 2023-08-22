package com.example.educationforeveryone.ui.CourseInfo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.educationforeveryone.ui.UserProfile.UserProfileModel;
import com.example.educationforeveryone.ui.UserProfile.UserProfileRepository;

public class CourseInfoViewModel extends ViewModel {

    private final MutableLiveData<CourseInfoModel> liveData;

    public CourseInfoViewModel(String courseId){
        CourseInfoRepository repository = new CourseInfoRepository(courseId);
        liveData = repository.getLiveData();
    }

    public MutableLiveData<CourseInfoModel> getData(){ return liveData;}
}
