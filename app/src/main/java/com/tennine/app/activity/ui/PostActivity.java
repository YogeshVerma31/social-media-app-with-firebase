
package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.dismissProgressDialog;
import static com.tennine.app.utils.Util.showToast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.Utils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tennine.app.R;
import com.tennine.app.databinding.ActivityPostBinding;
import com.tennine.app.model.PostModel;
import com.tennine.app.model.ReelsModel;
import com.tennine.app.utils.PreferenceManager;
import com.tennine.app.utils.Util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {

    private ActivityPostBinding mBinding;
    private FirebaseStorage storage;
    private Uri imageUri;
    private FirebaseFirestore firebaseFirestore;

    private String userName, userImage, type;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        getBundleData();
        setListener();
    }

    private void getBundleData() {
        userImage = getIntent().getStringExtra("userImage");
        userName = getIntent().getStringExtra("userName");
        type = getIntent().getStringExtra("type");
    }

    private void init() {
        preferenceManager = new PreferenceManager(this);
        storage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void setListener() {
        mBinding.ivBack.setOnClickListener(v -> onBackPressed());

        mBinding.ivPost.setOnClickListener(v -> {
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
                                showToast("Please Allow Permissions", PostActivity.this);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                    }).check();

        });

        mBinding.btnShare.setOnClickListener(v -> {

            if (type.equals("POST")) {

                if (checkInput()) {
                    Util.showProgressbar(this);
                    final StorageReference reference = storage.getReference().child("post")
                            .child(preferenceManager.getString("id"))
                            .child(new Date().getTime() + "");
                    reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String documentId = UUID.randomUUID().toString();
                                    PostModel postModel = new PostModel();
                                    postModel.setPostId(documentId);
                                    postModel.setPostImage(uri.toString());
                                    postModel.setPostedBy(preferenceManager.getString("id"));
                                    postModel.setPostDescription(mBinding.edtCaption.getText().toString());
                                    postModel.setPostLikes(Long.parseLong("0"));
                                    postModel.setPostComments(Long.parseLong("0"));
                                    postModel.setPostDate(new Date().getTime());
                                    postModel.setNumRating(0);
                                    postModel.setPresendUserRate(false);
                                    postModel.setUserName(userName);
                                    postModel.setUserImage(userImage);
                                    firebaseFirestore.collection("posts").document(preferenceManager.getString("id"))
                                            .collection("POSTS").document(documentId).set(postModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dismissProgressDialog();
                                            finish();
                                            showToast("Post Uploaded Succesfully", PostActivity.this);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            } else {

                if (checkInput()) {
                    Util.showProgressbar(this);
                    final StorageReference reference = storage.getReference().child("reels")
                            .child(preferenceManager.getString("id"))
                            .child(new Date().getTime() + "");
                    reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String documentId = UUID.randomUUID().toString();
                                    ReelsModel reelsModel = new ReelsModel();
                                    reelsModel.setVideoId(documentId);
                                    reelsModel.setVideoUrl(uri.toString());
                                    reelsModel.setUserId(preferenceManager.getString("id"));
                                    reelsModel.setVideoDescrp(mBinding.edtCaption.getText().toString());
                                    reelsModel.setShares(Long.parseLong("0"));
                                    reelsModel.setComments(Long.parseLong("0"));
                                    reelsModel.setLikes(Long.parseLong("0"));
                                    reelsModel.setVideoDate(new Date().getTime());
                                    reelsModel.setVideoTimeStamp(new Date());
                                    reelsModel.setPresentUserLike(false);
                                    reelsModel.setNumRating(0);
                                    reelsModel.setUserName(userName);
                                    reelsModel.setUserImage(userImage);
                                    firebaseFirestore.collection("REELS")
                                            .document(documentId)
                                            .set(reelsModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
                                                            .collection("REELS").document(documentId).set(reelsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            dismissProgressDialog();
                                                            finish();
                                                            showToast("REELS Uploaded Succesfully", PostActivity.this);
                                                        }
                                                    });
                                                }
                                            });


                                }
                            });
                        }
                    });
                }

            }


        });

    }

    private boolean checkInput() {
        if (imageUri.equals("")) {
            showToast("Select Image", this);
            return false;
        } else {
            return true;
        }
    }


    private void selectImage() {
        if (type.equals("POST")) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);

        }


    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    imageUri = result.getData().getData();
                    Glide.with(this)
                            .load(imageUri)
                            .into(mBinding.ivPost);
                }
            }
    );
}