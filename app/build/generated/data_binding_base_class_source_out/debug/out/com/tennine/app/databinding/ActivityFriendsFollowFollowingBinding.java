// Generated by view binder compiler. Do not edit!
package com.tennine.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.tennine.app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityFriendsFollowFollowingBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final AppCompatImageView ivBack;

  @NonNull
  public final TabLayout tlFollowingFollowers;

  @NonNull
  public final ViewPager vpFollowingFollowers;

  private ActivityFriendsFollowFollowingBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull AppCompatImageView ivBack, @NonNull TabLayout tlFollowingFollowers,
      @NonNull ViewPager vpFollowingFollowers) {
    this.rootView = rootView;
    this.ivBack = ivBack;
    this.tlFollowingFollowers = tlFollowingFollowers;
    this.vpFollowingFollowers = vpFollowingFollowers;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityFriendsFollowFollowingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityFriendsFollowFollowingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_friends_follow_following, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityFriendsFollowFollowingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ivBack;
      AppCompatImageView ivBack = ViewBindings.findChildViewById(rootView, id);
      if (ivBack == null) {
        break missingId;
      }

      id = R.id.tlFollowingFollowers;
      TabLayout tlFollowingFollowers = ViewBindings.findChildViewById(rootView, id);
      if (tlFollowingFollowers == null) {
        break missingId;
      }

      id = R.id.vpFollowingFollowers;
      ViewPager vpFollowingFollowers = ViewBindings.findChildViewById(rootView, id);
      if (vpFollowingFollowers == null) {
        break missingId;
      }

      return new ActivityFriendsFollowFollowingBinding((LinearLayoutCompat) rootView, ivBack,
          tlFollowingFollowers, vpFollowingFollowers);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
