package com.example.farmguardian.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;

import com.example.farmguardian.ViewModel.FirebaseDatabaseCallback;
import com.example.farmguardian.ViewModel.FirebaseDatabaseHelper;
import com.example.farmguardian.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText edusername, edEmail, edpassword, Edconfirm;
    Button btn;
    TextView tv;

    FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edusername = findViewById(R.id.editTextRegusername);
        edpassword = findViewById(R.id.editTextTextRegPassword);
        edEmail = findViewById(R.id.editTextEmail);
        Edconfirm = findViewById(R.id.editTextTextConfirmPassword);
        btn = findViewById(R.id.buttonRegister);
        tv = findViewById(R.id.textHaveAcc);






        btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen));




        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        startActivity(new Intent(RegisterActivity.this, LoginActivityFirebase.class));

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edusername.getText().toString();
                String password = edpassword.getText().toString();
                String email = edEmail.getText().toString();
                String confirmpass = Edconfirm.getText().toString();


                if (username.length() == 0 || password.length() == 0 || email.length() == 0 || confirmpass.length() == 0) {
                    Toast.makeText(getApplicationContext(), "All details should be filled!", Toast.LENGTH_SHORT).show();
                } else {
                    if (isValidEmail(email)) {
                        if (password.compareTo(confirmpass) == 0) {
                              registerUser(username, email, password);





                        } else {
                            Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void registerUser(String username, String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User registration successful, save profile to Firebase Realtime Database
                        firebaseDatabaseHelper.saveProfile(username, email, password, new FirebaseDatabaseCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean result) {
                                Log.d("RegisterActivity", "Registration success");//debug
                                Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(RegisterActivity.this, LoginActivityFirebase.class));
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                Log.e("RegisterActivity", "Registration failed: " + errorMessage); // debug
                                Toast.makeText(getApplicationContext(), "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // User registration failed
                        Log.e("RegisterActivity", "User registration failed: " + task.getException().getMessage());
                        Toast.makeText(getApplicationContext(), "User registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



}