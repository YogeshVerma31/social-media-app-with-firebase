// Generated by data binding compiler. Do not edit!
package com.tennine.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.tennine.app.R;
import com.tennine.app.listener.OnFeedClickListener;
import com.tennine.app.model.PostModel;
import com.tennine.app.utils.SquareImageView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemHomeLayoutBinding extends ViewDataBinding {
  @NonNull
  public final TextView btnAbout;

  @NonNull
  public final ImageView imageView19;

  @NonNull
  public final ImageView imageView20;

  @NonNull
  public final AppCompatImageView ivEmoji;

  @NonNull
  public final SquareImageView ivPost;

  @NonNull
  public final CircleImageView ivProfile;

  @NonNull
  public final LinearLayoutCompat linearLayoutCompat;

  @NonNull
  public final LinearLayoutCompat linearLayoutCompat2;

  @NonNull
  public final RatingBar ratingBar;

  @NonNull
  public final TextView textView10;

  @NonNull
  public final TextView textView39;

  @NonNull
  public final TextView textView45;

  @NonNull
  public final TextView textView46;

  @NonNull
  public final TextView textView8;

  @NonNull
  public final TextView tvUserName;

  @Bindable
  protected PostModel mModel;

  @Bindable
  protected OnFeedClickListener mItemlistener;

  @Bindable
  protected int mPosition;

  protected ItemHomeLayoutBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView btnAbout, ImageView imageView19, ImageView imageView20, AppCompatImageView ivEmoji,
      SquareImageView ivPost, CircleImageView ivProfile, LinearLayoutCompat linearLayoutCompat,
      LinearLayoutCompat linearLayoutCompat2, RatingBar ratingBar, TextView textView10,
      TextView textView39, TextView textView45, TextView textView46, TextView textView8,
      TextView tvUserName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnAbout = btnAbout;
    this.imageView19 = imageView19;
    this.imageView20 = imageView20;
    this.ivEmoji = ivEmoji;
    this.ivPost = ivPost;
    this.ivProfile = ivProfile;
    this.linearLayoutCompat = linearLayoutCompat;
    this.linearLayoutCompat2 = linearLayoutCompat2;
    this.ratingBar = ratingBar;
    this.textView10 = textView10;
    this.textView39 = textView39;
    this.textView45 = textView45;
    this.textView46 = textView46;
    this.textView8 = textView8;
    this.tvUserName = tvUserName;
  }

  public abstract void setModel(@Nullable PostModel model);

  @Nullable
  public PostModel getModel() {
    return mModel;
  }

  public abstract void setItemlistener(@Nullable OnFeedClickListener itemlistener);

  @Nullable
  public OnFeedClickListener getItemlistener() {
    return mItemlistener;
  }

  public abstract void setPosition(int position);

  public int getPosition() {
    return mPosition;
  }

  @NonNull
  public static ItemHomeLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_home_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemHomeLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemHomeLayoutBinding>inflateInternal(inflater, R.layout.item_home_layout, root, attachToRoot, component);
  }

  @NonNull
  public static ItemHomeLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_home_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemHomeLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemHomeLayoutBinding>inflateInternal(inflater, R.layout.item_home_layout, null, false, component);
  }

  public static ItemHomeLayoutBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ItemHomeLayoutBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemHomeLayoutBinding)bind(component, view, R.layout.item_home_layout);
  }
}
