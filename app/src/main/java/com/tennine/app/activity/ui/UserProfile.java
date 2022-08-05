package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.prettyCount;
import static com.tennine.app.utils.Util.showToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.tennine.app.R;
import com.tennine.app.adapter.FeedAdapter;
import com.tennine.app.adapter.ProfilePostAdapter;
import com.tennine.app.adapter.ReelsPostAdapter;
import com.tennine.app.databinding.ActivityUserProfileBinding;
import com.tennine.app.listener.OnFeedClickListener;
import com.tennine.app.listener.OnProfilePostListener;
import com.tennine.app.model.PostModel;
import com.tennine.app.model.ReelsModel;
import com.tennine.app.model.UserModel;
import com.tennine.app.utils.PreferenceManager;
import com.tennine.app.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserProfile extends AppCompatActivity implements View.OnClickListener, OnProfilePostListener {

    private ActivityUserProfileBinding activityUserProfileBinding;

    private PreferenceManager preferenceManager;

    private FirebaseFirestore mfirebaseDb;
    private FirebaseAuth mfirebaseAuth;

    public static final int MAX_LINES = 5;
    public static final String TWO_SPACES = " ";

    private Boolean isSpannable = true;
    private List<UserModel> user;
    private long no_of_followers, no_of_following;

    private String username, website, nickname, bio, userImage;

    private List<PostModel> postModelList;
    private List<ReelsModel> reelsModelList;
    private ProfilePostAdapter feedAdapter;
    private ReelsPostAdapter reelsPostAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserProfileBinding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(activityUserProfileBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        setListeners();
        setData();
        setPostData();
    }

    private void setReelsData() {
        reelsModelList.clear();
        activityUserProfileBinding.tvNoPost.setVisibility(View.GONE);
        activityUserProfileBinding.rvPostProfile.setVisibility(View.GONE);
        activityUserProfileBinding.pbMainFeed.setVisibility(View.VISIBLE);
        mfirebaseDb.collection("users").document(preferenceManager.getString("id"))
                .collection("REELS")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        activityUserProfileBinding.pbMainFeed.setVisibility(View.GONE);
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot documentSnapshot : list) {

                            mfirebaseDb.collection("REELS")
                                    .document(documentSnapshot.getString("videoId"))
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            ReelsModel reelsModel = new ReelsModel();
                                            reelsModel.setVideoId(documentSnapshot.getString("videoId"));
                                            reelsModel.setVideoUrl(documentSnapshot.getString("videoUrl"));
                                            reelsModel.setVideoPlay(documentSnapshot.getLong("videoPlay"));
                                            reelsModelList.add(reelsModel);
                                            reelsPostAdapter.notifyDataSetChanged();
                                        }
                                    });

                        }
                        setDataToReels();
                    } else {
                        activityUserProfileBinding.tvNoPost.setVisibility(View.VISIBLE);
                        activityUserProfileBinding.pbMainFeed.setVisibility(View.GONE);
                    }
                });
    }

    private void setDataToReels() {
        activityUserProfileBinding.rvPostProfile.setVisibility(View.VISIBLE);
        reelsPostAdapter = new ReelsPostAdapter(reelsModelList);
        activityUserProfileBinding.rvPostProfile.setAdapter(reelsPostAdapter);
    }

    private void setPostData() {
        postModelList.clear();
        activityUserProfileBinding.tvNoPost.setVisibility(View.GONE);
        activityUserProfileBinding.rvPostProfile.setVisibility(View.GONE);
        activityUserProfileBinding.pbMainFeed.setVisibility(View.VISIBLE);
        mfirebaseDb.collection("posts").document(preferenceManager.getString("id"))
                .collection("POSTS")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            activityUserProfileBinding.pbMainFeed.setVisibility(View.GONE);
                            List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                PostModel postModel = new PostModel();
                                postModel.setPostId(documentSnapshot.getId());
                                postModel.setPostImage(documentSnapshot.getString("postImage"));
                                postModel.setUserName(documentSnapshot.getString("userName"));
                                postModel.setPostedBy(documentSnapshot.getString("postedBy"));
                                postModel.setPostId(documentSnapshot.getString("postId"));
                                postModelList.add(postModel);
                            }
                            setDataToRv();
                        } else {
                            activityUserProfileBinding.tvNoPost.setVisibility(View.VISIBLE);
                            activityUserProfileBinding.pbMainFeed.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void setDataToRv() {
        activityUserProfileBinding.rvPostProfile.setVisibility(View.VISIBLE);
        feedAdapter = new ProfilePostAdapter(postModelList, this);
        activityUserProfileBinding.rvPostProfile.setAdapter(feedAdapter);
    }

    private void setListeners() {

        activityUserProfileBinding.tvbio.setOnClickListener(this);

        activityUserProfileBinding.tvSpannablebio.setOnClickListener(this);

        activityUserProfileBinding.cvFollowers.setOnClickListener(this);

        activityUserProfileBinding.cvFollowing.setOnClickListener(this);
        activityUserProfileBinding.ivBack.setOnClickListener(this);
        activityUserProfileBinding.btnEditProfile.setOnClickListener(this);
        activityUserProfileBinding.btnPost.setOnClickListener(this);
        activityUserProfileBinding.btnReels.setOnClickListener(this);
        activityUserProfileBinding.btnSetting.setOnClickListener(this);


    }

    private void doSpannableText(String bio) {
        activityUserProfileBinding.tvSpannablebio.post(new Runnable() {
            @Override
            public void run() {
                if (activityUserProfileBinding.tvSpannablebio.getLineCount() > MAX_LINES) {
                    isSpannable = true;
                    int lastCharShown = activityUserProfileBinding.tvSpannablebio.getLayout().getLineVisibleEnd(MAX_LINES - 1);
                    activityUserProfileBinding.tvSpannablebio.setMaxLines(MAX_LINES);

                    String moreString = getString(R.string.more);
                    String suffix = TWO_SPACES + moreString;

                    // 3 is a "magic number" but it's just basically the length of the ellipsis we're going to insert
                    String actionDisplayText = bio.substring(0, lastCharShown - suffix.length() - 3) + "..." + suffix;

                    SpannableString truncatedSpannableString = new SpannableString(actionDisplayText);
                    int startIndex = actionDisplayText.indexOf(moreString);
                    truncatedSpannableString.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_blue_light)), startIndex, startIndex + moreString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    activityUserProfileBinding.tvSpannablebio.setText(truncatedSpannableString);
                }
            }
        });


    }

    private void setData() {
        Util.showProgressbar(this);
        mfirebaseDb.collection("users").document(preferenceManager.getString("id"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Util.dismissProgressDialog();
                        if (task.isSuccessful() && task.getResult() != null) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            user = new ArrayList<>();
                            UserModel user1 = new UserModel();
                            user1.bio = documentSnapshot.getString("bio");
                            user1.email = documentSnapshot.getString("email");
                            user1.phoneNumber = documentSnapshot.getString("phone");
                            user1.nickname = documentSnapshot.getString("nickname");
                            user1.fullname = documentSnapshot.getString("firstname");
                            user1.lastname = documentSnapshot.getString("Lastname");
                            user1.username = documentSnapshot.getString("username");
                            user1.location = documentSnapshot.getString("location");
                            user1.image = documentSnapshot.getString("image");
                            user1.no_of_followers = documentSnapshot.getLong("no_of_followers");
                            user1.no_of_following = documentSnapshot.getLong("no_of_following");
                            user1.no_of_posts = documentSnapshot.getLong("no_of_posts");
                            user.add(user1);
                            setDataToUI(user);

                        } else {
                            String error = task.getException().getMessage();
                            showToast(error, UserProfile.this);
                        }
                    }
                });


    }


    private void setDataToUI(List<UserModel> userModelList) {
        String fullbio = userModelList.get(0).bio;
        userImage = userModelList.get(0).image;
        nickname = userModelList.get(0).nickname;
        website = "";
        bio = fullbio;
        username = userModelList.get(0).username;

        activityUserProfileBinding.ivUserProfile.setImageBitmap(getBitmapFromEncodedString(userModelList.get(0).image));
        activityUserProfileBinding.tvUserName.setText(userModelList.get(0).username);
        activityUserProfileBinding.tvFullName.setText((userModelList.get(0).fullname) + " " + (userModelList.get(0).lastname));
        activityUserProfileBinding.tvlocation.setText(userModelList.get(0).location);
        activityUserProfileBinding.tvNickName.setText(userModelList.get(0).nickname);
        activityUserProfileBinding.tvbio.setText(fullbio);
        activityUserProfileBinding.tvSpannablebio.setText(fullbio);
        activityUserProfileBinding.tvPost.setText(prettyCount(userModelList.get(0).no_of_posts)+"");
        activityUserProfileBinding.tvFollowers.setText(prettyCount(userModelList.get(0).no_of_followers));
        activityUserProfileBinding.tvFollowing.setText(prettyCount(userModelList.get(0).no_of_following));

        doSpannableText(fullbio);

    }

    private void init() {
        preferenceManager = new PreferenceManager(this);
        mfirebaseDb = FirebaseFirestore.getInstance();
        mfirebaseAuth = FirebaseAuth.getInstance();
        postModelList = new ArrayList<>();
        reelsModelList = new ArrayList<>();
    }


    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        if (encodedImage != null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvbio:
                activityUserProfileBinding.tvbio.setVisibility(View.GONE);
                activityUserProfileBinding.tvSpannablebio.setVisibility(View.VISIBLE);
                break;
            case R.id.tvSpannablebio:
                activityUserProfileBinding.tvbio.setVisibility(View.VISIBLE);
                activityUserProfileBinding.tvSpannablebio.setVisibility(View.GONE);
                break;
            case R.id.cvFollowers:
                startActivity(new Intent(UserProfile.this, FollowingFollowersActivity.class)
                        .putExtra("firstFragment", prettyCount(user.get(0).no_of_followers)+"Like")
                        .putExtra("secondFragment", prettyCount(user.get(0).no_of_following)+"Likes")
                        .putExtra("tabPosition", 0)
                        .putExtra("id", preferenceManager.getString("id"))
                );
                break;
            case R.id.cvFollowing:
                startActivity(new Intent(UserProfile.this, FollowingFollowersActivity.class)
                        .putExtra("firstFragment", prettyCount(user.get(0).no_of_followers)+"Like")
                        .putExtra("secondFragment", prettyCount(user.get(0).no_of_following)+"Likes")
                        .putExtra("tabPosition", 1)
                        .putExtra("id", preferenceManager.getString("id"))
                );
                break;
            case R.id.btn_editProfile:
                startActivity(new Intent(UserProfile.this, EditProfile.class)
                        .putExtra("image", userImage)
                        .putExtra("username", username)
                        .putExtra("nickname", nickname)
                        .putExtra("bio", bio)
                        .putExtra("website", website)
                );
                break;

            case R.id.btnSetting:
                startActivity(new Intent(UserProfile.this, SettingActivity.class));
                break;

            case R.id.btnPost:
                setPostData();
                break;

            case R.id.btnReels:
                setReelsData();
                break;

            case R.id.ivBack:
                onBackPressed();
                break;
        }

    }

    @Override
    public void userView(PostModel postModel) {

        startActivity(new Intent(UserProfile.this,FeedViewActivity.class)
                .putExtra("userName",username)
                .putExtra("userImage",userImage)
                .putExtra("id",postModel.getPostId())
                .putExtra("userId",postModel.getPostedBy())
        );

    }
}