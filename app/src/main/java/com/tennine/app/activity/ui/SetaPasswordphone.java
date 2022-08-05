package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.dismissProgressDialog;
import static com.tennine.app.utils.Util.showProgressbar;
import static com.tennine.app.utils.Util.showToast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tennine.app.R;
import com.tennine.app.databinding.ActivitySetaPasswordphoneBinding;
import com.tennine.app.utils.PreferenceManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SetaPasswordphone extends AppCompatActivity {

    private ActivitySetaPasswordphoneBinding mBinding;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private String email, number, mode;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySetaPasswordphoneBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        getBundleData();
        setListeners();

    }

    private void getBundleData() {
        email = getIntent().getStringExtra("email");
        number = getIntent().getStringExtra("phone");
        mode = getIntent().getStringExtra("mode");
    }

    private void setListeners() {
        mBinding.btnSubmit.setOnClickListener(v -> {
            Map<String, String> map = new HashMap<>();

            if (checkInput()) {
                startActivity(new Intent(SetaPasswordphone.this, Register.class)
                        .putExtra("mode", mode)
                        .putExtra("password", mBinding.edtPassword.getText().toString())
                        .putExtra("email", email)
                        .putExtra("username", "")
                        .putExtra("phone", number)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                );

                finish();

            }
        });
    }

        private void init () {
            firebaseFirestore = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
            preferenceManager = new PreferenceManager(this);
        }


        private boolean checkInput () {
            if (mBinding.edtPassword.getText().toString().isEmpty()) {
                showToast("Password Empty!", this);
                return false;
            } else if (mBinding.edtPassword.getText().toString().length() < 8) {
                showToast("Password Not Less Than 8 character!", this);
                return false;
            } else if (!(mBinding.edtPassword.getText().toString().equals(mBinding.edtConfPassword.getText().toString()))) {
                showToast("Password Not Match!", this);
                return false;
            } else {
                return true;
            }
        }
    }