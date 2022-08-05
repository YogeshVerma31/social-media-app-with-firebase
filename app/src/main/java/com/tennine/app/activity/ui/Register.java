package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.dismissProgressDialog;
import static com.tennine.app.utils.Util.showProgressbar;
import static com.tennine.app.utils.Util.showToast;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tennine.app.BuildConfig;
import com.tennine.app.R;
import com.tennine.app.databinding.ActivityRegisterBinding;
import com.tennine.app.utils.DatePicker;
import com.tennine.app.utils.PreferenceManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ActivityRegisterBinding activityRegisterBinding;


    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String endcodedImage;
    private Boolean checkAvailabiliy = false;
    private DatePicker mDatePickerDialogFragment;
    private String mode;
    private String password, email, username, phonenumber;

    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        getIntentVariables();
        setListeners();
    }

    private void getIntentVariables() {
        mode = getIntent().getStringExtra("mode");
        password = getIntent().getStringExtra("password");
        email = getIntent().getStringExtra("email");
        phonenumber = getIntent().getStringExtra("phone");
    }


    private void setListeners() {
        activityRegisterBinding.ivProfilePhoto.setOnClickListener(v -> {
            Dexter.withContext(this)
                    .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                selectImage();
                            } else {
                                showToast("Please Allow Permissions", Register.this);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                    }).check();

        });

        activityRegisterBinding.tvDOB.setOnClickListener(v -> {
            mDatePickerDialogFragment = new DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE OF BIRTH PICK");

        });

        activityRegisterBinding.btnRegister.setOnClickListener(v -> {
            if (isValidSignUPDetails()) {
                doRegister();
            }
        });

        activityRegisterBinding.tvCheckAvailability.setOnClickListener(v -> {
            if (activityRegisterBinding.edtUserName.getText().length() < 4) {
                showToast("Enter username or username not less than 4 words", this);
            } else {
                checkAvailability();
            }
        });

    }

    private void checkAvailability() {
        activityRegisterBinding.tvCheckAvailability.setVisibility(View.INVISIBLE);
        activityRegisterBinding.pbUserNameAvailability.setVisibility(View.VISIBLE);
        firebaseFirestore.collection("users").whereEqualTo("username", activityRegisterBinding.edtUserName.getText().toString()).get()
                .addOnCompleteListener(task12 -> {
                    if (task12.isSuccessful()) {
                        activityRegisterBinding.tvCheckAvailability.setVisibility(View.VISIBLE);
                        activityRegisterBinding.pbUserNameAvailability.setVisibility(View.GONE);
                        activityRegisterBinding.tvCheckAvailability.setText("Correct Username");
                        List<DocumentSnapshot> documentSnapshot = task12.getResult().getDocuments();
                        if (documentSnapshot.isEmpty()) {
                            checkAvailabiliy = true;
                        } else {
                            activityRegisterBinding.tvCheckAvailability.setText("Username Already Exists");
                            checkAvailabiliy = false;
                        }

                    }
                });

    }

    private void doRegister() {
        doRegisterWithPhone();
    }

    private void doRegisterWithPhone() {

        Map<String, Object> map = new HashMap<>();
        map.put("id", FirebaseAuth.getInstance().getUid());
        map.put("username", activityRegisterBinding.edtUserName.getText().toString());
        map.put("firstname", activityRegisterBinding.edtFirstName.getText().toString());
        map.put("Lastname", activityRegisterBinding.edtLastName.getText().toString());
        map.put("nickname", activityRegisterBinding.edtFirstName.getText().toString() + " " + activityRegisterBinding.edtLastName.getText().toString());
        map.put("Date of Birth", activityRegisterBinding.tvDOB.getText().toString());
        map.put("bio", "");
        map.put("location", "");
        map.put("image", endcodedImage);
        map.put("no_of_followers", 0);
        map.put("no_of_following", 0);
        map.put("no_of_posts", 0);
        map.put("email", email);
        map.put("phone", phonenumber);
        map.put("password", password);


        showProgressbar(this);

        firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> map1 = new HashMap<>();
                            map1.put("id", firebaseAuth.getCurrentUser().getUid());
                            map1.put("image", endcodedImage);
                            map1.put("nickname", activityRegisterBinding.edtFirstName.getText().toString() +
                                    " " + activityRegisterBinding.edtLastName.getText().toString());
                            map1.put("username", activityRegisterBinding.edtUserName.getText().toString());
                            firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                                    .collection("FOLLOWING")
                                    .document(firebaseAuth.getCurrentUser().getUid())
                                    .set(map1)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dismissProgressDialog();
                                            preferenceManager.putString("id", firebaseAuth.getUid());
                                            preferenceManager.putBoolean("isLogin", true);
                                            showToast("Register Successfully", Register.this);
                                            Intent intent = new Intent(Register.this, Googlefacebookphone.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                        } else {
                            String error = task.getException().getMessage();
                            showToast(error, Register.this);
                        }
                    }
                });
    }


    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
        activityRegisterBinding.tvDOB.setText(selectedDate);
    }

    private void init() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        preferenceManager = new PreferenceManager(this);
    }


    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImage.launch(intent);
    }


    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        activityRegisterBinding.ivProfilePhoto.setImageBitmap(bitmap);
                        endcodedImage = encodedImage(bitmap);

                    } catch (FileNotFoundException e) {

                    }
                }
            }
    );

    private String encodedImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewWeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewWeight, false);
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayInputStream);
        byte[] bytes = byteArrayInputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private Boolean isValidSignUPDetails() {

        if (endcodedImage == null) {
            showToast("Select Profile Image", this);
            return false;
        } else if (activityRegisterBinding.edtFirstName.getText().toString().trim().isEmpty()) {
            showToast("Enter First Name", this);
            return false;
        } else if (activityRegisterBinding.edtLastName.getText().toString().trim().isEmpty()) {
            showToast("Enter Last Name", this);
            return false;
        } else if (activityRegisterBinding.edtUserName.getText().toString().trim().isEmpty() && (!checkAvailabiliy)) {
            showToast("Enter Username or check Correct Username", this);
            return false;
        } else if (activityRegisterBinding.edtUserName.getText().length() < 4) {
            showToast("Username must be greater than 4 words", this);
            return false;
        } else if (activityRegisterBinding.tvDOB.getText().toString().trim().isEmpty()) {
            showToast("Select Date of Birth", this);
            return false;
        } else {
            return true;
        }
    }


}