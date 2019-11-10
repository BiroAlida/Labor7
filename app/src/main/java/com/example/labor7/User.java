package com.example.labor7;

import java.util.ArrayList;

public class User {

    private String name;
    private String location;
    private String imageUrl;
    private String date;
    private String gender;
    private ArrayList<String> hobby;
    private String department;
    private String yearOfStudy;
    private String expectations;

    public User() {}
    public User(String name, String location, String imageUrl, String date, String gender, ArrayList<String> hobby, String department, String yearOfStudy, String expectations) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.date = date;
        this.gender = gender;
        this.hobby = hobby;
        this.department = department;
        this.yearOfStudy = yearOfStudy;
        this.expectations = expectations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getHobby() {
        return hobby;
    }

    public void setHobby(ArrayList<String> hobby) {
        this.hobby = hobby;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getExpectations() {
        return expectations;
    }

    public void setExpectations(String expectations) {
        this.expectations = expectations;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

