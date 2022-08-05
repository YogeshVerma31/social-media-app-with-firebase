// Generated by data binding compiler. Do not edit!
package com.tennine.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.tennine.app.R;
import com.tennine.app.listener.OnFriendsClickListener;
import com.tennine.app.model.UserModel;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemFriendsBinding extends ViewDataBinding {
  @NonNull
  public final Button btnRemoveFollowing;

  @NonNull
  public final CircleImageView ivProfile;

  @NonNull
  public final TextView tvNickNameFollowing;

  @NonNull
  public final TextView tvUsername;

  @Bindable
  protected UserModel mModel;

  @Bindable
  protected OnFriendsClickListener mItemCLickListener;

  @Bindable
  protected int mPosition;

  protected ItemFriendsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button btnRemoveFollowing, CircleImageView ivProfile, TextView tvNickNameFollowing,
      TextView tvUsername) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnRemoveFollowing = btnRemoveFollowing;
    this.ivProfile = ivProfile;
    this.tvNickNameFollowing = tvNickNameFollowing;
    this.tvUsername = tvUsername;
  }

  public abstract void setModel(@Nullable UserModel model);

  @Nullable
  public UserModel getModel() {
    return mModel;
  }

  public abstract void setItemCLickListener(@Nullable OnFriendsClickListener itemCLickListener);

  @Nullable
  public OnFriendsClickListener getItemCLickListener() {
    return mItemCLickListener;
  }

  public abstract void setPosition(int position);

  public int getPosition() {
    return mPosition;
  }

  @NonNull
  public static ItemFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_friends, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemFriendsBinding>inflateInternal(inflater, R.layout.item_friends, root, attachToRoot, component);
  }

  @NonNull
  public static ItemFriendsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_friends, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemFriendsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemFriendsBinding>inflateInternal(inflater, R.layout.item_friends, null, false, component);
  }

  public static ItemFriendsBinding bind(@NonNull View view) {
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
  public static ItemFriendsBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemFriendsBinding)bind(component, view, R.layout.item_friends);
  }
}