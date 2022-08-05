package com.tennine.app.activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tennine.app.R;

import java.util.Objects;

public class PasswordUpdated extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_updated);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button login2 = findViewById(R.id.button6);
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordUpdated.this, Login.class);
                startActivity(intent);
            }
        });
    }
}