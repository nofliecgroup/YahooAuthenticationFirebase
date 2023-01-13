package com.nofliegroup.ncgofficialapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button YahooAuthBtn, email_sign_in_button, register_account_button;
    private EditText emailLogin, passwordLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        YahooAuthBtn = findViewById(R.id.connectToYahooBtn);
        email_sign_in_button = findViewById(R.id.email_sign_in_button);
        register_account_button = findViewById(R.id.register_account_button);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        mAuth = FirebaseAuth.getInstance();

        register_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                startActivity(intent);
            }
        });


        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();

                if (email.isEmpty()) {
                    emailLogin.setError("Please enter your email");
                    emailLogin.requestFocus();
                } else if (password.isEmpty()) {
                    passwordLogin.setError("Please enter your password");
                    passwordLogin.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });















        YahooAuthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithYahooAuthProvider(OAuthProvider.newBuilder("yahoo.com")
                        .addCustomParameter("prompt", "login")
                        .addCustomParameter("language", "en")
                        .setScopes(new ArrayList<String>(){
                                       {
                                           add("email");
                                           add("profile");
                                       }
                                   }
                        ).build());
            }
        });
    }



    private void signInWithYahooAuthProvider(OAuthProvider provider) {
        Task<AuthResult> yahooPendingResultTask = mAuth.getPendingAuthResult();
        if (yahooPendingResultTask != null) {
            yahooPendingResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    // User is signed in.
                    UserInfo userInfo = authResult.getUser().getProviderData().get(0);
                    // User profile picture is display
                    UserInfo profilePicture = authResult.getUser().getProviderData().get(0);
                    // User first name is display
                    UserInfo firstName = authResult.getUser().getProviderData().get(0);
                    // User last name is display
                    UserInfo lastName = authResult.getUser().getProviderData().get(0);
                    // User profile picture is display
                    UserInfo email = authResult.getUser().getProviderData().get(0);


                    // IdP data available in

                    authResult.getAdditionalUserInfo().getProfile().get("email");
                    authResult.getAdditionalUserInfo().getProfile().get("name");
                    authResult.getAdditionalUserInfo().getProfile().get("profile");
                    authResult.getAdditionalUserInfo().getProfile().get("picture");


                    // The OAuth access token can also be retrieved:
                    //authResult.getCredential().getAccessToken().
                    // The OAuth secret can be retrieved by calling:
                    // authResult.getCredential().getSecret().
                    // ...
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("email", authResult.getAdditionalUserInfo().getProfile().get("email").toString());
                    intent.putExtra("name", authResult.getAdditionalUserInfo().getProfile().get("name").toString());
                    intent.putExtra("profile", authResult.getAdditionalUserInfo().getProfile().get("profile").toString());
                    intent.putExtra("picture", authResult.getAdditionalUserInfo().getProfile().get("picture").toString());
                    intent.putExtra("given_name", authResult.getAdditionalUserInfo().getProfile().get("given_name").toString());
                    startActivity(intent);

                    Toast.makeText(MainActivity.this, "Yahoo Auth Success", Toast.LENGTH_SHORT).show();


                }
            }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Yahoo Auth Failure", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("TAG", "signInWithYahooAuthProvider: ");
            mAuth.startActivityForSignInWithProvider(this,provider).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Yahoo Auth Success", Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser firebaseUser  = mAuth.getCurrentUser();
                    Intent intent = new Intent(MainActivity.this, YahooConnectedActivity.class);
                    intent.putExtra("yahooemail", firebaseUser.getEmail());
                    intent.putExtra("yahoofullname", firebaseUser.getDisplayName());
                    intent.putExtra("profileimage", firebaseUser.getPhotoUrl().toString());
                    intent.putExtra("Message", "Yahoo Auth Success accessing user account");


                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this, "Yahoo Auth Success", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }// end of signInWithYahooAuthProvider method
}

//I am trying to get the user's email, name, profile, picture, and given_name from the Yahoo Auth Provider. I am able to get the email, name, profile, and picture but I am not able to get the given_name. I am using the following code to get the user's email, name, profile, picture, and given_name:
