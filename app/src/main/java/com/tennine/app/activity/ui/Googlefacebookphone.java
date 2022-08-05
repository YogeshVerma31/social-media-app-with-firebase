package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.dismissProgressDialog;
import static com.tennine.app.utils.Util.showProgressbar;
import static com.tennine.app.utils.Util.showToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tennine.app.R;
import com.tennine.app.databinding.ActivityGooglefacebookphoneBinding;
import com.tennine.app.databinding.ActivityOtpPhoneBinding;
import com.tennine.app.R;
import com.tennine.app.utils.PreferenceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Googlefacebookphone extends AppCompatActivity {
    private ActivityGooglefacebookphoneBinding activityGooglefacebookphoneBinding;
    private FirebaseAuth mfirebaseAuth;
    private String verificationId;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;

    private FirebaseFirestore db;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGooglefacebookphoneBinding = ActivityGooglefacebookphoneBinding.inflate(getLayoutInflater());
        setContentView(activityGooglefacebookphoneBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        googleFacebookSignIn();
        setListeners();

    }

    private void init() {
        mfirebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(this);
    }

    private void googleFacebookSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                showToast(e.getMessage(), getApplicationContext());

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        showProgressbar(this);
        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showToast("Login Successfully", getApplicationContext());
                            FirebaseUser user = mfirebaseAuth.getCurrentUser();
                            Log.d("onComplete: ", user.getDisplayName() + user.getEmail() + user.getPhoneNumber());
                            showToast("verification complete", getApplicationContext());

                            Map<String, String> map = new HashMap<>();
                            map.put("email", user.getEmail());
                            map.put("phone", "");

                            db.collection("users").whereEqualTo("email", user.getEmail()).get()
                                    .addOnCompleteListener(task12 -> {
                                        if (task12.isSuccessful()) {
                                            List<DocumentSnapshot> documentSnapshot = task12.getResult().getDocuments();
                                            if (documentSnapshot.isEmpty()) {
                                                dismissProgressDialog();
                                                startActivity(new Intent(Googlefacebookphone.this, SetaPasswordphone.class)
                                                        .putExtra("email", user.getEmail())
                                                        .putExtra("phone", user.getPhoneNumber())
                                                        .putExtra("mode","EM")
                                                );
                                            } else {
                                                dismissProgressDialog();
                                                if (documentSnapshot.get(0).getString("password").isEmpty()) {
                                                    startActivity(new Intent(Googlefacebookphone.this, SetaPasswordphone.class));
                                                } else if (documentSnapshot.get(0).getString("username").isEmpty()) {
                                                    preferenceManager.putString("email", "");
                                                    preferenceManager.putString("phone", user.getPhoneNumber());
                                                    startActivity(new Intent(Googlefacebookphone.this, Register.class)
                                                            .putExtra("mode", "EM")
                                                    );
                                                } else {
                                                    preferenceManager.putString("email", "");
                                                    preferenceManager.putString("phone", user.getPhoneNumber());
                                                    preferenceManager.putBoolean("isLogin", true);
                                                    preferenceManager.putString("id", documentSnapshot.get(0).getString("id"));
                                                    startActivity(new Intent(Googlefacebookphone.this, MainActivity.class));
                                                    finish();
                                                }
                                            }

                                        } else {
                                            dismissProgressDialog();
                                            String error = task12.getException().getMessage();
                                            showToast(error, Googlefacebookphone.this);
                                        }
                                    });


                        } else {

                        }
                    }
                });
    }


    private void setListeners() {
        activityGooglefacebookphoneBinding.btnPhone3.setOnClickListener(view -> {
            Intent intent = new Intent(Googlefacebookphone.this, OtpPhone.class);
            startActivity(intent);
        });
        activityGooglefacebookphoneBinding.btnGoogle.setOnClickListener(view -> {
            signInGoogle();
        });
    }
}