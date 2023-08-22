package com.example.educationforeveryone.ui.CreateCourse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.educationforeveryone.MainActivity;
import com.example.educationforeveryone.databinding.ActivityCreateCourseBinding;
import com.example.educationforeveryone.ui.CourseInfo.CourseInfoModel;
import com.example.educationforeveryone.ui.PrivateChat.MessageViewModel;
import com.example.educationforeveryone.ui.PrivateChat.ViewModelFactory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.r0adkll.slidr.Slidr;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateCourseActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private ActivityCreateCourseBinding binding;
    private String category;
    private CreateCourseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateCourseBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
        setListeners();
    }

    private void init(){
        category = "Grades: ";
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Course");
        viewModel = new ViewModelProvider(this).get(CreateCourseViewModel.class);
        Slidr.attach(this);
    }


    private void setListeners(){
        binding.buttonCreateCourseSave.setOnClickListener(view -> {
            if(binding.editTextCourseName.getText() != null
                    && binding.editTextCourseBio.getText() != null){
                getCheckedBoxes();
                createCourse();
                startActivity(new Intent(this, MainActivity.class));
                System.out.println(category);
            }else{
                Toast.makeText(this, "Please fill all the blanks"
                        , Toast.LENGTH_SHORT).show();
            }

        });

    }

    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void getCheckedBoxes(){

        // Check if each checkbox is checked and add it to the corresponding list
        if (binding.checkBox8.isChecked()) {
            category = category + " 8";
        }
        if (binding.checkBox9.isChecked()) {
            category = category + " 9";
        }
        if (binding.checkBox10.isChecked()) {
            category = category + " 10";
        }
        if (binding.checkBox11.isChecked()) {
            category = category + " 11";
        }
        if (binding.checkBox12.isChecked()) {
            category = category + " 12";
        }
        category = category + "   Subjects: ";
        if (binding.checkBoxMath.isChecked()) {
            category = category + " Math";
        }
        if (binding.checkBoxPhysics.isChecked()) {
            category = category + " Physics";
        }
        if (binding.checkBoxChem.isChecked()) {
            category = category + " Chemistry";
        }
        if (binding.checkBoxBiology.isChecked()) {
            category = category + " Biology";
        }
        if (binding.checkBoxTurkish.isChecked()) {
            category = category + " Turkish";
        }

    }

    private void createCourse() {

        String bio = binding.editTextCourseBio.getText().toString();
        String courseName = binding.editTextCourseName.getText().toString();
        final String[] publisher = new String[1];
        viewModel.getUserName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                publisher[0] = s;
            }
        });
        //todo: firebase bağlantısı
        String courseID = reference.push().getKey();
        CourseInfoModel courseInfoModel = new CourseInfoModel(publisher[0],
                bio, courseName, getTime(), category, auth.getUid());
        if (courseID != null){
            reference.child(courseID).setValue(courseInfoModel).addOnSuccessListener(unused -> {
                Toast.makeText(this,
                        "course created successfully",
                        Toast.LENGTH_SHORT).show();
            });
        }
    }


}