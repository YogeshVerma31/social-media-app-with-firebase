package com.tennine.app.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tennine.app.R;

import com.tennine.app.databinding.ItemFriendsFollowersBinding;
import com.tennine.app.listener.OnClickListener;
import com.tennine.app.model.FollowingFollowersModel;
import com.tennine.app.model.UserModel;

import java.util.List;

public class FriendsFollowersAdapter extends RecyclerView.Adapter<FriendsFollowersAdapter.ViewHolder> {
    private List<FollowingFollowersModel> followingFollowersModelList;
    private OnClickListener onClickListener;

    public FriendsFollowersAdapter(List<FollowingFollowersModel> followingFollowersModelList, OnClickListener onClickListener) {
        this.followingFollowersModelList = followingFollowersModelList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FriendsFollowersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemFriendsFollowersBinding itemFollowerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_friends_followers, parent, false);
        return new ViewHolder(itemFollowerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsFollowersAdapter.ViewHolder holder, int position) {
        holder.itemFollowerBinding.setModel(followingFollowersModelList.get(position));
        holder.itemFollowerBinding.setOnCLickListener(onClickListener);
        holder.itemFollowerBinding.executePendingBindings();
    }

    public void updateList(List<FollowingFollowersModel> list){
        followingFollowersModelList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return followingFollowersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFriendsFollowersBinding itemFollowerBinding;

        public ViewHolder(ItemFriendsFollowersBinding itemFollowerBinding) {
            super(itemFollowerBinding.getRoot());
            this.itemFollowerBinding = itemFollowerBinding;
        }
    }
}