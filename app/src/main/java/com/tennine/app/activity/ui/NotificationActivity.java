package com.tennine.app.activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tennine.app.R;
import com.tennine.app.adapter.NotificationAdapter;
import com.tennine.app.databinding.ActivityNotificationBinding;
import com.tennine.app.listener.NotificationListener;
import com.tennine.app.model.NotificationModel;
import com.tennine.app.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotificationActivity extends AppCompatActivity implements NotificationListener {

    private ActivityNotificationBinding mBinding;
    private FirebaseFirestore db;
    private PreferenceManager manager;
    private List<NotificationModel> notificationModelList;
    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        getDataFromServer();
        setListener();
    }

    private void setListener() {
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getDataFromServer() {

        db.collection("NOTIFICATION")
                .document(manager.getString("id"))
                .collection("NOTIFICATIONS")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot ds : list) {
                                NotificationModel model = new NotificationModel();
                                model.setNotificationBy(ds.getString("notificationBy"));
                                model.setNotificationAt(ds.getLong("notificationAt"));
                                model.setPostImage(ds.getString("postImage"));
                                model.setPostId(ds.getString("postId"));
                                model.setType(ds.getString("type"));
                                model.setPostType(ds.getString("postType"));
                                model.setPostedBy(ds.getString("postBy"));
                                model.setCheckOpen(ds.getBoolean("checkOpen"));
                                model.setDescription(ds.getString("description"));
                                notificationModelList.add(model);
                                setDataToRV();

                                db.collection("users")
                                        .document(ds.getString("notificationBy"))
                                        .get()
                                        .addOnSuccessListener(documentSnapshot -> {
                                            if (documentSnapshot.exists()) {
                                                model.setUserName(documentSnapshot.getString("username"));
                                                model.setUserImage(documentSnapshot.getString("image"));
                                                adapter.notifyDataSetChanged();
                                            } else {
                                                setDataToRV();
                                            }
                                            setDataToRV();
                                        });
                            }
                        } else {
                            setDataToRV();
                        }
                    }
                });
    }

    private void init() {
        notificationModelList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        manager = new PreferenceManager(this);
    }

    private void setDataToRV() {
        if (notificationModelList.isEmpty()) {
            mBinding.tvNoNotification.setVisibility(View.VISIBLE);
            mBinding.pbNotification.setVisibility(View.GONE);
        } else {
            mBinding.tvNoNotification.setVisibility(View.GONE);
            mBinding.pbNotification.setVisibility(View.GONE);
            adapter = new NotificationAdapter(notificationModelList,this);
            mBinding.rvNotification.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(NotificationModel model) {
        if (model.getType().equals("like")){
            startActivity(new Intent(NotificationActivity.this,FeedViewActivity.class)
                    .putExtra("id",model.getPostId())
                    .putExtra("userId",manager.getString("id"))
                    .putExtra("userName",getIntent().getStringExtra("userName"))
                    .putExtra("userImage",getIntent().getStringExtra("userImage"))
            );
        }else if (model.getType().equals("comment")){
            startActivity(new Intent(NotificationActivity.this,CommentsActivity.class)
                    .putExtra("postedBy",manager.getString("id"))
                    .putExtra("postId",model.getPostId())
                    .putExtra("userName",getIntent().getStringExtra("userName"))
                    .putExtra("userImage",getIntent().getStringExtra("userImage"))
                    .putExtra("type",model.getPostType())
            );
        }else{

        }
    }
}