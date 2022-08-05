package com.tennine.app.activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.tennine.app.R;

import java.util.Objects;

public class OtpVerification extends AppCompatActivity {

    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        Objects.requireNonNull(getSupportActionBar()).hide();
        verify = findViewById(R.id.btnverifyOtp);
        verify.setOnClickListener(view -> {
            Intent intent = new Intent(OtpVerification.this, SetaPasswordphone.class);
            startActivity(intent);
        });
    }
}