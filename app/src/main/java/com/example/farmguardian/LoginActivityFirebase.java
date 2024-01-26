package com.example.farmguardian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.FileInputStream;
import java.io.IOException;

public class LoginActivityFirebase extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText edusername, edpassword;
    private Button btn;
    TextView tv; //"don't have account yet?"



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        edusername = findViewById(R.id.editTextusername);
        edpassword = findViewById(R.id.editTextTextPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textRegister);

        btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen));



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edusername.getText().toString();
                String password = edpassword.getText().toString();

                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "All details should be filled!", Toast.LENGTH_SHORT).show();
                } else {
                    // Authenticate user with Firebase
                    firebaseAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(LoginActivityFirebase.this, task -> {
                                if (task.isSuccessful()) {
                                    // Fetch user details from Realtime Database
                                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                    if (currentUser != null) {
                                        getUserDetails(currentUser.getUid());
                                    }
                                } else {
                                    Log.e("AuthenticationError", "Failed: " + task.getException());

                                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityFirebase.this, RegisterActivity.class));
            }
        });




    }

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user == null) {

                Toast.makeText(getApplicationContext(), "You are not signed in.", Toast.LENGTH_SHORT).show();


            }
        }
    };


    private void getUserDetails(String userId) {
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    String username = snapshot.child("username").getValue(String.class);


                    Toast.makeText(getApplicationContext(), "Welcome, " + username, Toast.LENGTH_SHORT).show();

                    // Proceed to the next activity

                    // Navigating to HomeActivity
                    startActivity(new Intent(LoginActivityFirebase.this, HomeActivity.class));
                    finish(); // Finish LoginBackupActivity to prevent going back

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error fetching user details.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
