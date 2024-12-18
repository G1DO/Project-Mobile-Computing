package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.e_commerce.R;

public class SignUp extends AppCompatActivity {

    EditText username, email, password;
    Button signup;
    CalendarView calendarView;
    FirebaseAuth mAuth; // Firebase Authentication instance
    String birthdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign up");

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        calendarView = findViewById(R.id.calendarView);
        signup = findViewById(R.id.signup);

        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            month++; // Calendar months start from 0
            birthdate = day + "-" + month + "-" + year;
            Toast.makeText(SignUp.this, "Selected date: " + birthdate, Toast.LENGTH_SHORT).show();
        });

        signup.setOnClickListener(view -> {
            username = findViewById(R.id.username);
            email = findViewById(R.id.email);
            password = findViewById(R.id.password);

            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();
            String usernameText = username.getText().toString().trim();

            if (usernameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || birthdate.isEmpty()) {
                Toast.makeText(SignUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SignUp.this, "Sign-up Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent();
                                intent.putExtra("username", usernameText);
                                intent.putExtra("email", emailText);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            } else {
                                Toast.makeText(SignUp.this, "Sign-up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
