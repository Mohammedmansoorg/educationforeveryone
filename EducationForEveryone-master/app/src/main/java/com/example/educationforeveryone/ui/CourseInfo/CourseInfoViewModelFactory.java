package com.example.educationforeveryone.ui.CourseInfo;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.educationforeveryone.ui.PrivateChat.MessageViewModel;

public class CourseInfoViewModelFactory implements ViewModelProvider.Factory {

    private String courseId;

    public CourseInfoViewModelFactory(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CourseInfoViewModel(courseId);
    }

}
