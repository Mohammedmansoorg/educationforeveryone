package com.example.educationforeveryone.ui.Course;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.educationforeveryone.R;
import com.example.educationforeveryone.databinding.FragmentCourseListBinding;
import com.example.educationforeveryone.ui.CreateCourse.CreateCourseActivity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class CourseListFragment extends Fragment {

    private List<CourseModel> courseList;
    private FragmentCourseListBinding binding;

    private CourseAdapter courseAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCourseListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        init();
        courseViewModel.getCourses().observe(getViewLifecycleOwner(), courseModels -> {
           courseList.clear();
           courseList.addAll(courseModels);
            System.out.println("models.."+courseModels);
           courseAdapter = new CourseAdapter(courseList, getActivity(), getContext());
           binding.recyclerViewChatsFragment.setAdapter(courseAdapter);
       });

        return root;
    }

    private void init(){
        courseList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewChatsFragment.setLayoutManager(layoutManager);

        binding.floatingButtonAddcourse.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), CreateCourseActivity.class));
        });
        MaterialToolbar toolbar = binding.toolbarCoursesFragment;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//
    }

    // call onCreateOptionsMenu
    //todo: menu nun null gelmesinden kaynaklanan hatadan ötürü burayı yoruma alıyorum
    //todo:
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
//        super.onCreate(savedInstanceState);
//    }

    // set search feature
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.search_courses);
        System.out.println(menu.hasVisibleItems());
        System.out.println(menu.getItem(0));
        searchItem.getItemId();
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                courseAdapter.getFilter().filter(s);
                return false;
            }
        });
    }


//
//    public void getData() {
//        firebaseFirestore.collection("coursePost").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                }
//                if (value != null) {
//                    for (DocumentSnapshot snapshot : value.getDocuments()) {
//                        Map<String, Object> data = snapshot.getData();
//                        String email = (String) data.get("usermail");
//                        String lesson = (String) data.get("lesson");
//                        String year = (String) data.get("year");
//                        Course post = new Course(email, year, lesson);
//                        courseListe.add(post);
//
//                    }
//                    coursePostAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}