package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.dismissProgressDialog;
import static com.tennine.app.utils.Util.showProgressbar;
import static com.tennine.app.utils.Util.showToast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tennine.app.BuildConfig;
import com.tennine.app.R;
import com.tennine.app.databinding.ActivitySettingBinding;
import com.tennine.app.utils.PreferenceManager;

import java.util.Objects;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding mBinding;
    private FirebaseFirestore db;
    private PreferenceManager preferenceManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        setListener();

    }

    private void setListener() {
        mBinding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Tennine");
                    String shareMessage = "\nLet me recommend you this Tennine Application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        mBinding.btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Under Construction", SettingActivity.this);
            }
        });

        mBinding.btnCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Under Construction", SettingActivity.this);
            }
        });

        mBinding.btnLa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Under Construction", SettingActivity.this);
            }
        });

        mBinding.tvDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressbar(SettingActivity.this);
                db.collection("users")
                        .document(preferenceManager.getString("id"))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dismissProgressDialog();
                                showToast("Deleted Succesfully", SettingActivity.this);
                                startActivity(new Intent(SettingActivity.this, Googlefacebookphone.class));
                                finish();
                            }
                        });
            }
        });

        mBinding.btnTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Under Construction", SettingActivity.this);
            }
        });

        mBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                preferenceManager.clear();
                showToast("Logged Out", SettingActivity.this);
                Intent intent = new Intent(SettingActivity.this, Googlefacebookphone.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        preferenceManager = new PreferenceManager(this);

    }
}