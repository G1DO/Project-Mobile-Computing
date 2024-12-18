package com.example.e_commerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Database.MyDatabase;
import com.example.e_commerce.Model.CustomerModel;
import com.example.e_commerce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    MyDatabase obj;
    EditText username, password;
    TextView error;
    boolean flag = true;
    Button login, forget_password;
    String value = null, value2 = null;
    CheckBox rememberme;
    //public static String u_name="";

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        obj = new MyDatabase(this);
        login = (Button) findViewById(R.id.Login);
        forget_password = (Button) findViewById(R.id.forget_password);

        rememberme = (CheckBox) findViewById(R.id.rememberme);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String str = preferences.getString("rememberme", "");
        if (str.equals("true")) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        } else if (str.equals("false")) {
            Toast.makeText(Login.this,"please sign in.",Toast.LENGTH_SHORT).show();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CustomerModel> arrayList = obj.Get_Data();
                //Toast.makeText(Login.this,arrayList.size()+" ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                username = (EditText) findViewById(R.id.usernameinlogin);
                password = (EditText) findViewById(R.id.passwordinlogin);
                error = (TextView) findViewById(R.id.error);


                firebaseAuth.signInWithEmailAndPassword(username.toString(), password.toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if (user != null) {
                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                                Toast.makeText(Login.this, "Login Failed: " + errorMessage, Toast.LENGTH_LONG).show();

                            }
                        });



                forget_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Login.this, Forget_password.class);
                        startActivity(intent);
                    }
                });


                rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (compoundButton.isChecked()) {
                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("rememberme", "true");
                            editor.apply();
                            //Toast.makeText(Login.this,"Checked",Toast.LENGTH_SHORT).show();
                        } else if (!compoundButton.isChecked()) {
                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("rememberme", "false");
                            editor.apply();
                            //Toast.makeText(Login.this,"UnChecked",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}