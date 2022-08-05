package com.tennine.app.activity.fragment;

import static com.tennine.app.utils.Util.showToast;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tennine.app.R;
import com.tennine.app.activity.ui.FriendProfile;
import com.tennine.app.activity.ui.UserProfile;
import com.tennine.app.adapter.FollowersAdapter;
import com.tennine.app.adapter.FriendsFollowersAdapter;
import com.tennine.app.databinding.FragmentFollowersBinding;
import com.tennine.app.databinding.FragmentFriendsFollowersBinding;
import com.tennine.app.listener.FriendListener;
import com.tennine.app.listener.OnClickListener;
import com.tennine.app.model.FollowingFollowersModel;
import com.tennine.app.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class FriendsFollowersFragment extends Fragment implements OnClickListener {


    public FriendsFollowersFragment() {
        // Required empty public constructor
    }

    private PreferenceManager preferenceManager;

    private FragmentFriendsFollowersBinding fragmentFollowersBinding;
    private List<FollowingFollowersModel> followingFollowersModelList;
    private FirebaseFirestore firebaseFirestore;
    private FriendsFollowersAdapter followersAdapter;
    private Boolean isConnected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFollowersBinding = FragmentFriendsFollowersBinding.inflate(inflater, container, false);
        init();
        setListener();
        getDataFromServer(new FriendListener() {
            @Override
            public void getBoolean(Boolean isConnected, DocumentSnapshot documentSnapshot, String id) {
                FollowingFollowersModel followingFollowersModel = new FollowingFollowersModel(
                        documentSnapshot.get("id").toString(),
                        documentSnapshot.get("username").toString(),
                        documentSnapshot.get("image").toString(),
                        documentSnapshot.get("nickname").toString(),
                        isConnected
                );
                followingFollowersModelList.add(followingFollowersModel);
            }
        });

        return fragmentFollowersBinding.getRoot();
    }

    private void setListener() {
        fragmentFollowersBinding.edtSearchFollowers.addTextChangedListener(new TextWatcher() {
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
        List<FollowingFollowersModel> temp = new ArrayList();
        for (FollowingFollowersModel d : followingFollowersModelList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getUsername().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview
        followersAdapter.updateList(temp);
    }

    private void init() {
        preferenceManager = new PreferenceManager(getContext());
        firebaseFirestore = FirebaseFirestore.getInstance();
        followingFollowersModelList = new ArrayList<>();
    }

    private void getDataFromServer(FriendListener friendListener) {
        firebaseFirestore.collection("users").document(getActivity().getIntent().getStringExtra("id")).
                collection("FOLLOWERS")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : list) {
                        checkUserExists(documentSnapshot, friendListener);
                    }
                    setDataToRV();
                } else {
                    fragmentFollowersBinding.tvNoData.setVisibility(View.VISIBLE);
                    fragmentFollowersBinding.pbFollowers.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setDataToRV() {
        fragmentFollowersBinding.pbFollowers.setVisibility(View.GONE);
        fragmentFollowersBinding.edtSearchFollowers.setVisibility(View.VISIBLE);
        fragmentFollowersBinding.rvFollowers.setVisibility(View.VISIBLE);
        followersAdapter = new FriendsFollowersAdapter(followingFollowersModelList, this);
        fragmentFollowersBinding.rvFollowers.setAdapter(followersAdapter);
    }

    private void checkUserExists(DocumentSnapshot documentSnapshot, FriendListener listener) {
        firebaseFirestore.collection("users").document(preferenceManager.getString("id")).collection("FOLLOWING")
                .whereEqualTo("username", documentSnapshot.get("username"))
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        listener.getBoolean(true, documentSnapshot, queryDocumentSnapshots.getDocuments().get(0).getId());
                        followersAdapter.notifyDataSetChanged();
                    } else {
                        listener.getBoolean(false, documentSnapshot, "");
                        followersAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onILikeClick(FollowingFollowersModel followingFollowersModel, int position) {

    }

    @Override
    public void onRemoveUser(FollowingFollowersModel followingFollowersModel, int position) {
//        firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
//                .collection("FOLLOWERS").document(followingFollowersModel.getUserId()).delete()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            followingFollowersModelList.remove(position);
//                            followersAdapter.notifyItemChanged(position);
//                            firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
//                                    .get().addOnCompleteListener(task1 -> {
//                                if (task1.isSuccessful()) {
//                                    DocumentSnapshot documentSnapshot = task1.getResult();
//                                    firebaseFirestore.collection("users").document(preferenceManager.getString("id"))
//                                            .update("no_of_followers", Long.parseLong(documentSnapshot.get("no_of_followers").toString()) - 1)
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    if (task.isSuccessful()) {
//
//                                                    } else {
//
//                                                    }
//                                                }
//                                            });
//
//                                }
//                            });
//
//                            firebaseFirestore.collection("users").document(followingFollowersModel.getUserId())
//                                    .collection("FOLLOWING").document(preferenceManager.getString("id"))
//                                    .delete()
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()){
//                                                firebaseFirestore.collection("users").document(followingFollowersModel.getUserId())
//                                                        .get().addOnCompleteListener(task1 -> {
//                                                    if (task1.isSuccessful()) {
//                                                        DocumentSnapshot documentSnapshot = task1.getResult();
//                                                        firebaseFirestore.collection("users").document(followingFollowersModel.getUserId())
//                                                                .update("no_of_following", Long.parseLong(documentSnapshot.get("no_of_following").toString()) - 1)
//                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                    @Override
//                                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                                        if (task.isSuccessful()) {
//
//                                                                        } else {
//
//                                                                        }
//                                                                    }
//                                                                });
//                                                    }
//                                                });
//                                            }
//                                        }
//                                    });
//
//                        } else {
//
//                        }
//                    }
//                });
    }


    @Override
    public void onUserViewCLick(FollowingFollowersModel followingFollowersModel, int position) {
        if (followingFollowersModel.getUserId().equals(preferenceManager.getString("id"))) {
            Intent intent = new Intent(getContext(), UserProfile.class);
            intent.putExtra("id", followingFollowersModel.getUserId());
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), FriendProfile.class);
            intent.putExtra("id", followingFollowersModel.getUserId());
            startActivity(intent);
        }
    }
}