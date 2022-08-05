package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.dismissProgressDialog;
import static com.tennine.app.utils.Util.showProgressbar;
import static com.tennine.app.utils.Util.showToast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tennine.app.R;
import com.tennine.app.databinding.ActivityEditProfileBinding;
import com.tennine.app.utils.PreferenceManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EditProfile extends AppCompatActivity {
    private ActivityEditProfileBinding mBinding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private String endcodedImage;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        getBundleData();
        setListeners();

    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(this);
    }

    private void getBundleData() {
        if (getIntent().getStringExtra("image") != null) {
            endcodedImage = getIntent().getStringExtra("image");
        }
        mBinding.ivProfile.setImageBitmap(getBitmapFromEncodedString(getIntent().getStringExtra("image")));
        mBinding.edtBio.setText(getIntent().getStringExtra("bio"));
        mBinding.edtName.setText(getIntent().getStringExtra("nickname"));
        mBinding.edtUsername.setText(getIntent().getStringExtra("username"));
        mBinding.edtWebsite.setText(getIntent().getStringExtra("website"));
    }

    private void setListeners() {
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBinding.ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressbar(EditProfile.this);
                Map<String, Object> map = new HashMap<>();
                map.put("bio", mBinding.edtBio.getText().toString());
                map.put("image", endcodedImage);
                map.put("username", mBinding.edtUsername.getText().toString());
                map.put("nickname", mBinding.edtName.getText().toString());
                map.put("website", mBinding.edtWebsite.getText().toString());
                firebaseFirestore.collection("users")
                        .document(preferenceManager.getString("id"))
                        .update(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dismissProgressDialog();
                                showToast("uploaded successfully",EditProfile.this);
                            }
                        });

            }
        });
        mBinding.ivProfile.setOnClickListener(v -> {
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
                                showToast("Please Allow Permissions", EditProfile.this);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                    }).check();

        });
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
                        mBinding.ivProfile.setImageBitmap(bitmap);
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

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        if (encodedImage != null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }

    }

}