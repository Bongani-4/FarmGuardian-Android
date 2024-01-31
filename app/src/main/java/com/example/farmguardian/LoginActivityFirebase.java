package com.example.farmguardian;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    TextView tv, forgotpasswordTV; //"don't have account yet? or password forgotten"


    private static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);


        edusername = findViewById(R.id.editTxtEMAIL);
        edpassword = findViewById(R.id.editTextTextPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textRegister);
        forgotpasswordTV = findViewById(R.id.Forgotpasword);

        btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen));




        if (isLoggedIn()) {
            startActivity(new Intent(LoginActivityFirebase.this, HomeActivity.class));
            finish();
            return;
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInternetConnected()) {
                    String username = edusername.getText().toString();
                    String password = edpassword.getText().toString();

                    if (username.length() == 0 || password.length() == 0) {
                        Toast.makeText(getApplicationContext(), "All details should be filled!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Authenticate user with Firebase
                        firebaseAuth.signInWithEmailAndPassword(username, password)
                                .addOnCompleteListener(LoginActivityFirebase.this, task -> {
                                    if (task.isSuccessful()) {
                                        // Fetch user details from Realtime DB
                                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                                        if (currentUser != null) {
                                            getUserDetails(currentUser);

                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }else {
                    showNoInternetDialog();

                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityFirebase.this, RegisterActivity.class));
            }
        });

        //password reset

        forgotpasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reset password logic
                showForgotPasswordDialog();
            }
        });
    }

    private void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot password");
        builder.setMessage("\nEnter your email address");
        final EditText emailEditText = new EditText(this);
        builder.setView(emailEditText);

        builder.setPositiveButton("Set new Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = emailEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    sendPasswordResetEmail(email);
                } else {
                    Toast.makeText(LoginActivityFirebase.this, "Enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void sendPasswordResetEmail(String email) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivityFirebase.this, "To reset your password. Check your email.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivityFirebase.this, "Failed to send password reset email.", Toast.LENGTH_SHORT).show();
                        }
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


    private void getUserDetails(FirebaseUser currentUser) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String email = userSnapshot.child("email").getValue(String.class);
                    String username = userSnapshot.child("username").getValue(String.class);


                    if (email.equals(currentUser.getEmail()) || username.equals(currentUser.getDisplayName())) {
                        Toast.makeText(getApplicationContext(), "Welcome, " + username, Toast.LENGTH_SHORT).show();

                        saveLoginStatus(getApplicationContext(),true);

                        startActivity(new Intent(LoginActivityFirebase.this, HomeActivity.class));
                        finish();
                        return;
                    }
                }


                Toast.makeText(getApplicationContext(), "User not found in the database.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error fetching user details.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void saveLoginStatus(Context context, boolean isLoggedIn){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        if (isLoggedIn) {
            long currentTimeMillis = System.currentTimeMillis();
            editor.putLong("loginTime", currentTimeMillis);
        } else {

            editor.remove("loginTime");
        }
        editor.apply();
    }




    private boolean isLoggedIn() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            long loginTime = sharedPreferences.getLong("loginTime", 0);
            long currentTimeMillis = System.currentTimeMillis();


            long sessionDuration = 24 * 60 * 60 * 1000;

            //has session expired?
            isLoggedIn = (currentTimeMillis - loginTime) < sessionDuration;
        }

        return isLoggedIn;
    }




    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
            } else {

                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }
        }

        return false;
    }
    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No  Internet Connection");
        builder.setMessage("To use Farm Guardian,turn on mobile data or connect to Wi-Fi.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
