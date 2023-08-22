package com.example.educationforeveryone.ui.Course;

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
import com.example.educationforeveryone.ui.Chats.ChatModel;
import com.example.educationforeveryone.ui.CourseInfo.CourseInfoActivity;
import com.example.educationforeveryone.ui.UserProfile.UserProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.Viewholder> implements Filterable {

    private List<CourseModel> courseModelList;

    private List<CourseModel> courseModelListFull;

    private View view;

    Activity activity;

    android.content.Context context;

    public CourseAdapter(List<CourseModel> courseModelList, Activity activity, Context context) {
        this.courseModelList = courseModelList;
        courseModelListFull = new ArrayList<>(courseModelList);
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_post_recycler_row,
                parent,false);

        return new CourseAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    holder.text_courseName.setText(courseModelList.get(position).getCourseName());
    holder.text_publisher.setText(courseModelList.get(position).getPublisher());

    holder.constraint_course_item.setOnClickListener(view1 -> {
        Intent intent = new Intent(context, CourseInfoActivity.class);
        intent.putExtra("courseId",courseModelList.get(position).getCourseId());
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    });

    }

    @Override
    public int getItemCount() {
        return courseModelList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        private final AppCompatTextView text_courseName, text_publisher;
        private final ConstraintLayout constraint_course_item;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            text_publisher = itemView.findViewById(R.id.text_publisher);
            text_courseName = itemView.findViewById(R.id.text_courseName);
//            text_view_email = itemView.findViewById(R.id.recyclerViewUserEmailText);
            constraint_course_item = itemView.findViewById(R.id.constraint_course_item);
        }


    }

    public Filter getFilter(){
        return courseFilter;
    }

    private Filter courseFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<CourseModel> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(courseModelListFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(CourseModel item : courseModelListFull){
                    if(item.getCourseName().toLowerCase().contains(filterPattern)){
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
            courseModelList.clear();
            courseModelList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


}
