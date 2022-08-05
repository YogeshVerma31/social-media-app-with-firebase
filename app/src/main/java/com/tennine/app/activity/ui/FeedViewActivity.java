package com.tennine.app.activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tennine.app.R;
import com.tennine.app.databinding.ActivityFeedViewBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class
FeedViewActivity extends AppCompatActivity {

    private ActivityFeedViewBinding mBinding;
    private String postId, userName, userImage, userid;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFeedViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        getBundleData();
        getDataFromServer();
        setListener();
    }

    private void setListener() {
        mBinding.appCompatImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        db = FirebaseFirestore.getInstance();
    }

    private void getBundleData() {
        postId = getIntent().getStringExtra("id");
        userName = getIntent().getStringExtra("userName");
        userImage = getIntent().getStringExtra("userImage");
        userid = getIntent().getStringExtra("userId");

    }

    private void getDataFromServer() {

        mBinding.tvUserName.setText(userName);
        Glide.with(this)
                .load(getBitmapFromEncodedString(userImage))
                .into(mBinding.ivProfile);

        db.collection("posts")
                .document(userid)
                .collection("POSTS")
                .document(postId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            mBinding.tvLikes.setText(documentSnapshot.getLong("postLikes")+"Likes");
                            mBinding.tvComments.setText(documentSnapshot.getLong("postComments")+"comments");
                            mBinding.tvDecription.setText(documentSnapshot.getString("postDescription"));
                            Glide.with(FeedViewActivity.this)
                                    .load(documentSnapshot.getString("postImage"))
                                    .into(mBinding.ivPost);
                            mBinding.tvLikesBottom.setText(documentSnapshot.getLong("postLikes") + "others");
                            db.collection("posts")
                                    .document(userid)
                                    .collection("POSTS")
                                    .document(postId)
                                    .collection("RATINGS")
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if (!queryDocumentSnapshots.isEmpty()) {
                                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                int totalRatings = list.size();
                                                long ratingCounts = 0;
                                                for (DocumentSnapshot documentSnapshot1 : list) {
                                                    ratingCounts = ratingCounts + documentSnapshot1.getLong("rating");
                                                }
                                                float rating = ratingCounts / totalRatings;
                                                mBinding.ratingBar.setRating(rating);
                                                if (rating <= 1) {
                                                    mBinding.ivEmoji.setVisibility(View.VISIBLE);
                                                    mBinding.ivEmoji.setImageResource(R.drawable.one_star);
                                                } else if (rating <= 2) {
                                                    mBinding.ivEmoji.setVisibility(View.VISIBLE);
                                                    mBinding.ivEmoji.setImageResource(R.drawable.two_star);
                                                } else if (rating <= 3) {
                                                    mBinding.ivEmoji.setVisibility(View.VISIBLE);
                                                    mBinding.ivEmoji.setImageResource(R.drawable.three_star);
                                                } else if (rating <= 4) {
                                                    mBinding.ivEmoji.setVisibility(View.VISIBLE);
                                                    mBinding.ivEmoji.setImageResource(R.drawable.four_star);
                                                } else {
                                                    mBinding.ivEmoji.setVisibility(View.VISIBLE);
                                                    mBinding.ivEmoji.setImageResource(R.drawable.five_star);
                                                }

                                                animateImage();

                                            }
                                        }
                                    });
                        } else {

                        }
                    }
                });

    }

    public void animateImage() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        mBinding.ivEmoji.startAnimation(scaleAnimation);
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