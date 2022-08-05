package com.tennine.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tennine.app.R;
import com.tennine.app.databinding.ItemCommentsBinding;
import com.tennine.app.databinding.ItemHomeLayoutBinding;
import com.tennine.app.model.CommentsModel;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private List<CommentsModel> commentsModelList;

    public CommentsAdapter(List<CommentsModel> commentsModelList) {
        this.commentsModelList = commentsModelList;
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCommentsBinding itemCommentsBinding = DataBindingUtil.inflate(layoutInflater,R.layout.item_comments,parent,false);
        return new ViewHolder(itemCommentsBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        holder.itemCommentsBinding.setModel(commentsModelList.get(position));
        holder.itemCommentsBinding.setPosition(position);
        holder.itemCommentsBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return commentsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCommentsBinding itemCommentsBinding;
        public ViewHolder(@NonNull ItemCommentsBinding itemCommentsBinding) {
            super(itemCommentsBinding.getRoot());
            this.itemCommentsBinding = itemCommentsBinding;
        }
    }
}
