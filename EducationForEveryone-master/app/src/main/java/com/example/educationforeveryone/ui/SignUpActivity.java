package com.example.educationforeveryone.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Slide;
import android.view.View;
import android.widget.Toast;

import com.example.educationforeveryone.LoginActivity;
import com.example.educationforeveryone.R;
import com.example.educationforeveryone.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.r0adkll.slidr.Slidr;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        setSupportActionBar(binding.toolbarActivitySignup);
        try{
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null)
                actionBar.setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            // setDisplayHomeAsUpEnabled method could not set
        }

        binding.textAlreadyHaveAcc.setOnClickListener(view -> openLogin());
        signUp();

        Slidr.attach(this);
    }

    public void signUp(){
        binding.buttonSignup.setOnClickListener(v -> {
            String email = binding.edittextEmail.getText().toString().trim();
            String password =  binding.edittextPassword.getText().toString().trim();
            String password2 = binding.edittextPassword2.getText().toString().trim();

            binding.progressBarSignup.setVisibility(View.VISIBLE);

            if(TextUtils.isEmpty(email)) {
                binding.edittextEmail.setError("Email is required");
                return;

            }
            if(TextUtils.isEmpty(password)) {
                binding.edittextPassword.setError("Password is required");
                return;
            }
            if(password.length() < 8) {
                binding.edittextPassword2.setError("Password must be at least 8 characters");
                return;
            }
            if(!password.equals(password2)) {
                Toast.makeText(SignUpActivity.this, "Passwords must be the same!",Toast.LENGTH_SHORT).show();
                return;
            }


//            progressBar2.setVisibility(View.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(task -> {

                if(task.isSuccessful()) {
                    binding.progressBarSignup.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(),SignUpSetProfileActivity.class));
                }
                else {
                    binding.progressBarSignup.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "Error!" , Toast.LENGTH_LONG).show();

                }
            });
        });
    }

    public void openLogin(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

}