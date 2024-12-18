package com.example.e_commerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.e_commerce.R;

public class AdminLogin extends AppCompatActivity {

    private EditText useradmin, passadmin;
    private Button loginadmin;
    private FirebaseAuth mAuth;  // Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().setTitle("Login");

        useradmin = findViewById(R.id.useradmin);
        passadmin = findViewById(R.id.passadmin);
        loginadmin = findViewById(R.id.loginadmin);

        mAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth

        loginadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = useradmin.getText().toString().trim();
                String password = passadmin.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AdminLogin.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Sign in with Firebase Authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(AdminLogin.this, task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Check if it's the admin (based on email or other criteria)
                                    if (user != null && email.equals("admin@admin.com")) {
                                        // Proceed to the next screen if the admin logs in
                                        Intent intent = new Intent(AdminLogin.this, UploadProduct.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(AdminLogin.this, "Not authorized", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(AdminLogin.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }
}
