package com.tennine.app.adapter;

import static com.tennine.app.utils.Util.showToast;

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.tennine.app.R;
import com.tennine.app.model.PostModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BindingAdapters {

    @BindingAdapter("android:setImageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url == null) {
            imageView.setImageResource(R.drawable.user);
        } else if (url != null) {
            byte[] bytes = Base64.decode(url, Base64.DEFAULT);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        }
    }


    @BindingAdapter("android:setPostImage")
    public static void setPostImage(ImageView imageView, String url) {
        if (url == null) {
            imageView.setImageResource(R.drawable.user);
        } else if (url != null) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }

    @BindingAdapter("android:setOnRatingChange")
    public static void setOnRatingChange(RatingBar ratingBar, PostModel model) {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
    }

    @BindingAdapter("android:setPostTime")
    public static void setPostTime(TextView textView, Long url) {
        int day = 0;
        int hh = 0;
        int mm = 0;
        int ss = 0;
        String time;
        Date oldDate = new Date(url);

        Date cDate = new Date();
        Long timeDiff = cDate.getTime() - oldDate.getTime();
        day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
        hh = (int) (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day));
        mm = (int) (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        ss = (int) (TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.SECONDS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));

        if (day == 0 && hh == 0 && mm == 0) {
            time = ss + " seconds ago";
        } else if (day == 0 && hh == 0) {
            time = mm + " minutes ago";
        } else if (day == 0) {
            time = hh + " hour ago";
        } else {
            time = day + " days ago";
        }

        textView.setText(time);
    }


    @BindingAdapter("android:setbtnRemoveText")
    public static void setbtnRemoveText(Button button, Boolean isConnected) {
        if (isConnected) {
            button.setText("Connected");
        } else {
            button.setText("Connect");
        }
    }


    @BindingAdapter("android:setLikeBtn")
    public static void setLikeBtn(ImageView view, Boolean isConnected) {
        if (isConnected) {
            view.setImageTintList(view.getContext().getResources().getColorStateList(R.color.red));
        } else {
            view.setImageTintList(view.getContext().getResources().getColorStateList(R.color.black));
        }
    }

    @BindingAdapter("android:setbtnILikeText")
    public static void setbtnILikeText(Button button, Boolean isConnected) {
        if (isConnected) {
            button.setText("I LIKES");
        } else {
            button.setText("I LIKE");
        }
    }


}
