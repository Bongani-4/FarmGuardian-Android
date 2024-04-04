package com.example.farmguardian.Models;


public class AcaretakerModel {

    private String id;
    private String fullNames;
    private String location;
    private String contact;
    private String experience;
    private int available;


    // Default constructor
    public AcaretakerModel() {
        // Default constructor required by Firebase
    }


    public AcaretakerModel(String id, String fullNames, String location, String contact, String experience, int available) {
        this.id = id;
        this.fullNames = fullNames;
        this.location = location;
        this.contact = contact;
        this.experience = experience;
        this.available = available;
    }

    public AcaretakerModel(String fullNames, String location, String contact, String experience, int available) {
        this.fullNames = fullNames;
        this.location = location;
        this.contact = contact;
        this.experience = experience;
        this.available = available;
    }



    // Getter and setter for ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullNames() {
        return fullNames;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getExperience( ) {
        return experience;
    }

    public int isAvailable() {
        return available;
    }
}

