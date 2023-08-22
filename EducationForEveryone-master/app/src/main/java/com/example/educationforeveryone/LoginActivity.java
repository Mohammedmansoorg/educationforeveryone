package com.example.educationforeveryone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.educationforeveryone.databinding.ActivityLoginBinding;
import com.example.educationforeveryone.ui.SignUpActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;

    private String email_user, password_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        init();
        openSignIn();
        login();

        binding.textResetPass.setOnClickListener(view1 -> {
            email_user = binding.edittextEmail.getText().toString().trim();
            resetPassword(email_user);
        });


    }

    private void resetPassword(String email) {
        //TODO: reset password yazılacak
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(getApplicationContext(),
                        "password reset email been sented, please check your mail box",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),
                        "email could not sented, please check the accuracy of your email",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        firebaseAuth = FirebaseAuth.getInstance();
        setSupportActionBar(binding.toolbarActivityLogin);
    }

    public void login(){
        binding.buttonLogin.setOnClickListener(v -> {

            email_user = binding.edittextEmail.getText().toString().trim();
            password_user = binding.edittextPassword.getText().toString().trim();
            binding.progressBarLogin.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(email_user)) {
                binding.edittextEmail.setError("Email is required");
            }
            if (TextUtils.isEmpty(password_user)) {
                binding.edittextPassword.setError("Password is required");
            }
            if (binding.edittextPassword.length() < 8) {
                binding.edittextPassword.setError("Password must be at least 8 characters");
            }

            try {
                firebaseAuth.signInWithEmailAndPassword(email_user , password_user).addOnCompleteListener(task -> {

                    if(task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this,
                                "Logged in Successfully",Toast.LENGTH_SHORT).show();
                        binding.progressBarLogin.setVisibility(View.GONE);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                    else {
                        Toast.makeText(LoginActivity.this,
                                "Error!", Toast.LENGTH_LONG).show();
                        binding.progressBarLogin.setVisibility(View.GONE);
                    }
                });
            }

            catch (IllegalArgumentException e) {
                Toast.makeText(LoginActivity.this, "You must enter the password",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    public void openSignIn() {
        binding.textCreateAcc.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }
}