package com.example.educationforeveryone.ui.CourseInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.educationforeveryone.R;
import com.example.educationforeveryone.databinding.ActivityCourseInfoBinding;
import com.example.educationforeveryone.ui.PrivateChat.PrivateChatActivity;
import com.example.educationforeveryone.ui.UserProfile.UserProfileActivity;
import com.r0adkll.slidr.Slidr;

public class CourseInfoActivity extends AppCompatActivity {

    ActivityCourseInfoBinding binding;
    CourseInfoViewModel viewModel;
    private String otherUser, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String courseId = getIntent().getStringExtra("courseId");

        viewModel = new ViewModelProvider(this,
                new CourseInfoViewModelFactory(courseId)).get(CourseInfoViewModel.class);

        viewModel.getData().observe(this, courseInfoModel -> {
            updateData(courseInfoModel);
            otherUser = courseInfoModel.getPublisher_id();
            username = courseInfoModel.getPublisher();
            Toast.makeText(CourseInfoActivity.this,
                    "user:" + otherUser,Toast.LENGTH_SHORT).show();
        });

        setListeners();
        Slidr.attach(this);
    }

    private void updateData(CourseInfoModel courseInfoModel){
        binding.textCourseName.setText(courseInfoModel.getCourseName());
        binding.textBio.setText(courseInfoModel.getBio());
        binding.textCategory.setText(courseInfoModel.getCategory());
    }

    private void setListeners(){
        binding.msgBtn.setOnClickListener(view -> {
            if(otherUser != null){
                Intent intent = new Intent(this, PrivateChatActivity.class);
                intent.putExtra("otherUser", otherUser);
                intent.putExtra("username",username);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        binding.visitBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra("otherUser", otherUser);
            intent.putExtra("username",username);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }
}