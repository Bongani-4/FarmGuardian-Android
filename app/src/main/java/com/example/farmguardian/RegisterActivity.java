package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {
   EditText edusername, edEmail,edpassword,Edconfirm;
   Button btn;
   TextView tv;

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

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edusername.getText().toString();
                String password = edpassword.getText().toString();
                String email = edEmail.getText().toString();
                String confirmpass = Edconfirm.getText().toString();

                if(username.length()==0 || password.length()==0 || email.length()==0 || confirmpass.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"All details should be filled!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.compareTo(confirmpass)==0)
                    {

                    }else{
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));

            }
        });


    }
}