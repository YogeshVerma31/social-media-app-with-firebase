package com.tennine.app.activity.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tennine.app.utils.PreferenceManager;

public class BaseActivity extends AppCompatActivity {

    private DocumentReference documentReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        FirebaseFirestore databse = FirebaseFirestore.getInstance();
//        documentReference = databse.collection(Constants.KEY_COLLECTION_USERS)
//                .document(preferenceManager.getString(Constants.KEY_USER_ID));
    }
}
