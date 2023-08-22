package com.example.educationforeveryone;

import android.os.Bundle;
import android.view.View;

import com.example.educationforeveryone.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.option_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.add_question){
//            Intent intentToAddQuestion = new Intent(MainActivity.this, UploadQuestionActivity.class);
//            startActivity(intentToAddQuestion);
//
//        }else if (item.getItemId() == R.id.create_course){
//            Intent intentToCreateCourse = new Intent(MainActivity.this, CreateCourseActivity.class);
//            startActivity(intentToCreateCourse);
//
//        }else if (item.getItemId() == R.id.sign_out){
//            auth.signOut();
//            Intent intentToLogin = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intentToLogin);
//            finish();
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }
}