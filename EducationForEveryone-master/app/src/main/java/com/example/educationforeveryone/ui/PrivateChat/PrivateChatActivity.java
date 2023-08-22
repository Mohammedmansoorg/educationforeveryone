package com.example.educationforeveryone.ui.PrivateChat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.educationforeveryone.R;
import com.example.educationforeveryone.ui.UserProfile.UserProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.r0adkll.slidr.Slidr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PrivateChatActivity extends AppCompatActivity {

    private AppCompatImageView image_back_privatechat;
    private SwipeRefreshLayout swipe_to_refresh_privatechat;
    private RecyclerView recyclerView_privatechat;
    private AppCompatEditText edittext_privatechat;
    private AppCompatImageView image_send_privatechat;
    private AppCompatTextView text_username_privatechat;


    private MessageViewModel viewModel;
    private String currUser, otherUser, username;

    private MessageAdapter adapter;
    private List<MessageModel> messageList;
    private LinearLayout linear_privatechat;

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);

        init();
        //todo: debug için yazıldı sil
        //todo:
        viewModel = new ViewModelProvider(this,
                new ViewModelFactory(currUser, otherUser)).get(MessageViewModel.class);

//        ProgressDialog progress = new ProgressDialog(this);
//        progress.setTitle("Loading");
//        progress.setMessage("Wait while loading...");
//        progress.setIcon(R.drawable.ic_loading);
//        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();
        viewModel.getMessages().observe(this, new Observer<List<MessageModel>>() {
            @Override
            public void onChanged(List<MessageModel> messageModels) {
                updateData(messageModels);

//                progress.dismiss();
            }
        });


        setListeners();
        Slidr.attach(this);
    }

    private void updateData(List<MessageModel> messageModels){
        messageList.clear();
        messageList.addAll(messageModels);
//        System.out.println("models: "+messageModels.get(0).toString());
//        System.out.println("models: "+messageModels.get(1).toString());
        adapter = new MessageAdapter(messageList, currUser);
        recyclerView_privatechat.setAdapter(adapter);
        recyclerView_privatechat.smoothScrollToPosition(messageList.size());

    }

    private void init(){

        otherUser = getIntent().getStringExtra("otherUser");
        username = getIntent().getStringExtra("username");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            currUser = firebaseUser.getUid();
        }else{
            //todo: user giriş yapmamış gibi gözüküyor, yeniden giriş yapmasının bir yolunu bul
            // Zaman aşımına uğradı gibi bir şey söyleyip yeniden giriş yapmasını isteyebiliriz
            currUser = "";
        }
//        currUser = "terim";
//        otherUser = "fatih";
        System.out.println("other user:  "+otherUser);
        System.out.println("curr user: "+currUser);
        messageList = new ArrayList<>();
        image_back_privatechat = findViewById(R.id.image_back_privatechat);
        swipe_to_refresh_privatechat = findViewById(R.id.swipe_to_refresh_privatechat);
        recyclerView_privatechat = findViewById(R.id.recyclerView_privatechat);
        edittext_privatechat = findViewById(R.id.edittext_privatechat);
        image_send_privatechat = findViewById(R.id.image_send_privatechat);
        linear_privatechat = findViewById(R.id.linear_privatechat);
        text_username_privatechat = findViewById(R.id.text_username_privatechat);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_privatechat.setLayoutManager(layoutManager);
        getNewMessages(currUser, otherUser);

        databaseReference = FirebaseDatabase.getInstance().getReference("Messages");

        swipe_to_refresh_privatechat.setOnRefreshListener(() -> {
            //todo: refresh işlemini yap
            swipe_to_refresh_privatechat.setRefreshing(false);
        });

        text_username_privatechat.setText(username);
    }

    private void sendMessage(MessageModel message){
        String messageId = databaseReference.child(currUser).child(otherUser).push().getKey();
        if(messageId != null){
            databaseReference.child(currUser).child(otherUser)
                    .child(messageId).setValue(message).addOnCompleteListener(task -> {
                        databaseReference.child(otherUser).child(currUser).child(messageId)
                                .setValue(message).addOnCompleteListener(task1 -> {
                                    edittext_privatechat.requestFocus();
                                    edittext_privatechat.setText("");
                                    recyclerView_privatechat.smoothScrollToPosition(messageList.size());
                                    Toast.makeText(this,
                                            "message sent successfully",
                                            Toast.LENGTH_SHORT).show();
                                });
                    });
        }

    }

    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void setListeners(){
        image_send_privatechat.setOnClickListener(view -> {
            if(edittext_privatechat.getText() != null){
                String text = edittext_privatechat.getText().toString();
                String sender = currUser; //todo: bunu silcez, onun yerine
                //todo: aşağıda sender yerine direkr currUser yazcaz
                String time = getTime();
                sendMessage(new MessageModel(sender, text, time));
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this,
                        "you cannot send an empty message",Toast.LENGTH_SHORT).show();
            }

        });

        linear_privatechat.setOnClickListener(view -> {
            Intent intent = new Intent(this, UserProfileActivity.class);
            intent.putExtra("otherUser", otherUser);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void getNewMessages(String currUser, String  otherUser){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageModel messageModel = snapshot.getValue(MessageModel.class);
//                System.out.println("db: "+messageModel);
                messageList.add(messageModel);
                adapter = new MessageAdapter(messageList, currUser);
                recyclerView_privatechat.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        Query query = FirebaseDatabase.getInstance().getReference("Messages")
                .child(currUser).child(otherUser);
        query.addChildEventListener(childEventListener);
    }
}