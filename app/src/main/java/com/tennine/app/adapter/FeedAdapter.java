package com.tennine.app.adapter;

import static com.tennine.app.utils.Util.showToast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tennine.app.R;
import com.tennine.app.databinding.ItemHomeLayoutBinding;
import com.tennine.app.listener.OnFeedClickListener;
import com.tennine.app.model.PostModel;
import com.tennine.app.utils.PreferenceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private List<PostModel> postModelList;
    private OnFeedClickListener onFeedClickListener;
    private Context context;

    public FeedAdapter(List<PostModel> postModelList,OnFeedClickListener onFeedClickListener,Context context) {
        this.postModelList = postModelList;
        this.onFeedClickListener = onFeedClickListener;
        this.context = context;

    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemHomeLayoutBinding itemHomeLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_home_layout, parent, false);
        return new FeedAdapter.ViewHolder(itemHomeLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        holder.itemHomeLayoutBinding.setModel(postModelList.get(position));
        holder.itemHomeLayoutBinding.setItemlistener(onFeedClickListener);
        holder.itemHomeLayoutBinding.setPosition(position);
        holder.setData(postModelList.get(position),position);
        holder.itemHomeLayoutBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHomeLayoutBinding itemHomeLayoutBinding;
        private FirebaseFirestore firebaseFirestore;
        private PreferenceManager preferenceManager;

        public ViewHolder(ItemHomeLayoutBinding itemHomeLayoutBinding) {
            super(itemHomeLayoutBinding.getRoot());
            this.itemHomeLayoutBinding = itemHomeLayoutBinding;
            firebaseFirestore = FirebaseFirestore.getInstance();
            preferenceManager = new PreferenceManager(context);
        }


        public void setData(PostModel model,int position){
            itemHomeLayoutBinding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (rating <= 1) {
                        itemHomeLayoutBinding.ivEmoji.setVisibility(View.VISIBLE);
                        itemHomeLayoutBinding.ivEmoji.setImageResource(R.drawable.one_star);
                        uploadRating(model, rating);
                    } else if (rating <= 2) {
                        itemHomeLayoutBinding.ivEmoji.setVisibility(View.VISIBLE);
                        itemHomeLayoutBinding.ivEmoji.setImageResource(R.drawable.two_star);
                        uploadRating(model, rating);
                    } else if (rating <= 3) {
                       itemHomeLayoutBinding.ivEmoji.setVisibility(View.VISIBLE);
                       itemHomeLayoutBinding.ivEmoji.setImageResource(R.drawable.three_star);
                        uploadRating(model, rating);
                    } else if (rating <= 4) {
                        itemHomeLayoutBinding.ivEmoji.setVisibility(View.VISIBLE);
                        itemHomeLayoutBinding.ivEmoji.setImageResource(R.drawable.four_star);
                        uploadRating(model, rating);
                    } else {
                        itemHomeLayoutBinding.ivEmoji.setVisibility(View.VISIBLE);
                        itemHomeLayoutBinding.ivEmoji.setImageResource(R.drawable.five_star);
                        uploadRating(model, rating);
                    }

                    animateImage();
                }
            });


        }
        public void animateImage() {
            ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(200);
            itemHomeLayoutBinding.ivEmoji.startAnimation(scaleAnimation);
        }


        public void uploadRating(PostModel model, float rating) {
            Map<String, Object> map = new HashMap<>();
            map.put("rating", rating);
            firebaseFirestore.collection("posts")
                    .document(model.getPostedBy())
                    .collection("POSTS")
                    .document(model.getPostId())
                    .collection("RATINGS")
                    .document(preferenceManager.getString("id"))
                    .set(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
        }
    }

}