
package com.tennine.app.activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tennine.app.R;

import java.util.Objects;

public class CODEverification extends AppCompatActivity {
Button btnverification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeverification);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btnverification = findViewById(R.id.button4);
        btnverification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CODEverification.this, Newcredentials.class);
                startActivity(intent);
            }
        });

    }
}