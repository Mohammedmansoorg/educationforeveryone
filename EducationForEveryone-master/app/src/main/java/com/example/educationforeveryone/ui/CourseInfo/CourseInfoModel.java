package com.example.educationforeveryone.ui.CourseInfo;

public class CourseInfoModel {

    private String publisher, bio, courseName, date, category, publisher_id;

    public CourseInfoModel(String publisher, String bio, String courseName,
                           String date, String category, String publisher_id) {
        this.publisher = publisher;
        this.bio = bio;
        this.courseName = courseName;
        this.date = date;
        this.category = category;
        this.publisher_id = publisher_id;
    }

    public CourseInfoModel(){}


    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
