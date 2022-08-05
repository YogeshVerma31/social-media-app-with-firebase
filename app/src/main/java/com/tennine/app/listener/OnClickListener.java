package com.tennine.app.listener;

import android.view.View;

import com.tennine.app.model.FollowingFollowersModel;
import com.tennine.app.model.UserModel;

public interface OnClickListener {
    void onILikeClick(FollowingFollowersModel followingFollowersModel,int position);
    void onRemoveUser(FollowingFollowersModel followingFollowersModel,int position);
    void onUserViewCLick(FollowingFollowersModel followingFollowersModel,int position);
}
