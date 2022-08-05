package com.tennine.app.activity.ui;

import static com.tennine.app.utils.Util.showToast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.tennine.app.R;
import com.tennine.app.adapter.ViewPagerFollowingFollowersAdapter;
import com.tennine.app.adapter.ViewPagerFriendsFollow;
import com.tennine.app.databinding.ActivityFollowingFollowersBinding;
import com.tennine.app.databinding.ActivityFriendsFollowFollowingBinding;

import java.util.Objects;

public class FriendsFollowFollowingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityFriendsFollowFollowingBinding mBinding;
    private ViewPagerFriendsFollow ViewPagerFriendsFollow;
    private String FirstFragmentName,SecondFragmentName;
    private int tabPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFriendsFollowFollowingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getIntentData();
        setAdapter();
        setListeners();
    }

    private void setListeners() {
        mBinding.ivBack.setOnClickListener(this);
    }

    private void getIntentData() {
        FirstFragmentName = getIntent().getStringExtra("firstFragment");
        SecondFragmentName = getIntent().getStringExtra("secondFragment");
        tabPosition = getIntent().getIntExtra("tabPosition",-1);
    }

    private void setAdapter() {
        ViewPagerFriendsFollow = new ViewPagerFriendsFollow(getSupportFragmentManager(),FirstFragmentName,SecondFragmentName);
        mBinding.vpFollowingFollowers.setAdapter(ViewPagerFriendsFollow);
        mBinding.tlFollowingFollowers.setupWithViewPager(mBinding.vpFollowingFollowers);
        mBinding.vpFollowingFollowers.setCurrentItem(tabPosition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }
}