package com.example.e_commerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.example.e_commerce.R;

public class Forget_password extends AppCompatActivity {
    private Button check;
    private EditText usernameinforget, emailinforget;
    private TextView showpassword;
    private FirebaseAuth mAuth;  // Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().setTitle("Forget password");

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        check = findViewById(R.id.checkinforget_password);
        usernameinforget = findViewById(R.id.usernameinforget_password);
        emailinforget = findViewById(R.id.eamilinforget_password);
        showpassword = findViewById(R.id.showpassword);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailinforget.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(Forget_password.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Send password reset email using Firebase Authentication
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(Forget_password.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Forget_password.this, "Failed to send reset email: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
