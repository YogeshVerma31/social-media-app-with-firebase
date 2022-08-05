package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.showToast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tennine.app.R;

import com.tennine.app.R;
import com.tennine.app.databinding.ActivityLoginBinding;
import com.tennine.app.utils.PreferenceManager;

import java.util.List;
import java.util.Objects;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding mBinding;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        setListener();

    }

    private void init() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        preferenceManager= new PreferenceManager(this);
    }

    private void setListener() {
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    doLogin();
                }
            }
        });

        mBinding.edtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Googlefacebookphone.class));
                finish();
            }
        });
    }


    private void doLogin() {
        mAuth.signInWithEmailAndPassword(mBinding.edtPhoneEmail.getText().toString(), mBinding.edtPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            db.collection("users")
                                    .whereEqualTo("email", mBinding.edtPhoneEmail.getText().toString())
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if (!queryDocumentSnapshots.isEmpty()) {
                                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                preferenceManager.putString("id",list.get(0).getString("id"));
                                                preferenceManager.putBoolean("isLogin",true);
                                                startActivity(new Intent(Login.this,MainActivity.class));
                                                finish();
                                            }
                                        }
                                    });
                        } else {
                            String error = task.getException().getMessage();
                            showToast(error, Login.this);
                        }
                    }
                });

    }

    private boolean checkInput() {
        if (mBinding.edtPhoneEmail.getText().toString().isEmpty()) {
            showToast("Phone or Email is Empty", this);
            return false;
        } else if (mBinding.edtPassword.getText().toString().isEmpty()) {
            showToast("Password is Empty", this);
            return false;
        }

        if (mBinding.edtPhoneEmail.getText().toString().contains("gmail.com")) {
            return true;
        } else {
            if (mBinding.edtPhoneEmail.getText().toString().length() < 10) {
                showToast("incorrect Phone Number", this);
                return false;
            }
        }
        return true;


    }
}