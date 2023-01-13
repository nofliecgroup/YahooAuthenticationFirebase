package com.nofliegroup.ncgofficialapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    TextView welcomeMessage, signOut;

    FirebaseAuth fAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ConstraintLayout constraintLayout = findViewById(R.id.activityHome);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Intent intent = getIntent();

        welcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessage.setText("Welcome! You are connected" + intent.getStringExtra("email"));
        welcomeMessage.setText("Click on me to signout " + intent.getStringExtra("yahooemail"));
        String email = getIntent().getStringExtra("email");


        //welcomeMessage.setText("Welcome " + email);
        welcomeMessage.setText("Welcome! You are connected" + intent.getStringExtra("email"));
        welcomeMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
            }
        });








    }
}