package com.tennine.app.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tennine.app.R;
import com.tennine.app.databinding.ItemNotificationBinding;
import com.tennine.app.listener.NotificationListener;
import com.tennine.app.model.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationModel> notificationModelList;
    private NotificationListener listener;

    public NotificationAdapter(List<NotificationModel> notificationModelList,NotificationListener listener) {
        this.notificationModelList = notificationModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNotificationBinding itemNotificationBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_notification, parent, false);
        return new ViewHolder(itemNotificationBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.itemNotificationBinding.setModel(notificationModelList.get(position));
        holder.itemNotificationBinding.setListener(listener);
        holder.itemNotificationBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNotificationBinding itemNotificationBinding;

        public ViewHolder(@NonNull ItemNotificationBinding itemNotificationBinding) {
            super(itemNotificationBinding.getRoot());
            this.itemNotificationBinding = itemNotificationBinding;
        }
    }
}
