package com.tennine.app.listener;

import android.view.View;
import android.widget.ImageView;

import com.tennine.app.model.PostModel;

public interface OnFeedClickListener {
    void onLikeClick(View view,PostModel postModel, int position);
    void onCommentClick(PostModel postModel,int position);
    void onUserView(PostModel postModel,int position);

}
