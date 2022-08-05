// Generated by view binder compiler. Do not edit!
package com.tennine.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.tennine.app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentFriendsFollowingBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText edtSearchFollowing;

  @NonNull
  public final ProgressBar pbFollowing;

  @NonNull
  public final RecyclerView rvFollowing;

  @NonNull
  public final TextView tvNoData;

  private FragmentFriendsFollowingBinding(@NonNull ConstraintLayout rootView,
      @NonNull EditText edtSearchFollowing, @NonNull ProgressBar pbFollowing,
      @NonNull RecyclerView rvFollowing, @NonNull TextView tvNoData) {
    this.rootView = rootView;
    this.edtSearchFollowing = edtSearchFollowing;
    this.pbFollowing = pbFollowing;
    this.rvFollowing = rvFollowing;
    this.tvNoData = tvNoData;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentFriendsFollowingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentFriendsFollowingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_friends_following, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentFriendsFollowingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.edtSearchFollowing;
      EditText edtSearchFollowing = ViewBindings.findChildViewById(rootView, id);
      if (edtSearchFollowing == null) {
        break missingId;
      }

      id = R.id.pbFollowing;
      ProgressBar pbFollowing = ViewBindings.findChildViewById(rootView, id);
      if (pbFollowing == null) {
        break missingId;
      }

      id = R.id.rvFollowing;
      RecyclerView rvFollowing = ViewBindings.findChildViewById(rootView, id);
      if (rvFollowing == null) {
        break missingId;
      }

      id = R.id.tv_no_data;
      TextView tvNoData = ViewBindings.findChildViewById(rootView, id);
      if (tvNoData == null) {
        break missingId;
      }

      return new FragmentFriendsFollowingBinding((ConstraintLayout) rootView, edtSearchFollowing,
          pbFollowing, rvFollowing, tvNoData);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}