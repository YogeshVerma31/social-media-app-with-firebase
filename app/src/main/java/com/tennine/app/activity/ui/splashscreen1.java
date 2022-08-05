package com.tennine.app.activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.tennine.app.R;
import com.tennine.app.utils.PreferenceManager;

import java.util.Objects;

public class splashscreen1 extends AppCompatActivity {

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen1);
        preferenceManager = new PreferenceManager(this);
        Objects.requireNonNull(getSupportActionBar()).hide();

        new Handler().postDelayed(() -> {

            if (preferenceManager.getBoolean("isLogin")){
                startActivity(new Intent(splashscreen1.this, MainActivity.class));
                finish();
            }else {
                startActivity(new Intent(splashscreen1.this, Googlefacebookphone.class));
                finish();
            }
        },2000);
    }

}