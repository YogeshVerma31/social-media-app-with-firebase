package com.tennine.app.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tennine.app.R;
import com.tennine.app.databinding.ItemFollowingBinding;
import com.tennine.app.listener.OnClickListener;
import com.tennine.app.model.FollowingFollowersModel;

import java.util.List;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {
    private List<FollowingFollowersModel> followingFollowersModelList;
    private OnClickListener onClickListener;

    public FollowingAdapter(List<FollowingFollowersModel> followingFollowersModelList, OnClickListener onClickListener) {
        this.followingFollowersModelList = followingFollowersModelList;
        this.onClickListener = onClickListener;
    }
    @NonNull
    @Override
    public FollowingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemFollowingBinding itemFollowingBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_following,parent,false);
        return new ViewHolder(itemFollowingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingAdapter.ViewHolder holder, int position) {
        holder.itemFollowingBinding.setModel(followingFollowersModelList.get(position));
        holder.itemFollowingBinding.setItemCLickListener(onClickListener);
        holder.itemFollowingBinding.executePendingBindings();
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
        private ItemFollowingBinding itemFollowingBinding;

        public ViewHolder(ItemFollowingBinding itemFollowingBinding) {
            super(itemFollowingBinding.getRoot());
            this.itemFollowingBinding = itemFollowingBinding;
        }
    }
}
