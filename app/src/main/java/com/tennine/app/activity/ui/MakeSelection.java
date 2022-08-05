package com.tennine.app.activity.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tennine.app.R;

import java.util.Objects;

public class MakeSelection extends AppCompatActivity {
    TextView viaEmail;
    TextView viaSMS;
    ConstraintLayout layout;
    ConstraintLayout layout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);
        Objects.requireNonNull(getSupportActionBar()).hide();
        viaEmail = findViewById(R.id.viaEmail);
        viaSMS = findViewById(R.id.textView19);
        layout = findViewById(R.id.contraintsLayouts22);
        layout2 = findViewById(R.id.constraintLayout2);
//        viaEmail.setOnClickListener(view -> {
//            Intent intent = new Intent(MakeSelection.this,ForgetpasswordEmail.class);
//            startActivity(intent);
//        });
//        layout.setOnClickListener(view -> {
//            Intent intent = new Intent(MakeSelection.this,ForgetpasswordEmail.class);
//            startActivity(intent);
//        });

        layout2.setOnClickListener(view -> {
            Intent intent = new Intent(MakeSelection.this, CODEverification.class);
            startActivity(intent);
        });
    }
}