package com.example.educationforeveryone.ui.Chats;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.educationforeveryone.R;
import com.example.educationforeveryone.databinding.FragmentChatsBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    private FragmentChatsBinding binding;
    private ChatAdapter adapter;
    private List<ChatModel> chatModelList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChatsViewModel chatsViewModel =
                new ViewModelProvider(this).get(ChatsViewModel.class);

        binding = FragmentChatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();

//          ProgressDialog progress = new ProgressDialog(getContext());
//        progress.setTitle("Loading");
//        progress.setMessage("Wait while loading...");
//        progress.setIcon(R.drawable.ic_loading);
//        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();
        chatsViewModel.getChats().observe(getViewLifecycleOwner(), new Observer<List<ChatModel>>() {
            @Override
            public void onChanged(List<ChatModel> chatModels) {
                chatModelList.clear();
                chatModelList.addAll(chatModels);
                adapter = new ChatAdapter(chatModelList, getActivity(), getContext());
                binding.recyclerViewChatsFragment.setAdapter(adapter);
//                progress.dismiss();
            }
        });

//        binding.textDemo.setOnClickListener(view -> {
//            getActivity().startActivity(new Intent(getContext(), PrivateChatActivity.class));
//        });


        return root;
    }

    private void init(){
        chatModelList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewChatsFragment.setLayoutManager(layoutManager);

        MaterialToolbar toolbar = binding.toolbarChatsFragment;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    private void updateData(List<ChatModel> chatModels){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    // set search feature
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_chats,menu);
        MenuItem searchItem = menu.findItem(R.id.search_chats);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}