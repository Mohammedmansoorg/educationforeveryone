package com.example.educationforeveryone.ui.Home;

public class HomeModel {

    private String tyt, ayt;

    public HomeModel(String tyt, String ayt) {
        this.tyt = tyt;
        this.ayt = ayt;
    }

    public String getTyt() {
        return tyt;
    }

    public void setTyt(String tyt) {
        this.tyt = tyt;
    }

    public String getAyt() {
        return ayt;
    }

    public void setAyt(String ayt) {
        this.ayt = ayt;
    }
}
