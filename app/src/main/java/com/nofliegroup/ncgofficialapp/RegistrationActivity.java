package com.nofliegroup.ncgofficialapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
        TextInputEditText etEmail, etEpassword;
        Button btnRegister;
        FirebaseAuth fAuth;
        ProgressDialog progressDialog;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ConstraintLayout constraintLayout = findViewById(R.id.registration_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        fAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.email);
        etEpassword = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btnRegister);
        fAuth = FirebaseAuth.getInstance();

        //btnRegister.setOnClickListener(view -> UserRegistration());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegistration();
            }
        });

    }


    private void UserRegistration() {
        progressDialog.show();
        String email = etEmail.getText().toString().trim();
        String password = etEpassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()){
            progressDialog.dismiss();
            Toast.makeText(this, "All Fields are require! Please enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            fAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressDialog.dismiss();
                          startActivities(new Intent[]{new Intent(RegistrationActivity.this, HomeActivity.class)});
                          finish();
                          Toast.makeText(RegistrationActivity.this, "Registration Successful, User Created", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}