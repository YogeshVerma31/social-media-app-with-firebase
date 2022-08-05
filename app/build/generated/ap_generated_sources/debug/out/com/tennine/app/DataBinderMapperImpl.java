package com.tennine.app;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.tennine.app.databinding.ItemCommentsBindingImpl;
import com.tennine.app.databinding.ItemFollowerBindingImpl;
import com.tennine.app.databinding.ItemFollowingBindingImpl;
import com.tennine.app.databinding.ItemFriendsBindingImpl;
import com.tennine.app.databinding.ItemFriendsFollowersBindingImpl;
import com.tennine.app.databinding.ItemHomeLayoutBindingImpl;
import com.tennine.app.databinding.ItemNotificationBindingImpl;
import com.tennine.app.databinding.ItemPostReelsBindingImpl;
import com.tennine.app.databinding.ItemProfilePostBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ITEMCOMMENTS = 1;

  private static final int LAYOUT_ITEMFOLLOWER = 2;

  private static final int LAYOUT_ITEMFOLLOWING = 3;

  private static final int LAYOUT_ITEMFRIENDS = 4;

  private static final int LAYOUT_ITEMFRIENDSFOLLOWERS = 5;

  private static final int LAYOUT_ITEMHOMELAYOUT = 6;

  private static final int LAYOUT_ITEMNOTIFICATION = 7;

  private static final int LAYOUT_ITEMPOSTREELS = 8;

  private static final int LAYOUT_ITEMPROFILEPOST = 9;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(9);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tennine.app.R.layout.item_comments, LAYOUT_ITEMCOMMENTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tennine.app.R.layout.item_follower, LAYOUT_ITEMFOLLOWER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tennine.app.R.layout.item_following, LAYOUT_ITEMFOLLOWING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tennine.app.R.layout.item_friends, LAYOUT_ITEMFRIENDS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tennine.app.R.layout.item_friends_followers, LAYOUT_ITEMFRIENDSFOLLOWERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tennine.app.R.layout.item_home_layout, LAYOUT_ITEMHOMELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tennine.app.R.layout.item_notification, LAYOUT_ITEMNOTIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tennine.app.R.layout.item_post_reels, LAYOUT_ITEMPOSTREELS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tennine.app.R.layout.item_profile_post, LAYOUT_ITEMPROFILEPOST);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ITEMCOMMENTS: {
          if ("layout/item_comments_0".equals(tag)) {
            return new ItemCommentsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_comments is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMFOLLOWER: {
          if ("layout/item_follower_0".equals(tag)) {
            return new ItemFollowerBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_follower is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMFOLLOWING: {
          if ("layout/item_following_0".equals(tag)) {
            return new ItemFollowingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_following is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMFRIENDS: {
          if ("layout/item_friends_0".equals(tag)) {
            return new ItemFriendsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_friends is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMFRIENDSFOLLOWERS: {
          if ("layout/item_friends_followers_0".equals(tag)) {
            return new ItemFriendsFollowersBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_friends_followers is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMHOMELAYOUT: {
          if ("layout/item_home_layout_0".equals(tag)) {
            return new ItemHomeLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_home_layout is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMNOTIFICATION: {
          if ("layout/item_notification_0".equals(tag)) {
            return new ItemNotificationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_notification is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMPOSTREELS: {
          if ("layout/item_post_reels_0".equals(tag)) {
            return new ItemPostReelsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_post_reels is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMPROFILEPOST: {
          if ("layout/item_profile_post_0".equals(tag)) {
            return new ItemProfilePostBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_profile_post is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(7);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "itemCLickListener");
      sKeys.put(2, "itemlistener");
      sKeys.put(3, "listener");
      sKeys.put(4, "model");
      sKeys.put(5, "onCLickListener");
      sKeys.put(6, "position");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(9);

    static {
      sKeys.put("layout/item_comments_0", com.tennine.app.R.layout.item_comments);
      sKeys.put("layout/item_follower_0", com.tennine.app.R.layout.item_follower);
      sKeys.put("layout/item_following_0", com.tennine.app.R.layout.item_following);
      sKeys.put("layout/item_friends_0", com.tennine.app.R.layout.item_friends);
      sKeys.put("layout/item_friends_followers_0", com.tennine.app.R.layout.item_friends_followers);
      sKeys.put("layout/item_home_layout_0", com.tennine.app.R.layout.item_home_layout);
      sKeys.put("layout/item_notification_0", com.tennine.app.R.layout.item_notification);
      sKeys.put("layout/item_post_reels_0", com.tennine.app.R.layout.item_post_reels);
      sKeys.put("layout/item_profile_post_0", com.tennine.app.R.layout.item_profile_post);
    }
  }
}
