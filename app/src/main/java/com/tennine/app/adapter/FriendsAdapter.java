package com.tennine.app.adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tennine.app.R;
import com.tennine.app.databinding.ItemFriendsBinding;
import com.tennine.app.listener.OnClickListener;
import com.tennine.app.listener.OnFriendsClickListener;
import com.tennine.app.model.UserModel;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private List<UserModel> userModelList;
    private OnFriendsClickListener onClickListener;

    public FriendsAdapter(List<UserModel> userModelList, OnFriendsClickListener onClickListener) {
        this.userModelList = userModelList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemFriendsBinding itemFriendsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_friends,parent,false);
        return new ViewHolder(itemFriendsBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder holder, int position) {
       holder.itemFriendsBinding.setModel(userModelList.get(position));
       holder.itemFriendsBinding.setItemCLickListener(onClickListener);
       holder.itemFriendsBinding.setPosition(position);
       holder.itemFriendsBinding.executePendingBindings();
    }

    public void updateList(List<UserModel> list){
        userModelList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFriendsBinding itemFriendsBinding;

        public ViewHolder(ItemFriendsBinding itemFriendsBinding) {
            super(itemFriendsBinding.getRoot());
            this.itemFriendsBinding = itemFriendsBinding;
        }
    }
}
