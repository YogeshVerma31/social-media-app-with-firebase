// Generated by view binder compiler. Do not edit!
package com.tennine.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public final class NotificationtabLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView30;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final TextView textView76;

  @NonNull
  public final TextView textView77;

  @NonNull
  public final TextView textView78;

  @NonNull
  public final TextView textView79;

  @NonNull
  public final TextView textView80;

  @NonNull
  public final View view3;

  private NotificationtabLayoutBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView imageView30, @NonNull LinearLayout linearLayout,
      @NonNull TextView textView76, @NonNull TextView textView77, @NonNull TextView textView78,
      @NonNull TextView textView79, @NonNull TextView textView80, @NonNull View view3) {
    this.rootView = rootView;
    this.imageView30 = imageView30;
    this.linearLayout = linearLayout;
    this.textView76 = textView76;
    this.textView77 = textView77;
    this.textView78 = textView78;
    this.textView79 = textView79;
    this.textView80 = textView80;
    this.view3 = view3;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NotificationtabLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NotificationtabLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.notificationtab_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NotificationtabLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView30;
      ImageView imageView30 = ViewBindings.findChildViewById(rootView, id);
      if (imageView30 == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.textView76;
      TextView textView76 = ViewBindings.findChildViewById(rootView, id);
      if (textView76 == null) {
        break missingId;
      }

      id = R.id.textView77;
      TextView textView77 = ViewBindings.findChildViewById(rootView, id);
      if (textView77 == null) {
        break missingId;
      }

      id = R.id.textView78;
      TextView textView78 = ViewBindings.findChildViewById(rootView, id);
      if (textView78 == null) {
        break missingId;
      }

      id = R.id.textView79;
      TextView textView79 = ViewBindings.findChildViewById(rootView, id);
      if (textView79 == null) {
        break missingId;
      }

      id = R.id.textView80;
      TextView textView80 = ViewBindings.findChildViewById(rootView, id);
      if (textView80 == null) {
        break missingId;
      }

      id = R.id.view3;
      View view3 = ViewBindings.findChildViewById(rootView, id);
      if (view3 == null) {
        break missingId;
      }

      return new NotificationtabLayoutBinding((ConstraintLayout) rootView, imageView30,
          linearLayout, textView76, textView77, textView78, textView79, textView80, view3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
