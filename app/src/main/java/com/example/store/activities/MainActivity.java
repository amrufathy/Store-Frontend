package com.example.store.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.store.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        Log.d("facebook login status", Boolean.toString(isLoggedIn));

        if (isLoggedIn) {
            startActivity(new Intent(this, ProductsActivity.class));
        } else {
            LoginManager.getInstance().logOut();
        }

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = findViewById(R.id.fb_login_button);
        loginButton.setPermissions("public_profile, email");
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(MainActivity.this, ProductsActivity.class));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        Boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//
//        Log.d("post facebook login status", isLoggedIn.toString());
//
//        if (isLoggedIn) {
//            Log.d("post facebook login status", isLoggedIn.toString());
//            startActivity(new Intent(this, ProductsActivity.class));
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
