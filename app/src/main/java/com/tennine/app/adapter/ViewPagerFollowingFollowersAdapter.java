package com.tennine.app.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tennine.app.activity.fragment.FollowersFragment;
import com.tennine.app.activity.fragment.FollowingFragment;

public class ViewPagerFollowingFollowersAdapter extends FragmentPagerAdapter {
    private String fragmentOneName,fragmentTwoName;

    public ViewPagerFollowingFollowersAdapter(@NonNull FragmentManager fm,String fragmentOneName,String fragmentTwoName) {
        super(fm);
        this.fragmentOneName = fragmentOneName;
        this.fragmentTwoName = fragmentTwoName;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FollowersFragment();
        } else {
            return new FollowingFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position ==0){
            return fragmentOneName;
        }else{
            return fragmentTwoName;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
