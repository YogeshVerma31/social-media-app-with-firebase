package com.tennine.app.activity.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tennine.app.R;
import com.tennine.app.activity.ui.FriendProfile;
import com.tennine.app.adapter.FollowingAdapter;
import com.tennine.app.databinding.FragmentFollowingBinding;
import com.tennine.app.databinding.FragmentFriendsFollowingBinding;
import com.tennine.app.listener.OnClickListener;
import com.tennine.app.model.FollowingFollowersModel;
import com.tennine.app.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;


public class FriendsFollowingFragment extends Fragment implements OnClickListener {

    public FriendsFollowingFragment() {
        // Required empty public constructor
    }

    private PreferenceManager preferenceManager;

    private FragmentFriendsFollowingBinding fragmentFollowingBinding;
    private List<FollowingFollowersModel> followingFollowersModelList;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FollowingAdapter followingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentFollowingBinding = FragmentFriendsFollowingBinding.inflate(inflater, container, false);
        init();
        getDataFromServer();
        setListener();

        return fragmentFollowingBinding.getRoot();
    }

    private void setListener() {
        fragmentFollowingBinding.edtSearchFollowing.addTextChangedListener(new TextWatcher() {
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

    private void filter(String text){
        List<FollowingFollowersModel> temp = new ArrayList();
        for(FollowingFollowersModel d: followingFollowersModelList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getUsername().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        followingAdapter.updateList(temp);
    }
    private void init() {

        preferenceManager = new PreferenceManager(getContext());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        followingFollowersModelList = new ArrayList<>();
    }

    private void getDataFromServer() {

        firebaseFirestore.collection("users").document(getActivity().getIntent().getStringExtra("id")).collection("FOLLOWING")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : list) {
                        if (getActivity().getIntent().getStringExtra("id").equals(documentSnapshot.getString("id"))) {
                            continue;
                        }
                        FollowingFollowersModel followingFollowersModel = new FollowingFollowersModel(
                                documentSnapshot.get("id").toString(), documentSnapshot.get("username").toString(), documentSnapshot.get("image").toString(), documentSnapshot.get("nickname").toString(), true
                        );
                        followingFollowersModelList.add(followingFollowersModel);
                    }
                    setDataToRV();
                } else {
                    fragmentFollowingBinding.tvNoData.setVisibility(View.VISIBLE);
                    fragmentFollowingBinding.pbFollowing.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setDataToRV() {

        if (followingFollowersModelList.isEmpty()) {
            fragmentFollowingBinding.tvNoData.setVisibility(View.VISIBLE);
            fragmentFollowingBinding.pbFollowing.setVisibility(View.GONE);
            fragmentFollowingBinding.edtSearchFollowing.setVisibility(View.GONE);

        } else {
            fragmentFollowingBinding.pbFollowing.setVisibility(View.GONE);
            fragmentFollowingBinding.edtSearchFollowing.setVisibility(View.VISIBLE);
            fragmentFollowingBinding.rvFollowing.setVisibility(View.VISIBLE);
            fragmentFollowingBinding.edtSearchFollowing.setVisibility(View.VISIBLE);
            followingAdapter = new FollowingAdapter(followingFollowersModelList, this);
            fragmentFollowingBinding.rvFollowing.setAdapter(followingAdapter);
        }

    }


    @Override
    public void onILikeClick(FollowingFollowersModel followingFollowersModel, int position) {

    }

    @Override
    public void onRemoveUser(FollowingFollowersModel followingFollowersModel, int position) {

    }

    @Override
    public void onUserViewCLick(FollowingFollowersModel followingFollowersModel, int position) {
        Intent intent = new Intent(getContext(), FriendProfile.class);
        intent.putExtra("id",followingFollowersModel.getUserId());
        startActivity(intent);
    }
}