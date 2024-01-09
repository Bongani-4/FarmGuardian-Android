package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;

public class RegisterActivity extends AppCompatActivity {
    EditText edusername, edEmail, edpassword, Edconfirm;
    Button btn;
    TextView tv;
    Database db;

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

        // Initialize Database instance
        db = new Database(getApplicationContext(), "FarmGuardian", null, 1);



        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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
                            if (Isvalid(password)) {
                                // Call the register method in the Database class
                                registerUser(username, email, password);
                            } else {
                                Toast.makeText(getApplicationContext(), "Passwords must contain at least 8 characters, having a letter, digit & special character", Toast.LENGTH_SHORT).show();
                            }
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
        // Call the register method in the Database class
        db.register(username, email, password, null);
        Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
    public static boolean Isvalid(String password) {
        int F1 = 0, F2 = 0, F3 = 0;

        if (password.length() < 8) {
            return false;
        } else {
            for(int a = 0; a < password.length(); a++) {
                if(Character.isLetter(password.charAt(a))) {
                    F1=1;
                }
            }
            for(int K =0;K<password.length();K++)
            {
                if(Character.isDigit(password.charAt(K)))
                {
                    F2=1;
                }


            }
            for(int b =0;b<password.length();b++)
            {

                char c = password.charAt(b);

                if(c >= 33 && c<=46 ||c==64)
                {
                    F3=1;
                }


            }
            return F1 == 1 && F2 == 1 && F3 == 1;


        }
    }

}
