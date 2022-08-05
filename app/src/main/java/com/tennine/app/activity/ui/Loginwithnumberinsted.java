package com.tennine.app.activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tennine.app.R;

import java.util.Objects;

public class Loginwithnumberinsted extends AppCompatActivity {

    TextView useemailinsted,forgetnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginwithnumberinsted);
        Objects.requireNonNull(getSupportActionBar()).hide();
        useemailinsted = findViewById(R.id.textView17);
        forgetnumber = findViewById(R.id.tvforgetpasswordnumber);
        useemailinsted.setOnClickListener(view -> {
            Intent intent = new Intent(Loginwithnumberinsted.this,Login.class);
            startActivity(intent);
        });
        forgetnumber.setOnClickListener(view -> {
            Intent intent = new Intent(Loginwithnumberinsted.this, MakeSelection.class);
            startActivity(intent);
        });
    }
}