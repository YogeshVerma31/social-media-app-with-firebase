package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.showToast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tennine.app.R;
import com.tennine.app.adapter.FeedAdapter;
import com.tennine.app.adapter.FriendsAdapter;
import com.tennine.app.databinding.ActivityMainBinding;
import com.tennine.app.listener.FriendListener;
import com.tennine.app.listener.OnFeedClickListener;
import com.tennine.app.model.NotificationModel;
import com.tennine.app.model.PostModel;
import com.tennine.app.model.User;
import com.tennine.app.network.ApiService;
import com.tennine.app.network.ApisClient;
import com.tennine.app.utils.Constants;
import com.tennine.app.utils.PreferenceManager;
import com.tennine.app.utils.SquareImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnFeedClickListener {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private ActivityMainBinding activityMainBinding;
    private String userName, userImage, userNickname, userId;
    private List<PostModel> postModelList;
    private FeedAdapter feedAdapter;
    private SquareImageView squareImageView;
    private PreferenceManager preferenceManager;
    private User receiverUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        getFeedData(new FriendListener() {
            @Override
            public void getBoolean(Boolean isConnected, DocumentSnapshot documentSnapshot, String id) {
                PostModel postModel = new PostModel();
                postModel.setPostId(documentSnapshot.getId());
                postModel.setPostLikes(documentSnapshot.getLong("postLikes"));
                postModel.setPostComments(documentSnapshot.getLong("postComments"));
                postModel.setPostDate(documentSnapshot.getLong("postDate"));
                postModel.setPostDescription(documentSnapshot.getString("postDescription"));
                postModel.setPostedBy(documentSnapshot.getString("postedBy"));
                postModel.setLocation(documentSnapshot.getString("location"));
                postModel.setPostImage(documentSnapshot.getString("postImage"));
                postModel.setUserName(documentSnapshot.getString("userName"));
                postModel.setImageUrl(documentSnapshot.getString("userImage"));
                postModel.setLikeOrNot(isConnected);
                postModelList.add(postModel);
                setDataToRV();
            }
        });
        setBottomImage();
        setListeners();
        getToken();

    }
    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token){
        preferenceManager.putString(Constants.KEY_FCM_TOKEN,token);
        FirebaseFirestore databse = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                databse.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
        documentReference.update(Constants.KEY_FCM_TOKEN,token)
                .addOnFailureListener(e-> showToast("Unable to update token",this));
    }

    private void generateNotification(String message){
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiverUser.token);
            JSONObject data = new JSONObject();
            data.put(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
            data.put(Constants.KEY_NAME, preferenceManager.getString(Constants.KEY_NAME));
            data.put(Constants.KEY_FCM_TOKEN, preferenceManager.getString(Constants.KEY_FCM_TOKEN));
            data.put(Constants.KEY_MESSAGE, message);
            JSONObject body = new JSONObject();
            body.put(Constants.REMOTE_MSG_DATA, data);
            body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendNotification(body.toString());


        } catch (Exception exception) {
            showToast(exception.getMessage(),this);
        }
    }

    private void sendNotification(String messageBody) {
        ApisClient.getClient().create(ApiService.class).sendMessage(
                Constants.getRemoteMsgHeaders(),
                messageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            JSONObject resonseJson = new JSONObject(response.body());
                            JSONArray results = resonseJson.getJSONArray("results");
                            if (resonseJson.getInt("failure") == 1) {
                                JSONObject error = (JSONObject) results.get(0);
                                showToast(error.getString("error"),MainActivity.this);
                                return;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showToast("Notification send Successfully",MainActivity.this);
                } else {
                    showToast("Error: " + response.code(),MainActivity.this);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });
    }


    private void getFeedData(FriendListener friendListener) {
        activityMainBinding.pbMainFeed.setVisibility(View.VISIBLE);
        activityMainBinding.srlFeed.setVisibility(View.GONE);
        postModelList.clear();
        firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
                .collection("FOLLOWING")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot documentSnapshot1 : list) {
                                firebaseFirestore.collection("posts").document(documentSnapshot1.getId())
                                        .collection("POSTS")
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                if (!queryDocumentSnapshots.isEmpty()) {
                                                    activityMainBinding.tvNoPost.setVisibility(View.GONE);
                                                    activityMainBinding.pbMainFeed.setVisibility(View.GONE);
                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                    for (DocumentSnapshot documentSnapshot : list) {
                                                        checkLikeOrNot(documentSnapshot, friendListener);
                                                    }
                                                } else {
                                                    setDataToRV();
                                                }
                                            }

                                        });
                            }

                        } else {
                           setDataToRV();
                        }
                    }
                });


    }

    private void checkLikeOrNot(DocumentSnapshot documentSnapshot, FriendListener friendListener) {

        firebaseFirestore.collection("posts").document(documentSnapshot.getString("postedBy"))
                .collection("POSTS").document(documentSnapshot.getId())
                .collection("LIKES")
                .document(preferenceManager.getString("id"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot1 = task.getResult();
                            if (documentSnapshot1.exists()) {
                                friendListener.getBoolean(true, documentSnapshot, documentSnapshot1.getId());
                            } else {
                                friendListener.getBoolean(false, documentSnapshot, documentSnapshot1.getId());
                            }
                            feedAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    private void setListeners() {
        activityMainBinding.ivSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FriendsActivity.class);
                intent.putExtra("username", userName);
                intent.putExtra("userimage", userImage);
                intent.putExtra("usernickname", userNickname);
                intent.putExtra("id", userId);
                startActivity(intent);
            }
        });

        activityMainBinding.llUserHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserProfile.class);
                startActivity(intent);
            }
        });

        activityMainBinding.llReelHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReelsActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("userImage", userImage);
                startActivity(intent);
            }
        });

        activityMainBinding.ivPostHome.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PostActivity.class)
                    .putExtra("userName", userName)
                    .putExtra("userImage", userImage)
                    .putExtra("type", "POST"));
        });

        activityMainBinding.llNotiBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NotificationActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("userImage", userImage);
                startActivity(intent);
            }
        });

        activityMainBinding.srlFeed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postModelList.clear();
                getFeedData(new FriendListener() {
                    @Override
                    public void getBoolean(Boolean isConnected, DocumentSnapshot documentSnapshot, String id) {
                        PostModel postModel = new PostModel();
                        postModel.setPostId(documentSnapshot.getId());
                        postModel.setPostLikes(documentSnapshot.getLong("postLikes"));
                        postModel.setPostComments(documentSnapshot.getLong("postComments"));
                        postModel.setPostDate(documentSnapshot.getLong("postDate"));
                        postModel.setPostDescription(documentSnapshot.getString("postDescription"));
                        postModel.setPostedBy(documentSnapshot.getString("postedBy"));
                        postModel.setLocation(documentSnapshot.getString("location"));
                        postModel.setPostImage(documentSnapshot.getString("postImage"));
                        postModel.setUserName(documentSnapshot.getString("userName"));
                        postModel.setImageUrl(documentSnapshot.getString("userImage"));
                        postModel.setLikeOrNot(isConnected);
                        postModelList.add(postModel);
                    }
                });
                activityMainBinding.srlFeed.setRefreshing(false);
            }
        });


    }

    private void setBottomImage() {
        firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            activityMainBinding.ivProfileImage
                                    .setImageBitmap(getBitmapFromEncodedString(documentSnapshot.get("image").toString()));
                            activityMainBinding.tvUserNameHome.setText(documentSnapshot.get("username").toString());
                            userId = documentSnapshot.get("id").toString();
                            userImage = documentSnapshot.get("image").toString();
                            userNickname = documentSnapshot.get("nickname").toString();
                            userName = documentSnapshot.get("username").toString();
                        }
                    }
                });
    }


    private void setDataToRV() {
        activityMainBinding.srlFeed.setVisibility(View.VISIBLE);
        if (postModelList.isEmpty()) {
            activityMainBinding.tvNoPost.setVisibility(View.VISIBLE);
            activityMainBinding.pbMainFeed.setVisibility(View.GONE);
        } else {
            activityMainBinding.tvNoPost.setVisibility(View.GONE);
            activityMainBinding.pbMainFeed.setVisibility(View.GONE);
        }
        feedAdapter = new FeedAdapter(postModelList, this, this);
        activityMainBinding.rvFeed.setAdapter(feedAdapter);
    }

    private void init() {
        preferenceManager = new PreferenceManager(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        postModelList = new ArrayList<>();
        receiverUser = new User();

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
    public void onLikeClick(View v, PostModel postModel, int position) {
        ImageView imageView = (ImageView) v;
        if (postModel.getLikeOrNot()) {
            firebaseFirestore.collection("posts").document(postModel.getPostedBy())
                    .collection("POSTS").document(postModel.getPostId())
                    .collection("LIKES")
                    .document(preferenceManager.getString("id"))
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                imageView.setImageTintList(getResources().getColorStateList(R.color.black));
                                if (task.isSuccessful()) {
                                    firebaseFirestore.collection("posts").document(postModel.getPostedBy())
                                            .collection("POSTS").document(postModel.getPostId())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot documentSnapshot = task.getResult();
                                                        firebaseFirestore.collection("posts").document(postModel.getPostedBy())
                                                                .collection("POSTS").document(postModel.getPostId())
                                                                .update("postLikes", Long.parseLong(documentSnapshot.get("postLikes").toString()) - 1)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            firebaseFirestore.collection("posts").document(postModel.getPostedBy())
                                                                                    .collection("POSTS").document(postModel.getPostId())
                                                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        DocumentSnapshot documentSnapshot = task.getResult();
                                                                                        postModelList.get(position).setPostLikes(documentSnapshot.getLong("postLikes"));
                                                                                        postModelList.get(position).setLikeOrNot(false);
                                                                                        feedAdapter.notifyItemChanged(position);
                                                                                    }
                                                                                }
                                                                            });

                                                                        } else {

                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            });


                                }
                            }
                        }
                    });

        } else {
            Map<String, String> map = new HashMap<>();
            map.put("like", "true");

            firebaseFirestore.collection("posts").document(postModel.getPostedBy())
                    .collection("POSTS").document(postModel.getPostId())
                    .collection("LIKES")
                    .document(preferenceManager.getString("id"))
                    .set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                imageView.setImageTintList(getResources().getColorStateList(R.color.red));
                                imageView.setEnabled(true);
                                if (task.isSuccessful()) {
                                    firebaseFirestore.collection("posts").document(postModel.getPostedBy())
                                            .collection("POSTS").document(postModel.getPostId())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot documentSnapshot = task.getResult();
                                                        firebaseFirestore.collection("posts").document(postModel.getPostedBy())
                                                                .collection("POSTS").document(postModel.getPostId())
                                                                .update("postLikes", Long.parseLong(documentSnapshot.get("postLikes").toString()) + 1)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            firebaseFirestore.collection("posts").document(postModel.getPostedBy())
                                                                                    .collection("POSTS").document(postModel.getPostId())
                                                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        DocumentSnapshot documentSnapshot = task.getResult();
                                                                                        postModelList.get(position).setPostLikes(documentSnapshot.getLong("postLikes"));
                                                                                        postModelList.get(position).setLikeOrNot(true);
                                                                                        feedAdapter.notifyItemChanged(position);

                                                                                        NotificationModel notificationModel = new NotificationModel();
                                                                                        notificationModel.setNotificationAt(new Date().getTime());
                                                                                        notificationModel.setPostedBy(preferenceManager.getString("id"));
                                                                                        notificationModel.setPostImage(postModel.getPostImage());
                                                                                        notificationModel.setType("like");
                                                                                        notificationModel.setPostId(postModel.getPostId());
                                                                                        notificationModel.setPostType("POST");
                                                                                        notificationModel.setCheckOpen(false);
                                                                                        notificationModel.setDescription("like your post");
                                                                                        notificationModel.setNotificationBy(preferenceManager.getString("id"));

                                                                                        if (postModel.getPostedBy().equals(preferenceManager.getString("id"))) {

                                                                                        } else {
                                                                                            firebaseFirestore.collection("NOTIFICATION")
                                                                                                    .document(postModel.getPostedBy())
                                                                                                    .collection("NOTIFICATIONS")
                                                                                                    .document()
                                                                                                    .set(notificationModel)
                                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Void unused) {
                                                                                                            firebaseFirestore.collection("users")
                                                                                                                    .document(postModel.getPostedBy())
                                                                                                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                                                @Override
                                                                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                                                    if (documentSnapshot.exists()){
                                                                                                                        receiverUser.token = documentSnapshot.getString("fcmToken");
                                                                                                                        receiverUser.id =documentSnapshot.getString("id");
                                                                                                                        receiverUser.image =documentSnapshot.getString("image");
                                                                                                                        receiverUser.name =documentSnapshot.getString("username");
                                                                                                                        generateNotification("like your post");
                                                                                                                    }
                                                                                                                }
                                                                                                            });
                                                                                                        }
                                                                                                    });
                                                                                        }


                                                                                    }
                                                                                }
                                                                            });

                                                                        } else {

                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            });


                                }
                            }
                        }
                    });
        }
    }


    @Override
    public void onCommentClick(PostModel postModel, int position) {
        Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
        intent.putExtra("postId", postModel.getPostId());
        intent.putExtra("postedBy", postModel.getPostedBy());
        intent.putExtra("userName", userName);
        intent.putExtra("userImage", userImage);
        intent.putExtra("type", "POST");
        startActivity(intent);
    }

    @Override
    public void onUserView(PostModel postModel, int position) {
        if (postModel.getPostedBy().equals(preferenceManager.getString("id"))) {
            startActivity(new Intent(MainActivity.this, UserProfile.class));
        } else {
            startActivity(new Intent(MainActivity.this, FriendProfile.class)
                    .putExtra("id", postModel.getPostedBy())
            );
        }


    }


}