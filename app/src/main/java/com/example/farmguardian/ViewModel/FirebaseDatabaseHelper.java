package com.example.farmguardian.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.farmguardian.Models.AcaretakerModel;
import com.example.farmguardian.Models.FirebaseUserModel;
import com.google.firebase.database.*;
import java.util.List;
import java.util.ArrayList;
import com.github.javafaker.Faker;




public class FirebaseDatabaseHelper {



    private final DatabaseReference databaseReference;


    public FirebaseDatabaseHelper() {

        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void addSampleData() {
        for (int i = 0; i < 20; i++) {
            String caretakerId = databaseReference.child("ACUser").push().getKey();
            if (caretakerId != null) {
                AcaretakerModel caretaker = generateRandomCaretaker();
                databaseReference.child("ACUser").child(caretakerId).setValue(caretaker)
                        .addOnSuccessListener(aVoid -> Log.d("FirebaseDatabaseHelper", "Sample data added successfully"))
                        .addOnFailureListener(e -> Log.e("FirebaseDatabaseHelper", "Error adding sample data: " + e.getMessage()));
            }
        }
    }

    // Generate a random caretaker for sample data
    private AcaretakerModel generateRandomCaretaker() {
        Faker faker = new Faker();
        return new AcaretakerModel(
                faker.name().fullName(),
                faker.phoneNumber().cellPhone(),
                faker.address().city(),
                String.valueOf(faker.number().randomDigitNotZero()),
                faker.bool().bool() ? 1 : 0 //  boolean to integer
        );
    }


    public void saveProfile(String username, String email, String password, FirebaseDatabaseCallback<Boolean> callback) {
        String userId = databaseReference.child("users").push().getKey();

        if (userId != null) {
            FirebaseUserModel user = new FirebaseUserModel(username, email, password);
            databaseReference.child("users").child(userId).setValue(user)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("FirebaseDatabaseHelper", "Profile saved successfully");
                        callback.onSuccess(true);
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FirebaseDatabaseHelper", "Error saving profile: " + e.getMessage());
                        callback.onFailure("Error saving profile");
                    });
        }
    }
    public void saveProfileAC( String contacts, String location, String fullnames, String experience, int CBavailable) {
        String caretakerId = databaseReference.child("ACUser").push().getKey();

        if (caretakerId != null) {
            AcaretakerModel caretaker = new AcaretakerModel(fullnames,  location,contacts , experience, CBavailable);
            databaseReference.child("ACUser").child(caretakerId).setValue(caretaker)
                    .addOnSuccessListener(aVoid -> Log.d("FirebaseDatabaseHelper", "Profile saved successfully"))
                    .addOnFailureListener(e -> Log.e("FirebaseDatabaseHelper", "Error saving profile: " + e.getMessage()));
        }
    }

    // Get a list of caretakers from Firebase Realtime Database
    public void getAcaretakerList(FirebaseDatabaseCallback<List<AcaretakerModel>> callback) {
        databaseReference.child("ACUser").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<AcaretakerModel> caretakerList = new ArrayList<>();

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    AcaretakerModel caretaker = childSnapshot.getValue(AcaretakerModel.class);
                    if (caretaker != null) {
                        caretakerList.add(caretaker);
                    }
                }

                callback.onSuccess(caretakerList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("FirebaseDatabaseHelper", "Error fetching Animal caretaker list: " + error.getMessage());
                callback.onFailure("Error fetching Animal caretaker list");
            }
        });
    }

    public void getUsersList(FirebaseDatabaseCallback<List<FirebaseUserModel>> callback) {
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FirebaseUserModel> userList = new ArrayList<>();

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    FirebaseUserModel user = childSnapshot.getValue(FirebaseUserModel.class);
                    if (user != null) {
                        userList.add(user);
                    }
                }

                callback.onSuccess(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseDatabaseHelper", "Error fetching user list: " + error.getMessage());
                callback.onFailure("Error fetching user list");
            }
        });
    }
}