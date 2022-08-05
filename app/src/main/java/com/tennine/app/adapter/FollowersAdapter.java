package com.tennine.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tennine.app.R;
import com.tennine.app.databinding.ItemFollowerBinding;
import com.tennine.app.listener.OnClickListener;
import com.tennine.app.model.FollowingFollowersModel;
import com.tennine.app.model.UserModel;

import java.util.List;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {
    private List<FollowingFollowersModel> followingFollowersModelList;
    private OnClickListener onClickListener;

    public FollowersAdapter(List<FollowingFollowersModel> followingFollowersModelList, OnClickListener onClickListener) {
        this.followingFollowersModelList = followingFollowersModelList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FollowersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemFollowerBinding itemFollowerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_follower,parent,false);
        return new ViewHolder(itemFollowerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersAdapter.ViewHolder holder, int position) {
        holder.itemFollowerBinding.setModel(followingFollowersModelList.get(position));
        holder.itemFollowerBinding.setOnCLickListener(onClickListener);
        holder.itemFollowerBinding.setPosition(position);
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
        private ItemFollowerBinding itemFollowerBinding;

        public ViewHolder(ItemFollowerBinding itemFollowerBinding) {
            super(itemFollowerBinding.getRoot());
            this.itemFollowerBinding = itemFollowerBinding;
        }
    }
}
