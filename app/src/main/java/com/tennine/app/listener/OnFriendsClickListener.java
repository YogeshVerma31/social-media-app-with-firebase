package com.tennine.app.listener;

import com.tennine.app.model.UserModel;

public interface OnFriendsClickListener {
    void onFriendAddOrRemove(UserModel userModel,int position);
    void onViewProfile(UserModel userModel);
}
