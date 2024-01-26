package com.example.farmguardian;

public class FirebaseUserModel {

    private String username;
    private String email;
    private String password;

    // Empty constructor for Firebase
    public FirebaseUserModel() {
    }

    public FirebaseUserModel(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
