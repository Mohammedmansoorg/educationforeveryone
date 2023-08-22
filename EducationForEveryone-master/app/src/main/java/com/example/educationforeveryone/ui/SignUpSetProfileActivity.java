package com.example.educationforeveryone.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educationforeveryone.LoginActivity;
import com.example.educationforeveryone.R;
import com.example.educationforeveryone.databinding.ActivitySignUpSetProfileBinding;
import com.example.educationforeveryone.ui.Profile.ProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.List;

public class SignUpSetProfileActivity extends AppCompatActivity {

    private ActivitySignUpSetProfileBinding binding;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private List<String> skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpSetProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        skills = new ArrayList<>();

        binding.btnAddSkill.setOnClickListener(view -> {
            addSkill();
        });

        binding.buttonCreateProfile.setOnClickListener(view -> {
            binding.progressBarSetprofile.setVisibility(View.VISIBLE);
            createProfile();
            binding.progressBarSetprofile.setVisibility(View.GONE);
        });

        Slidr.attach(this);
    }


    // Firebase realtime database e user bilgilerini push lar
    private void createProfile(){
        String name = binding.editTextName.getText().toString();
        String bio = binding.editTextBio.getText().toString();
        if(auth.getUid() == null){
            Toast.makeText(getApplicationContext(),"USER NOT FOUND",Toast.LENGTH_SHORT).show();
            //todo: bu durum meydana gelirse Firebase auth dan kayıt olunan emaili silip
            // yeniden sign up ekranına göndermeliyiz kullanıcıyı

        }else if(name.equals("") || bio.equals("")){
            Toast.makeText(getApplicationContext(),"you gotta fill all the blanks",Toast.LENGTH_SHORT).show();
        }else{
            ProfileModel profile = new ProfileModel(name, bio, skills);
            databaseReference.child("Users").child(auth.getUid()).setValue(profile);

            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

    }

    private void addSkill() {
        String skill = binding.editTextSkill.getText().toString().trim();

        if (!skill.isEmpty()) {
            addSkillTag(skill);
            binding.editTextSkill.setText("");
            skills.add(skill);
        }
    }

    private void addSkillTag(String skill) {
        // Create a new skill tag TextView
        TextView skillTagTextView = new TextView(this);
        skillTagTextView.setText(skill);
        skillTagTextView.setBackgroundResource(R.drawable.shape_edittext);
        skillTagTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
        skillTagTextView.setPadding(40, 20, 40, 20);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(11, 10, 11, 0);
        skillTagTextView.setLayoutParams(layoutParams);

        // Add the skill tag to the skill tags container
        binding.skillTagsContainer.addView(skillTagTextView);
        skillTagTextView.setOnLongClickListener(v -> {
            binding.skillTagsContainer.removeView(skillTagTextView);
            return true;
        });
    }
}