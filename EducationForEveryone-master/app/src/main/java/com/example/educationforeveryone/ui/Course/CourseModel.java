package com.example.educationforeveryone.ui.Course;

public class CourseModel {
    private  String publisher, courseName, courseId;

    public CourseModel(String publisher, String courseName, String courseId) {
        this.publisher = publisher;
        this.courseName = courseName;
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
