package com.example.farmguardian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity
{

    private  String currentUsername;
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
                Database db  = new Database(getApplicationContext(),"FarmGuardian",null,1);

                if(username.length()==0 || password.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"All details should be filled!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(db.login(username,password) ==1) {
                        Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",username);
                        editor.apply();  //save data with key and value.


                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));



                    }else{
                        Toast.makeText(getApplicationContext(), "Unknown user", Toast.LENGTH_SHORT).show();


                    }

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
    public String  getUsername()
    {

        return currentUsername;
    }


}