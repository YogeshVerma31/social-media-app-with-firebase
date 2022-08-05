package com.tennine.app.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tennine.app.R;
import com.tennine.app.databinding.ItemPostReelsBinding;
import com.tennine.app.databinding.ItemProfilePostBinding;
import com.tennine.app.model.ReelsModel;

import java.util.List;

public class ReelsPostAdapter extends RecyclerView.Adapter<ReelsPostAdapter.ViewHolder> {
    private List<ReelsModel> reelsModelList;

    public ReelsPostAdapter(List<ReelsModel> reelsModelList) {
        this.reelsModelList = reelsModelList;
    }

    @NonNull
    @Override
    public ReelsPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPostReelsBinding itemProfilePostBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_post_reels, parent, false);
        return new ViewHolder(itemProfilePostBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReelsPostAdapter.ViewHolder holder, int position) {
        holder.itemPostReelsBinding.setModel(reelsModelList.get(position));
        holder.itemPostReelsBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return reelsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemPostReelsBinding itemPostReelsBinding;
        public ViewHolder(@NonNull ItemPostReelsBinding itemPostReelsBinding) {
            super(itemPostReelsBinding.getRoot());
            this.itemPostReelsBinding= itemPostReelsBinding;
        }
    }
}
