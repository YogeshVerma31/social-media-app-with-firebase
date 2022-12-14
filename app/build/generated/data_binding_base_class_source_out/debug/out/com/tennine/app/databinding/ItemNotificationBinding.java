// Generated by data binding compiler. Do not edit!
package com.tennine.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.tennine.app.R;
import com.tennine.app.listener.NotificationListener;
import com.tennine.app.model.NotificationModel;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemNotificationBinding extends ViewDataBinding {
  @NonNull
  public final AppCompatTextView appCompatTextView;

  @NonNull
  public final AppCompatImageView ivPost;

  @NonNull
  public final CircleImageView ivProfile;

  @NonNull
  public final AppCompatTextView tvUserName;

  @Bindable
  protected NotificationModel mModel;

  @Bindable
  protected NotificationListener mListener;

  protected ItemNotificationBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppCompatTextView appCompatTextView, AppCompatImageView ivPost, CircleImageView ivProfile,
      AppCompatTextView tvUserName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appCompatTextView = appCompatTextView;
    this.ivPost = ivPost;
    this.ivProfile = ivProfile;
    this.tvUserName = tvUserName;
  }

  public abstract void setModel(@Nullable NotificationModel model);

  @Nullable
  public NotificationModel getModel() {
    return mModel;
  }

  public abstract void setListener(@Nullable NotificationListener listener);

  @Nullable
  public NotificationListener getListener() {
    return mListener;
  }

  @NonNull
  public static ItemNotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_notification, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemNotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemNotificationBinding>inflateInternal(inflater, R.layout.item_notification, root, attachToRoot, component);
  }

  @NonNull
  public static ItemNotificationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_notification, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemNotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemNotificationBinding>inflateInternal(inflater, R.layout.item_notification, null, false, component);
  }

  public static ItemNotificationBinding bind(@NonNull View view) {
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
  public static ItemNotificationBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemNotificationBinding)bind(component, view, R.layout.item_notification);
  }
}
