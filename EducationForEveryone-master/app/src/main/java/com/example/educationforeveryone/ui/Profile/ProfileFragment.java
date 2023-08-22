package com.example.educationforeveryone.ui.Profile;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.educationforeveryone.LoginActivity;
import com.example.educationforeveryone.R;
import com.example.educationforeveryone.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private DatabaseReference reference;
    private FirebaseUser user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        ProfileFragmentViewModel viewModel = new ViewModelProvider(this)
                .get(ProfileFragmentViewModel.class);

        if(savedInstanceState != null){
            String saved = savedInstanceState.getString("saved");
            Toast.makeText(getContext(),saved,Toast.LENGTH_SHORT).show();
            binding.textSkills.setText(saved);
        }else{

            viewModel.getData().observe(getViewLifecycleOwner(), this::updateData);
        }

        setListeners();

        return root;
    }

    private void setListeners() {
        binding.buttonLogout.setOnClickListener(view -> {
            //todo: log out
            FirebaseAuth.getInstance().signOut();
            getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } );

        binding.buttonSave.setOnClickListener(view -> {
            //todo: save info
            if(binding.textUsername.getText() != null && binding.textSkills.getText() != null
                    && binding.textBio.getText() != null){
                saveData();
            }else{
                Toast.makeText(getContext(), "you gotta fill all the blanks"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData(ProfileModel profileModel){
        binding.textUsername.setText(profileModel.getUsername());
        binding.textBio.setText(profileModel.getBio());
        StringBuilder temp = new StringBuilder();
        for(String str : profileModel.getSkills()){
            temp.append(str).append(", ");
        }
        binding.textSkills.setText(temp);


    }

    // save data into the database
    private void saveData(){
       String username = binding.textUsername.getText().toString();
       String bio = binding.textBio.getText().toString();
       String skills = binding.textSkills.getText().toString();


       List<String> itemList = Arrays.asList(skills.split(",\\s*"));

       ProfileModel profileModel = new ProfileModel(username, bio, itemList);
       reference.child(user.getUid()).setValue(profileModel);
//       cleanEditTexts();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("saved", binding.textSkills.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}