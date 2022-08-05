package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.showToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tennine.app.adapter.FriendsAdapter;
import com.tennine.app.databinding.ActivityFriendsBinding;
import com.tennine.app.listener.FriendListener;
import com.tennine.app.listener.OnFriendsClickListener;
import com.tennine.app.model.NotificationModel;
import com.tennine.app.model.UserModel;
import com.tennine.app.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class FriendsActivity extends AppCompatActivity implements OnFriendsClickListener {

    private ActivityFriendsBinding activityFriendsBinding;
    private List<UserModel> userModelList;
    private FriendsAdapter friendsAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Boolean isConnected = false;
    private Map<String, String> userMap;

    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFriendsBinding = ActivityFriendsBinding.inflate(getLayoutInflater());
        setContentView(activityFriendsBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        setListeners();
        getBundleData();
        getDataFromServer(new FriendListener() {
            @Override
            public void getBoolean(Boolean isConnected, DocumentSnapshot documentSnapshot, String id) {
                UserModel userModel = new UserModel(
                        documentSnapshot.get("id").toString(),
                        documentSnapshot.get("email").toString(),
                        documentSnapshot.get("username").toString(),
                        documentSnapshot.get("nickname").toString(),
                        documentSnapshot.get("nickname").toString(),
                        documentSnapshot.get("phone").toString(),
                        documentSnapshot.get("location").toString(),
                        documentSnapshot.get("bio").toString(),
                        documentSnapshot.get("image").toString(),
                        documentSnapshot.get("Lastname").toString(),
                        documentSnapshot.getLong("no_of_posts"),
                        documentSnapshot.getLong("no_of_posts"),
                        documentSnapshot.getLong("no_of_posts"),
                        isConnected
                );
                userModelList.add(userModel);
            }
        });

    }

    private void getBundleData() {
        userMap = new HashMap<>();
        userMap.put("id", getIntent().getStringExtra("id"));
        userMap.put("username", getIntent().getStringExtra("username"));
        userMap.put("nickname", getIntent().getStringExtra("usernickname"));
        userMap.put("image", getIntent().getStringExtra("userimage"));
    }

    private void setListeners() {
        activityFriendsBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activityFriendsBinding.edtSearchFriends.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });


    }

    private void filter(String text) {
        List<UserModel> temp = new ArrayList();
        for (UserModel d : userModelList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getUsername().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        friendsAdapter.updateList(temp);
    }

    private void init() {
        preferenceManager = new PreferenceManager(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userModelList = new ArrayList<>();
    }

    private void getDataFromServer(FriendListener listner) {
        firebaseFirestore.collection("users")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot documentSnapshot : list) {
                    if (documentSnapshot.get("username").equals(getIntent().getStringExtra("username"))) {
                        continue;
                    }
                    checkUserExists(documentSnapshot, listner);
                }
                setDataToRV();
            }
        });
    }


    private void checkUserExists(DocumentSnapshot documentSnapshot, FriendListener listener) {
        firebaseFirestore.collection("users").document(preferenceManager.getString("id")).collection("FOLLOWING")
                .whereEqualTo("username", documentSnapshot.get("username"))
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        listener.getBoolean(true, documentSnapshot, queryDocumentSnapshots.getDocuments().get(0).getId());
                        friendsAdapter.notifyDataSetChanged();
                    } else {
                        listener.getBoolean(false, documentSnapshot, "");
                        friendsAdapter.notifyDataSetChanged();
                    }
                });
    }


    private void setDataToRV() {
        activityFriendsBinding.pbFriends.setVisibility(View.GONE);
        activityFriendsBinding.edtSearchFriends.setVisibility(View.VISIBLE);
        activityFriendsBinding.rvFriends.setVisibility(View.VISIBLE);
        friendsAdapter = new FriendsAdapter(userModelList, this);
        activityFriendsBinding.rvFriends.setAdapter(friendsAdapter);
    }

    @Override
    public void onFriendAddOrRemove(UserModel userModel, int position) {
        if (userModel.isConnected) {
            firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
                    .collection("FOLLOWING").document(userModel.Id).delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                userModelList.get(position).setConnected(false);
                                friendsAdapter.notifyItemChanged(position);
                                firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                        if (task1.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task1.getResult();
                                            firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
                                                    .update("no_of_following", Long.parseLong(documentSnapshot.get("no_of_following").toString()) - 1)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {

                                                            } else {

                                                            }
                                                        }
                                                    });

                                        }
                                    }
                                });

                                firebaseFirestore.collection("users").document(userModel.Id)
                                        .collection("FOLLOWERS").document(preferenceManager.getString("id"))
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    firebaseFirestore.collection("users").document(userModel.Id)
                                                            .get().addOnCompleteListener(task1 -> {
                                                        if (task1.isSuccessful()) {
                                                            DocumentSnapshot documentSnapshot = task1.getResult();
                                                            firebaseFirestore.collection("users").document(userModel.Id)
                                                                    .update("no_of_followers", Long.parseLong(documentSnapshot.get("no_of_followers").toString()) - 1)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {

                                                                            } else {

                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    });
                                                }
                                            }
                                        });

                            } else {

                            }
                        }
                    });
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("id", userModel.Id);
            map.put("nickname", userModel.nickname);
            map.put("image", userModel.image);
            map.put("username", userModel.username);


            firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
                    .collection("FOLLOWING").document(userModel.Id)
                    .set(map)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            userModelList.get(position).setConnected(true);
                            friendsAdapter.notifyItemChanged(position);
                            firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
                                    .get().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task1.getResult();
                                    firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
                                            .update("no_of_following", Long.parseLong(documentSnapshot.get("no_of_following").toString()) + 1)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                    } else {

                                                    }
                                                }
                                            });
                                }
                            });

                        } else {
                        }
                    });

            firebaseFirestore.collection("users").document(userModel.Id)
                    .collection("FOLLOWERS").document(preferenceManager.getString("id"))
                    .set(userMap)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            userModelList.get(position).setConnected(true);
                            friendsAdapter.notifyItemChanged(position);
                            firebaseFirestore.collection("users").document(userModel.Id)
                                    .get().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task1.getResult();
                                    firebaseFirestore.collection("users").document(userModel.Id)
                                            .update("no_of_followers", Long.parseLong(documentSnapshot.get("no_of_followers").toString()) + 1)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        NotificationModel notificationModel = new NotificationModel();
                                                        notificationModel.setNotificationAt(new Date().getTime());
                                                        notificationModel.setPostedBy(preferenceManager.getString("id"));
                                                        notificationModel.setPostImage("");
                                                        notificationModel.setType("FOLLOW");
                                                        notificationModel.setPostId("");
                                                        notificationModel.setPostType("POST");
                                                        notificationModel.setCheckOpen(false);
                                                        notificationModel.setDescription("Started Following You");
                                                        notificationModel.setNotificationBy(preferenceManager.getString("id"));

                                                        firebaseFirestore.collection("NOTIFICATION")
                                                                .document(userModel.Id)
                                                                .collection("NOTIFICATIONS")
                                                                .document()
                                                                .set(notificationModel)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                    }

                                                                });

                                                    } else {

                                                    }
                                                }
                                            });
                                }
                            });

                        } else {
                        }
                    });


        }
    }

    @Override
    public void onViewProfile(UserModel userModel) {
        Intent intent = new Intent(FriendsActivity.this, FriendProfile.class);
        intent.putExtra("id", userModel.Id);
        startActivity(intent);
    }
}