package com.tennine.app.adapter;

import static com.tennine.app.utils.Util.prettyCount;
import static com.tennine.app.utils.Util.showToast;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tennine.app.R;
import com.tennine.app.activity.ui.CommentsActivity;
import com.tennine.app.activity.ui.FriendProfile;
import com.tennine.app.activity.ui.MainActivity;
import com.tennine.app.activity.ui.UserProfile;
import com.tennine.app.model.NotificationModel;
import com.tennine.app.model.ReelsModel;
import com.tennine.app.utils.PreferenceManager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReelsAdapter extends RecyclerView.Adapter<ReelsAdapter.ReelsViewHolder> {
    private List<ReelsModel> reelsModelList;
    private Activity context;
    private FirebaseFirestore firebaseFirestore;
    private PreferenceManager preferenceManager;

    public ReelsAdapter(List<ReelsModel> reelsModelList, Activity context) {
        this.reelsModelList = reelsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReelsViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_video,
                parent,
                false
        )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ReelsViewHolder holder, int position) {
        holder.setData(reelsModelList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return reelsModelList.size();
    }

    public class ReelsViewHolder extends RecyclerView.ViewHolder {

        private VideoView videoView;
        private TextView tvUserName, tvLikes, tvComments, tvShare;
        private ImageView ivUser, ivLikes, ivComment, ivShares, ivEmoji;
        private ProgressBar pbReels;
        private RatingBar mRatingbar;


        public ReelsViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceManager = new PreferenceManager(context);
            firebaseFirestore = FirebaseFirestore.getInstance();
            videoView = itemView.findViewById(R.id.videoView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvComments = itemView.findViewById(R.id.tvComments);
            tvShare = itemView.findViewById(R.id.tvShares);
            ivUser = itemView.findViewById(R.id.ivProfile);
            pbReels = itemView.findViewById(R.id.pbReels);
            ivComment = itemView.findViewById(R.id.ivComments);
            ivLikes = itemView.findViewById(R.id.ivLikes);
            ivShares = itemView.findViewById(R.id.ivShares);
            mRatingbar = itemView.findViewById(R.id.ratingBar);
            ivEmoji = itemView.findViewById(R.id.ivEmoji);

        }

        void setData(ReelsModel reelsModel, int position) {
            videoView.setVideoPath(reelsModel.getVideoUrl());
            tvUserName.setText(reelsModel.getUserName());
            tvLikes.setText(prettyCount(reelsModel.getLikes()));
            tvComments.setText(prettyCount(reelsModel.getComments()));
            tvShare.setText(prettyCount(reelsModel.getShares()));

            if (reelsModel.getUserImage() == null) {
                ivUser.setImageResource(R.drawable.user);
            } else if (reelsModel.getUserImage() != null) {
                byte[] bytes = Base64.decode(reelsModel.getUserImage(), Base64.DEFAULT);
                ivUser.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }

            if (reelsModel.isPresentUserLike()) {
                ivLikes.setImageTintList(context.getResources().getColorStateList(R.color.red));
            }

            tvLikes.setText(reelsModel.getLikes() + "");

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    pbReels.setVisibility(View.GONE);

                    float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                    float screenRatio = videoView.getWidth() / (float) videoView.getHeight();
                    float scale = videoRatio / screenRatio;

                    if (scale >= 1f) {
                        videoView.setScaleX(scale);
                    } else {
                        videoView.setScaleX(1f / scale);
                    }
                    mp.start();
                    firebaseFirestore.collection("REELS")
                            .document(reelsModel.getVideoId())
                            .update("videoPlay", (reelsModel.getVideoPlay() + 1))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    showToast("uploaded", context);
                                    firebaseFirestore.collection("users")
                                            .document(reelsModel.getUserId())
                                            .collection("REELS")
                                            .document(reelsModel.getVideoId())
                                            .update("videoPlay", reelsModel.getVideoPlay() + 1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            });
                                }
                            });


                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            });

            if (reelsModel.isRating()) {
                mRatingbar.setRating(reelsModel.getNumRating());
                float rating = reelsModel.getNumRating();
                if (rating <= 1) {
                    ivEmoji.setVisibility(View.VISIBLE);
                    ivEmoji.setImageResource(R.drawable.one_star);
                    uploadRating(reelsModel.getVideoId(), rating);
                } else if (rating <= 2) {
                    ivEmoji.setVisibility(View.VISIBLE);
                    ivEmoji.setImageResource(R.drawable.two_star);
                    uploadRating(reelsModel.getVideoId(), rating);
                } else if (rating <= 3) {
                    ivEmoji.setVisibility(View.VISIBLE);
                    ivEmoji.setImageResource(R.drawable.three_star);
                    uploadRating(reelsModel.getVideoId(), rating);
                } else if (rating <= 4) {
                    ivEmoji.setVisibility(View.VISIBLE);
                    ivEmoji.setImageResource(R.drawable.four_star);
                    uploadRating(reelsModel.getVideoId(), rating);
                } else {
                    ivEmoji.setVisibility(View.VISIBLE);
                    ivEmoji.setImageResource(R.drawable.five_star);
                    uploadRating(reelsModel.getVideoId(), rating);
                }

                animateImage(ivEmoji);
            }

            ivUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (reelsModel.getUserId().equals(preferenceManager.getString("id"))) {
                        context.startActivity(new Intent(context, UserProfile.class));
                    } else {
                        context.startActivity(new Intent(context, FriendProfile.class)
                                .putExtra("id", reelsModel.getUserId()));
                    }
                }
            });

            tvUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (reelsModel.getUserId().equals(preferenceManager.getString("id"))) {
                        context.startActivity(new Intent(context, UserProfile.class));
                    } else {
                        context.startActivity(new Intent(context, FriendProfile.class)
                                .putExtra("id", reelsModel.getUserId()));
                    }
                }
            });

            ivLikes.setOnClickListener(v -> {

                if (reelsModel.isPresentUserLike()) {
                    firebaseFirestore.collection("REELS")
                            .document(reelsModel.getVideoId())
                            .collection("LIKES")
                            .document(preferenceManager.getString("id"))
                            .delete()
                            .addOnSuccessListener(unused ->
                                    firebaseFirestore.collection("REELS")
                                            .document(reelsModel.getVideoId())
                                            .update("likes", reelsModel.getLikes() - 1)
                                            .addOnSuccessListener(unused1 -> {
                                                ivLikes.setImageTintList(context.getResources().getColorStateList(R.color.white));
                                                tvLikes.setText(reelsModel.getLikes() - 1 + "");
                                                reelsModelList.get(position)
                                                        .setPresentUserLike(false);
                                                reelsModelList.get(position)
                                                        .setLikes(reelsModel.getLikes() - 1);


                                                NotificationModel notificationModel = new NotificationModel();
                                                notificationModel.setNotificationAt(new Date().getTime());
                                                notificationModel.setPostedBy(preferenceManager.getString("id"));
                                                notificationModel.setPostImage(reelsModel.getVideoUrl());
                                                notificationModel.setType("like");
                                                notificationModel.setPostId(reelsModel.getVideoId());
                                                notificationModel.setPostType("POST");
                                                notificationModel.setCheckOpen(false);
                                                notificationModel.setDescription("like your REEL");
                                                notificationModel.setNotificationBy(preferenceManager.getString("id"));

                                                if (reelsModel.getUserId().equals(preferenceManager.getString("id"))) {

                                                } else {
                                                    firebaseFirestore.collection("NOTIFICATION")
                                                            .document(reelsModel.getUserId())
                                                            .collection("NOTIFICATIONS")
                                                            .document()
                                                            .set(notificationModel)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    firebaseFirestore.collection("users")
                                                                            .document(reelsModel.getUserId())
                                                                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                                        }
                                                                    });
                                                                }
                                                            });

                                                }


                                            }));

                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("like", true);
                    firebaseFirestore.collection("REELS")
                            .document(reelsModel.getVideoId())
                            .collection("LIKES")
                            .document(preferenceManager.getString("id"))
                            .set(map)
                            .addOnSuccessListener(unused -> {
                                ivLikes.setImageTintList(context.getResources().getColorStateList(R.color.red));
                                tvLikes.setText(reelsModel.getLikes() + 1 + "");
                                firebaseFirestore.collection("REELS")
                                        .document(reelsModel.getVideoId())
                                        .update("likes", reelsModel.getLikes() + 1)
                                        .addOnSuccessListener(unused12 -> {
                                            reelsModelList.get(position)
                                                    .setPresentUserLike(true);
                                            reelsModelList.get(position)
                                                    .setLikes(reelsModel.getLikes() + 1);
                                        });
                            });
                }


            });


            mRatingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (rating <= 1) {
                        ivEmoji.setVisibility(View.VISIBLE);
                        ivEmoji.setImageResource(R.drawable.one_star);
                        uploadRating(reelsModel.getVideoId(), rating);
                    } else if (rating <= 2) {
                        ivEmoji.setVisibility(View.VISIBLE);
                        ivEmoji.setImageResource(R.drawable.two_star);
                        uploadRating(reelsModel.getVideoId(), rating);
                    } else if (rating <= 3) {
                        ivEmoji.setVisibility(View.VISIBLE);
                        ivEmoji.setImageResource(R.drawable.three_star);
                        uploadRating(reelsModel.getVideoId(), rating);
                    } else if (rating <= 4) {
                        ivEmoji.setVisibility(View.VISIBLE);
                        ivEmoji.setImageResource(R.drawable.four_star);
                        uploadRating(reelsModel.getVideoId(), rating);
                    } else {
                        ivEmoji.setVisibility(View.VISIBLE);
                        ivEmoji.setImageResource(R.drawable.five_star);
                        uploadRating(reelsModel.getVideoId(), rating);
                    }

                    animateImage(ivEmoji);
                }
            });
            ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentsActivity.class);
                    intent.putExtra("type", "REELS");
                    intent.putExtra("postId", reelsModel.getVideoId());
                    intent.putExtra("userName", reelsModel.getUserName());
                    intent.putExtra("userImage", reelsModel.getUserImage());
                    context.startActivity(intent);
                }
            });

        }

        public void animateImage(ImageView iv) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(200);
            ivEmoji.startAnimation(scaleAnimation);
        }

        public void uploadRating(String reelsId, float rating) {
            Map<String, Object> map = new HashMap<>();
            map.put("rating", rating);
            firebaseFirestore.collection("REELS")
                    .document(reelsId)
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
