package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.showToast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tennine.app.R;
import com.tennine.app.adapter.CommentsAdapter;
import com.tennine.app.adapter.FeedAdapter;
import com.tennine.app.databinding.ActivityCommentsBinding;
import com.tennine.app.model.CommentsModel;
import com.tennine.app.model.NotificationModel;
import com.tennine.app.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CommentsActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private ActivityCommentsBinding commentsBinding;
    private List<CommentsModel> commentsModelList;
    private CommentsAdapter commentsAdapter;
    private PreferenceManager preferenceManager;
    private String postedBy, type;
    private String postId, userName, userImage,comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentsBinding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(commentsBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        getIntentData();
//        getDataFromFirebase();
        listenComments();
        setListeners();

    }

    private void setListeners() {
        commentsBinding.layoutSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentsBinding.edtComments.getText().toString() != null) {

                    String commentId = String.valueOf(UUID.randomUUID());
                    Map<String, Object> map = new HashMap<>();
                    map.put("userId", postedBy);
                    map.put("userName", userName);
                    map.put("userImage", userImage);
                    map.put("commentsId", commentId);
                    map.put("textComments", commentsBinding.edtComments.getText().toString());
                    map.put("commentsDate", new Date().getTime());
                    map.put("timeStamp", new Date());
                    comments = commentsBinding.edtComments.getText().toString();
                    commentsBinding.edtComments.setText("");

                    if (type.equals("REELS")) {

                        firebaseFirestore.collection("REELS")
                                .document(postId)
                                .collection("COMMENTS")
                                .document(commentId)
                                .set(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        firebaseFirestore.collection("REELS")
                                                .document(postId)
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        firebaseFirestore.collection("REELS")
                                                                .document(postId)
                                                                .update("comments", documentSnapshot.getLong("comments") + 1)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        NotificationModel notificationModel = new NotificationModel();
                                                                        notificationModel.setNotificationAt(new Date().getTime());
                                                                        notificationModel.setPostedBy(preferenceManager.getString("id"));
                                                                        notificationModel.setPostImage(documentSnapshot.getString("videoUrl"));
                                                                        notificationModel.setType("REELS");
                                                                        notificationModel.setPostId(documentSnapshot.getString("videoId"));
                                                                        notificationModel.setCheckOpen(false);
                                                                        notificationModel.setDescription("comment your Reel -" + comments);
                                                                        notificationModel.setNotificationBy(preferenceManager.getString("id"));
                                                                        notificationModel.setPostType("REELS");
                                                                        if (documentSnapshot.getString("userId").equals(preferenceManager.getString("id"))) {

                                                                        } else {
                                                                            firebaseFirestore.collection("NOTIFICATION")
                                                                                    .document(postedBy)
                                                                                    .collection("NOTIFICATIONS")
                                                                                    .document()
                                                                                    .set(notificationModel)
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void unused) {

                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });

                    } else {
                        firebaseFirestore.collection("posts").document(postedBy)
                                .collection("POSTS")
                                .document(postId)
                                .collection("COMMENTS")
                                .document(commentId)
                                .set(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        firebaseFirestore.collection("posts")
                                                .document(postedBy)
                                                .collection("POSTS")
                                                .document(postId)
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        firebaseFirestore.collection("posts")
                                                                .document(postedBy)
                                                                .collection("POSTS")
                                                                .document(postId)
                                                                .update("postComments", documentSnapshot.getLong("postComments") + 1)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        NotificationModel notificationModel = new NotificationModel();
                                                                        notificationModel.setNotificationAt(new Date().getTime());
                                                                        notificationModel.setPostedBy(preferenceManager.getString("id"));
                                                                        notificationModel.setPostImage(documentSnapshot.getString("postImage"));
                                                                        notificationModel.setType("comment");
                                                                        notificationModel.setPostId(postId);
                                                                        notificationModel.setCheckOpen(false);
                                                                        notificationModel.setDescription("comment your post -" + commentsBinding.edtComments.getText().toString());
                                                                        notificationModel.setNotificationBy(preferenceManager.getString("id"));
                                                                        notificationModel.setPostType("POST");

                                                                        if (postedBy.equals(preferenceManager.getString("id"))) {

                                                                        } else {
                                                                            firebaseFirestore.collection("NOTIFICATION")
                                                                                    .document(postedBy)
                                                                                    .collection("NOTIFICATIONS")
                                                                                    .document()
                                                                                    .set(notificationModel)
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void unused) {

                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                });

                                    }
                                });
                    }

                } else {
                    showToast("Comments Empty", CommentsActivity.this);
                    return;
                }
            }
        });
    }

    private void listenComments() {
        if (type.equals("REELS")) {
            firebaseFirestore.collection("REELS")
                    .document(postId)
                    .collection("COMMENTS")
                    .addSnapshotListener(eventListener);
        } else {
            firebaseFirestore.collection("posts").document(postedBy)
                    .collection("POSTS").document(postId).collection("COMMENTS")
                    .addSnapshotListener(eventListener);
        }
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }

        if (value != null) {
            int count = commentsModelList.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    CommentsModel commentsModel = new CommentsModel();
                    commentsModel.setCommentsDate(Long.parseLong(documentChange.getDocument().get("commentsDate").toString()));
                    commentsModel.setCommentsId(documentChange.getDocument().getString("commentsId"));
                    commentsModel.setTextComments(documentChange.getDocument().getString("textComments"));
                    commentsModel.setUserName(documentChange.getDocument().getString("userName"));
                    commentsModel.setUserId(documentChange.getDocument().getString("userId"));
                    commentsModel.setUserImage(documentChange.getDocument().getString("userImage"));
                    commentsModel.commnetsDate = (documentChange.getDocument().getDate("timeStamp"));
                    commentsModelList.add(commentsModel);
                }

                setDataToRV();

            }

            Collections.sort(commentsModelList, (obj1, obj2) -> obj1.commnetsDate.compareTo(obj2.commnetsDate));
            if (count == 0) {
                commentsAdapter.notifyDataSetChanged();
            } else {
                commentsAdapter.notifyItemRangeInserted(commentsModelList.size(), commentsModelList.size());
                commentsBinding.rvComments.smoothScrollToPosition(commentsModelList.size() - 1);
            }
            commentsBinding.rvComments.setVisibility(View.VISIBLE);

        }
        commentsBinding.pbComments.setVisibility(View.GONE);
        int countAfter = commentsModelList.size();
        if (countAfter == 0) {
            commentsBinding.tvNoComment.setVisibility(View.VISIBLE);
        } else {
            commentsBinding.tvNoComment.setVisibility(View.GONE);

        }

    };


    private void getIntentData() {
        postedBy = getIntent().getStringExtra("postedBy");
        postId = getIntent().getStringExtra("postId");
        userName = getIntent().getStringExtra("userName");
        userImage = getIntent().getStringExtra("userImage");
        type = getIntent().getStringExtra("type");

    }

    private void getDataFromFirebase() {
        firebaseFirestore.collection("posts").document(postedBy).collection("POSTS")
                .document(postId)
                .collection("COMMENTS")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot documentSnapshot : list) {
                                commentsModelList.add(new CommentsModel(documentSnapshot.getString("commentsId"),
                                        documentSnapshot.getString("userId"),
                                        documentSnapshot.getString("userName"),
                                        documentSnapshot.getString("userImage"),
                                        documentSnapshot.getString("textComments"),
                                        documentSnapshot.getLong("commentsDate")
                                ));
                            }

                            setDataToRV();


                        } else {
                            commentsBinding.rvComments.setVisibility(View.GONE);
                            commentsBinding.pbComments.setVisibility(View.GONE);
                            commentsBinding.tvNoComment.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }

    private void setDataToRV() {
        commentsBinding.pbComments.setVisibility(View.GONE);
        commentsBinding.rvComments.setVisibility(View.VISIBLE);
        commentsAdapter = new CommentsAdapter(commentsModelList);
        commentsBinding.rvComments.setAdapter(commentsAdapter);
    }

    private void init() {
        preferenceManager = new PreferenceManager(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        commentsModelList = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(commentsModelList);

    }

}