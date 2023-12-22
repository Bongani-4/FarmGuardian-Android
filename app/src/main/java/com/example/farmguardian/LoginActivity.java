package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity
{

   EditText edusername, edpassword ;
    Button btn;
    TextView tv;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edusername = findViewById(R.id.editTextusername);
        edpassword = findViewById(R.id.editTextTextPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textRegister);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edusername.getText().toString();
                String password = edpassword.getText().toString();

                if(username.length()==0 || password.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"All details should be filled!", Toast.LENGTH_SHORT).show();
                }
                else{
                                   Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                }
            }
        });
         tv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity( new Intent(LoginActivity.this,RegisterActivity.class));

             }
         });
        }


}