package com.example.educationforeveryone.ui.UserProfile;

import java.util.List;

public class UserProfileModel {

    private String username, bio;
    private List<String> skills;

    public UserProfileModel(String username, String bio, List<String> skills) {
        this.username = username;
        this.bio = bio;
        this.skills = skills;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
