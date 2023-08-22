package com.example.educationforeveryone.ui.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.educationforeveryone.R;
import com.example.educationforeveryone.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.getData().observe(getViewLifecycleOwner(), this::updateData);

        return root;
    }

    @SuppressLint("SetTextI18n")
    private void updateData(HomeModel homeModel){
        binding.textTyt.setText("TYT: " + homeModel.getTyt());
        binding.textAyt.setText("AYT: " + homeModel.getAyt());
    }
}