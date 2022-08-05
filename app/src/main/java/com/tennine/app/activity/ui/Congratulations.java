package com.tennine.app.activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tennine.app.R;

import java.util.Objects;

public class Congratulations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}