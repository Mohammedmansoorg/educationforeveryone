package com.example.educationforeveryone.ui.Chats;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educationforeveryone.R;
import com.example.educationforeveryone.ui.PrivateChat.PrivateChatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> implements Filterable {

    private List<ChatModel> chatModelList;
    private List<ChatModel> chatModelListFull;
    private View view;
    Activity activity;
    Context context;

    public ChatAdapter(List<ChatModel> chatModelList, Activity activity, Context context){
        this.chatModelList = chatModelList;
        this.activity = activity;
        this.context = context;
        chatModelListFull = new ArrayList<>(chatModelList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.text_username_chat_item.setText(chatModelList.get(position).getUsername());
        }catch (Exception e){
            holder.text_username_chat_item.setText("username could not loaded");
        }

        holder.constraint_chat_item.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, PrivateChatActivity.class);
            intent.putExtra("otherUser", chatModelList.get(position).getUserId());
            intent.putExtra("username",chatModelList.get(position).getUsername());
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        if(chatModelList.size() == 0){
            holder.text_username_chat_item.setText("there is no chat yet");
        }
    }

    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView text_username_chat_item;
        private ConstraintLayout constraint_chat_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_username_chat_item = itemView.findViewById(R.id.text_username_chat_item);
            constraint_chat_item = itemView.findViewById(R.id.constraint_chat_item);
        }
    }

    public Filter getFilter(){
        return postFilter;
    }

    private Filter postFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ChatModel> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(chatModelListFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(ChatModel item : chatModelListFull){
                    if(item.getUsername().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            chatModelList.clear();
            chatModelList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


}
