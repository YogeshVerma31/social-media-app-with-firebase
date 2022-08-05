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
import com.tennine.app.adapter.ReelsAdapter;
import com.tennine.app.databinding.ActivityReelsBinding;
import com.tennine.app.model.ReelsModel;
import com.tennine.app.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReelsActivity extends AppCompatActivity {
    private ActivityReelsBinding mBinding;
    private static List<ReelsModel> reelsModelList;
    private static ReelsAdapter reelsAdapter;

    private String userName,userImage;
    private FirebaseFirestore firebaseFirestore;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityReelsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        getData();
        getBundleData();
        setListeners();
    }

    private void getBundleData() {
        userName = getIntent().getStringExtra("userName");
        userImage = getIntent().getStringExtra("userImage");
    }

    private void getData() {
        firebaseFirestore.collection("REELS")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot:list){
                                ReelsModel reelsModel = new ReelsModel();
                                firebaseFirestore.collection("REELS")
                                        .document(documentSnapshot.getString("videoId"))
                                        .collection("LIKES")
                                        .document(preferenceManager.getString("id"))
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    reelsModel.setPresentUserLike(true);
                                                }else{
                                                    reelsModel.setPresentUserLike(false);
                                                }
                                                    reelsAdapter.notifyDataSetChanged();
                                            }
                                        });

                                firebaseFirestore.collection("REELS")
                                        .document(documentSnapshot.getString("videoId"))
                                        .collection("RATINGS")
                                        .document(preferenceManager.getString("id"))
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    reelsModel.setRating(true);
                                                    reelsModel.setNumRating(documentSnapshot.getLong("rating"));
                                                }else{
                                                    reelsModel.setRating(false);
                                                    reelsModel.setNumRating(0);
                                                }
                                                reelsAdapter.notifyDataSetChanged();
                                            }
                                        });

                                reelsModel.setUserId(documentSnapshot.getString("userId"));
                                reelsModel.setUserImage(documentSnapshot.getString("userImage"));
                                reelsModel.setUserName(documentSnapshot.getString("userName"));
                                reelsModel.setLikes(documentSnapshot.getLong("likes"));
                                reelsModel.setVideoId(documentSnapshot.getString("reelsId"));
                                reelsModel.setComments(documentSnapshot.getLong("comments"));
                                reelsModel.setShares(documentSnapshot.getLong("shares"));
                                reelsModel.setVideoDate(documentSnapshot.getLong("videoDate"));
                                reelsModel.setVideoTimeStamp(documentSnapshot.getDate("videoTimeStamp"));
                                reelsModel.setVideoUrl(documentSnapshot.getString("videoUrl"));
                                reelsModel.setVideoDescrp(documentSnapshot.getString("videoDescp"));
                                reelsModel.setVideoId(documentSnapshot.getString("videoId"));
                                reelsModel.setVideoPlay(documentSnapshot.getLong("videoPlay"));
                                reelsModelList.add(reelsModel);
                            }

                            setDataToRv();

                        }else{

                        }
                    }
                });

    }

    private void setDataToRv() {
        reelsAdapter.notifyDataSetChanged();
        mBinding.vpReels.setAdapter(reelsAdapter);
    }

//    public static void updateList(Boolean isLike,int positon){
//        reelsModelList.get(positon).setPresentUserLike(isLike);
//        reelsAdapter.notifyItemChanged(positon);
//    }

    private void init() {
        reelsModelList = new ArrayList<>();
        reelsAdapter = new ReelsAdapter(reelsModelList,this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(this);
    }

    private void setListeners() {
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBinding.ivReelsPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReelsActivity.this,PostActivity.class)
                        .putExtra("userName", userName)
                        .putExtra("userImage", userImage)
                        .putExtra("type","REELS"));
            }
        });
    }
}