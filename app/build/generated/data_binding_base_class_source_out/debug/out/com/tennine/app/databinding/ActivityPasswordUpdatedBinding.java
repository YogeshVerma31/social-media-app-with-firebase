// Generated by view binder compiler. Do not edit!
package com.tennine.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.tennine.app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPasswordUpdatedBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button button6;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final TextView textView33;

  @NonNull
  public final TextView textView34;

  @NonNull
  public final TextView textView35;

  private ActivityPasswordUpdatedBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button button6, @NonNull ImageView imageView, @NonNull TextView textView33,
      @NonNull TextView textView34, @NonNull TextView textView35) {
    this.rootView = rootView;
    this.button6 = button6;
    this.imageView = imageView;
    this.textView33 = textView33;
    this.textView34 = textView34;
    this.textView35 = textView35;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPasswordUpdatedBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPasswordUpdatedBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_password_updated, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPasswordUpdatedBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button6;
      Button button6 = ViewBindings.findChildViewById(rootView, id);
      if (button6 == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.textView33;
      TextView textView33 = ViewBindings.findChildViewById(rootView, id);
      if (textView33 == null) {
        break missingId;
      }

      id = R.id.textView34;
      TextView textView34 = ViewBindings.findChildViewById(rootView, id);
      if (textView34 == null) {
        break missingId;
      }

      id = R.id.textView35;
      TextView textView35 = ViewBindings.findChildViewById(rootView, id);
      if (textView35 == null) {
        break missingId;
      }

      return new ActivityPasswordUpdatedBinding((ConstraintLayout) rootView, button6, imageView,
          textView33, textView34, textView35);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}