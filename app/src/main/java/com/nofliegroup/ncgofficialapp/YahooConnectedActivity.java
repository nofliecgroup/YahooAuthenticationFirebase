package com.nofliegroup.ncgofficialapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;;

public class YahooConnectedActivity extends AppCompatActivity {
    TextView welcomeMessage;
    TextView tvLoadEmail;
    Button btnlogout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yahoo_connected);

        ConstraintLayout constraintLayout = findViewById(R.id.yahooConnected);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


        btnlogout = findViewById(R.id.btnlogout);
        tvLoadEmail = findViewById(R.id.tvLoadEmail);
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String profile = intent.getStringExtra("profile");
        String yahooemail = intent.getStringExtra("yahooemail");
        String profileimage = intent.getStringExtra("profileimage");
        String yahoofullname = intent.getStringExtra("yahoofullname");

        tvLoadEmail.setText(email);
        tvLoadEmail.setText(profile);
        tvLoadEmail.setText(yahooemail);

       // welcomeMessage.setText("Welcome! You are connected" + intent.getStringExtra("email"));

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                tvLoadEmail.setText("Logged In");
                tvLoadEmail.setText(email);
                tvLoadEmail.setText(profile);
                tvLoadEmail.setText(yahooemail);
                tvLoadEmail.setText(profileimage);
                tvLoadEmail.setText(yahoofullname);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //startActivity(new Intent(HomeActivity.this, ConnectYahooActivity.class));
                String yahooMailUri = "content://com.yahoo.mail.provider/inbox";
                Uri uri = Uri.parse(yahooMailUri);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                finish();

            }
        }); // end of btnlogout.setOnClickListener


        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                finish();

            }
        }); // end of btnlogout.setOnClickListener

    }
    }