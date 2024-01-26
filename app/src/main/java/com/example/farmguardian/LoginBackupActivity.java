//backup login

package com.example.farmguardian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class LoginBackupActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Database db;

    EditText edusername, edpassword;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        db = new Database(getApplicationContext(), "FarmGuardian", null, 1); // Initialize Database with application context



        edusername = findViewById(R.id.editTextusername);
        edpassword = findViewById(R.id.editTextTextPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textRegister);

        if (isLoggedIn()) {
            navigateToHome();
        }

        btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.DarkGreen));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edusername.getText().toString();
                String password = edpassword.getText().toString();

                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "All details should be filled!", Toast.LENGTH_SHORT).show();
                } else {
                    // Pass the username to the login method
                    if (db.login(username, password) == 1) {
                        // Save logged-in user in SharedPreferences
                        saveUsernameToSharedPreferences(username);

                        // Navigating to HomeActivity
                        startActivity(new Intent(LoginBackupActivity.this, HomeActivity.class));
                        finish(); // Finish LoginBackupActivity to prevent going back
                    } else {
                        Toast.makeText(getApplicationContext(), "Unknown user", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginBackupActivity.this, RegisterActivity.class));
            }
        });
    }

    private void saveUsernameToSharedPreferences(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    private boolean isLoggedIn() {

        return sharedPreferences.contains("username");
    }
    private void navigateToHome() {
        startActivity(new Intent(LoginBackupActivity.this, HomeActivity.class));
        finish(); // Finish LoginBackupActivity to prevent going back
    }
}
