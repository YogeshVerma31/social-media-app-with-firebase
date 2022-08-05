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
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tennine.app.R;
import com.tennine.app.adapter.FeedAdapter;
import com.tennine.app.adapter.ProfilePostAdapter;
import com.tennine.app.adapter.ReelsPostAdapter;
import com.tennine.app.databinding.ActivityFriendProfileBinding;
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

public class FriendProfile extends AppCompatActivity implements OnProfilePostListener {

    private ActivityFriendProfileBinding mBinding;
    private PreferenceManager preferenceManager;

    private FirebaseFirestore mfirebaseDb;
    private FirebaseAuth mfirebaseAuth;

    public static final int MAX_LINES = 5;
    public static final String TWO_SPACES = " ";

    private Boolean isSpannable = true;
    private List<UserModel> user;
    private long no_of_followers, no_of_following;
    private String userId;

    private List<PostModel> postModelList;
    private List<ReelsModel> reelsModelList;
    private ProfilePostAdapter feedAdapter;
    private ReelsPostAdapter reelsPostAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFriendProfileBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        getBundleData();
        setListeners();
        setData();
        setPostData();

    }

    private void getBundleData() {
        userId = getIntent().getStringExtra("id");
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

    private void doSpannableText(String bio) {
        mBinding.tvSpannablebio.post(new Runnable() {
            @Override
            public void run() {
                if (mBinding.tvSpannablebio.getLineCount() > MAX_LINES) {
                    isSpannable = true;
                    int lastCharShown = mBinding.tvSpannablebio.getLayout().getLineVisibleEnd(MAX_LINES - 1);
                    mBinding.tvSpannablebio.setMaxLines(MAX_LINES);

                    String moreString = getString(R.string.more);
                    String suffix = TWO_SPACES + moreString;

                    // 3 is a "magic number" but it's just basically the length of the ellipsis we're going to insert
                    String actionDisplayText = bio.substring(0, lastCharShown - suffix.length() - 3) + "..." + suffix;

                    SpannableString truncatedSpannableString = new SpannableString(actionDisplayText);
                    int startIndex = actionDisplayText.indexOf(moreString);
                    truncatedSpannableString.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_blue_light)), startIndex, startIndex + moreString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mBinding.tvSpannablebio.setText(truncatedSpannableString);
                }
            }
        });


    }

    private void setData() {
        Util.showProgressbar(this);
        mfirebaseDb.collection("users").document(userId)
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
                            showToast(error, FriendProfile.this);
                        }
                    }
                });
    }


    private void setPostData() {
        postModelList.clear();
        mBinding.tvNoPost.setVisibility(View.GONE);
        mBinding.rvPostProfile.setVisibility(View.GONE);
        mBinding.pbMainFeed.setVisibility(View.VISIBLE);
        mfirebaseDb.collection("posts").document(userId)
                .collection("POSTS")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            mBinding.pbMainFeed.setVisibility(View.GONE);
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
                            mBinding.tvNoPost.setVisibility(View.VISIBLE);
                            mBinding.pbMainFeed.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void setDataToRv() {
        mBinding.rvPostProfile.setVisibility(View.VISIBLE);
        feedAdapter = new ProfilePostAdapter(postModelList, this);
        mBinding.rvPostProfile.setAdapter(feedAdapter);
    }


    private void setDataToUI(List<UserModel> userModelList) {
        String fullbio = userModelList.get(0).bio;

        mBinding.ivUserProfile.setImageBitmap(getBitmapFromEncodedString(userModelList.get(0).image));
        mBinding.tvUserName.setText(userModelList.get(0).username);
        mBinding.tvFullName.setText((userModelList.get(0).fullname) + " " + (userModelList.get(0).lastname));
        mBinding.tvlocation.setText(userModelList.get(0).location);
        mBinding.tvNickName.setText(userModelList.get(0).nickname);
        mBinding.tvbio.setText(fullbio);
        mBinding.tvSpannablebio.setText(fullbio);
        mBinding.tvPost.setText(userModelList.get(0).no_of_posts + "");
        mBinding.tvFollowers.setText(prettyCount(userModelList.get(0).no_of_followers));
        mBinding.tvFollowing.setText(prettyCount(userModelList.get(0).no_of_following));

        doSpannableText(fullbio);

    }

    private void setReelsData() {
        reelsModelList.clear();
        mBinding.tvNoPost.setVisibility(View.GONE);
        mBinding.rvPostProfile.setVisibility(View.GONE);
        mBinding.pbMainFeed.setVisibility(View.VISIBLE);
        mfirebaseDb.collection("users").document(userId)
                .collection("REELS")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        mBinding.pbMainFeed.setVisibility(View.GONE);
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
                        mBinding.tvNoPost.setVisibility(View.VISIBLE);
                        mBinding.pbMainFeed.setVisibility(View.GONE);
                    }
                });
    }

    private void setDataToReels() {
        mBinding.rvPostProfile.setVisibility(View.VISIBLE);
        reelsPostAdapter = new ReelsPostAdapter(reelsModelList);
        mBinding.rvPostProfile.setAdapter(reelsPostAdapter);
    }

    private void setListeners() {
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBinding.tvFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendProfile.this, FriendsFollowFollowingActivity.class)
                        .putExtra("firstFragment", prettyCount(user.get(0).no_of_followers) + " Like")
                        .putExtra("secondFragment", prettyCount(user.get(0).no_of_following) + " Likes")
                        .putExtra("tabPosition", 0)
                        .putExtra("id", userId)
                );
            }
        });

        mBinding.tvFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendProfile.this, FriendsFollowFollowingActivity.class)
                        .putExtra("firstFragment", prettyCount(user.get(0).no_of_followers)+ " Like")
                        .putExtra("secondFragment", prettyCount(user.get(0).no_of_following) + " Likes")
                        .putExtra("tabPosition", 1)
                        .putExtra("id", userId)
                );
            }
        });

        mBinding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPostData();
            }
        });

        mBinding.btnReels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReelsData();
            }
        });

    }

    @Override
    public void userView(PostModel postModel) {
        startActivity(new Intent(FriendProfile.this,FeedViewActivity.class)
                .putExtra("userName",postModel.getUserName())
                .putExtra("userImage",postModel.getUserImage())
                .putExtra("id",postModel.getPostId())
                .putExtra("userId",postModel.getPostedBy())
        );
    }
}