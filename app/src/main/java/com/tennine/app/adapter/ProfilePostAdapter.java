package com.tennine.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tennine.app.R;
import com.tennine.app.databinding.ItemHomeLayoutBinding;
import com.tennine.app.databinding.ItemProfilePostBinding;
import com.tennine.app.listener.OnProfilePostListener;
import com.tennine.app.model.PostModel;

import java.util.List;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ViewHolder> {

    private List<PostModel> postModelList;
    private OnProfilePostListener listener;

    public ProfilePostAdapter(List<PostModel> postModelList,OnProfilePostListener listener) {
        this.postModelList = postModelList;
        this.listener= listener;
    }

    @NonNull
    @Override
    public ProfilePostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemProfilePostBinding itemProfilePostBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_profile_post, parent, false);
        return new ViewHolder(itemProfilePostBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePostAdapter.ViewHolder holder, int position) {
        holder.itemProfilePostBinding.setModel(postModelList.get(position));
        holder.itemProfilePostBinding.executePendingBindings();
        holder.itemProfilePostBinding.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProfilePostBinding itemProfilePostBinding;
        public ViewHolder(@NonNull ItemProfilePostBinding itemProfilePostBinding) {
            super(itemProfilePostBinding.getRoot());
            this.itemProfilePostBinding = itemProfilePostBinding;
        }
    }
}
