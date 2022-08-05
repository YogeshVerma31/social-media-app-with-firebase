package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.dismissProgressDialog;
import static com.tennine.app.utils.Util.showProgressbar;
import static com.tennine.app.utils.Util.showToast;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tennine.app.R;
import com.tennine.app.databinding.ActivityOtpPhoneBinding;
import com.tennine.app.utils.PreferenceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class OtpPhone extends AppCompatActivity {

    private ActivityOtpPhoneBinding activityOtpPhoneBinding;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    private FirebaseAuth mfirebaseAuth;
    private FirebaseFirestore db;

    private PreferenceManager preferenceManager;

    private String verificationId;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private Timer timer;
    private int count = 60;
    private String phone, number;
    private final String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOtpPhoneBinding = ActivityOtpPhoneBinding.inflate(getLayoutInflater());
        setContentView(activityOtpPhoneBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        googleFacebookSignIn();
        setListeners();
    }

    private void googleFacebookSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setListeners() {
        activityOtpPhoneBinding.btnGenerateOtp.setOnClickListener(v -> {
            if (isValidSignInDetails()) {
                signInPhone();
            }
        });

        activityOtpPhoneBinding.btnverifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityOtpPhoneBinding.edtPhoneVerification.getText().toString().trim() == null ||
                        activityOtpPhoneBinding.edtPhoneVerification.getText().toString().trim().isEmpty()) {
                    showToast("Enter OTP", OtpPhone.this);
                } else {
                    showProgressbar(OtpPhone.this);
                    verifyCode(activityOtpPhoneBinding.edtPhoneVerification.getText().toString().trim());
                }
            }
        });


        activityOtpPhoneBinding.tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOtp();
                activityOtpPhoneBinding.tvResend.setEnabled(false);
                activityOtpPhoneBinding.tvResend.setAlpha(0.5f);
                count = 60;
            }
        });
    }

    private void signInPhone() {
        number = activityOtpPhoneBinding.edtPhoneNumber.getText().toString();
        phone = "+91" + activityOtpPhoneBinding.edtPhoneNumber.getText().toString();
        sendVerificationCode(phone);
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
                showToast(account.getId(), getApplicationContext());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                showToast(e.getMessage(), getApplicationContext());

            }
        }
    }


    private void sendVerificationCode(String number) {

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                final String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    activityOtpPhoneBinding.edtPhoneVerification.setText(code);
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                activityOtpPhoneBinding.clEnterNUmber.setVisibility(View.VISIBLE);
                activityOtpPhoneBinding.clVerifyCode.setVisibility(View.GONE);
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mfirebaseAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallback)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        activityOtpPhoneBinding.clEnterNUmber.setVisibility(View.GONE);
        activityOtpPhoneBinding.clVerifyCode.setVisibility(View.VISIBLE);
        showToast("Otp send Successfully", getApplicationContext());

        activityOtpPhoneBinding.tvNumber.setText("Enter the code we sent to" + number);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (count == 0) {
                            activityOtpPhoneBinding.tvResend.setText("Resend");
                            activityOtpPhoneBinding.tvResend.setEnabled(true);
                            activityOtpPhoneBinding.tvResend.setAlpha(1f);
                        } else {
                            activityOtpPhoneBinding.tvResend.setText("Resend in " + count);
                            count--;
                        }
                    }
                });
            }
        }, 0, 1000);
    }


    private void resendOtp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mfirebaseAuth)
                        .setPhoneNumber(phone)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallback)
                        .setForceResendingToken(forceResendingToken)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        showProgressbar(this);
        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        showToast("verification complete", getApplicationContext());

                        Map<String, String> map = new HashMap<>();
                        map.put("email", email);
                        map.put("phone", number);

                        db.collection("users").whereEqualTo("phone", number).get()
                                .addOnCompleteListener(task12 -> {
                                    if (task12.isSuccessful()) {
                                        List<DocumentSnapshot> documentSnapshot = task12.getResult().getDocuments();
                                        if (documentSnapshot.isEmpty()) {
                                            dismissProgressDialog();
                                            startActivity(new Intent(OtpPhone.this, SetaPasswordphone.class)
                                                    .putExtra("email", email)
                                                    .putExtra("phone", number));
                                        } else {
                                            dismissProgressDialog();
                                            if (documentSnapshot.get(0).getString("password").isEmpty()) {
                                                startActivity(new Intent(OtpPhone.this, SetaPasswordphone.class));
                                            } else if (documentSnapshot.get(0).getString("username").isEmpty()) {
                                                preferenceManager.putString("email", "");
                                                preferenceManager.putString("phone", number);
                                                startActivity(new Intent(OtpPhone.this, Register.class)
                                                        .putExtra("mode", "PH")
                                                );
                                            } else {
                                                preferenceManager.putString("email", "");
                                                preferenceManager.putString("phone", number);
                                                preferenceManager.putBoolean("isLogin", true);
                                                preferenceManager.putString("id", documentSnapshot.get(0).getString("id"));
                                                Intent intent = new Intent(OtpPhone.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }

                                    } else {
                                        dismissProgressDialog();
                                        String error = task12.getException().getMessage();
                                        showToast(error, OtpPhone.this);
                                    }
                                });


                    } else {
                        dismissProgressDialog();
                        activityOtpPhoneBinding.edtPhoneVerification.setError("Invalid OTP");
                        activityOtpPhoneBinding.edtPhoneVerification.setText("");
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mfirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showToast("verification complete", getApplicationContext());

                        Map<String, String> map = new HashMap<>();
                        map.put("email", email);
                        map.put("phone", "");

                        db.collection("users").whereEqualTo("phone", number).get()
                                .addOnCompleteListener(task12 -> {
                                    if (task12.isSuccessful()) {
                                        List<DocumentSnapshot> documentSnapshot = task12.getResult().getDocuments();
                                        if (documentSnapshot.isEmpty()) {
                                            dismissProgressDialog();
                                            startActivity(new Intent(OtpPhone.this, SetaPasswordphone.class)
                                                    .putExtra("email", email)
                                                    .putExtra("phone", ""));
                                        } else {
                                            dismissProgressDialog();
                                            if (documentSnapshot.get(0).getString("password").isEmpty()) {
                                                startActivity(new Intent(OtpPhone.this, SetaPasswordphone.class));
                                            } else if (documentSnapshot.get(0).getString("username").isEmpty()) {
                                                preferenceManager.putString("email", "");
                                                preferenceManager.putString("phone", "");
                                                startActivity(new Intent(OtpPhone.this, Register.class)
                                                        .putExtra("mode", "PH")
                                                );
                                            } else {
                                                preferenceManager.putString("email", "");
                                                preferenceManager.putString("phone", number);
                                                preferenceManager.putString("id", documentSnapshot.get(0).getString("id"));
                                                startActivity(new Intent(OtpPhone.this, MainActivity.class));
                                                finish();
                                            }
                                        }

                                    } else {
                                        dismissProgressDialog();
                                        String error = task12.getException().getMessage();
                                        showToast(error, OtpPhone.this);
                                    }
                                });


                    } else {

                    }
                });
    }


    private void init() {
        preferenceManager = new PreferenceManager(this);
        mfirebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        timer = new Timer();

    }

    private Boolean isValidSignInDetails() {
        if (TextUtils.isEmpty(activityOtpPhoneBinding.edtPhoneNumber.getText().toString()) ||
                activityOtpPhoneBinding.edtPhoneNumber.getText().toString().length() < 10) {
            showToast("Invalid Mobile Number", getApplicationContext());
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}