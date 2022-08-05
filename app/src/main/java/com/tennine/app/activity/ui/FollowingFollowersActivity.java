package com.tennine.app.activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.tennine.app.R;
import com.tennine.app.adapter.ViewPagerFollowingFollowersAdapter;
import com.tennine.app.databinding.ActivityFollowingFollowersBinding;

import java.util.Objects;

public class FollowingFollowersActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityFollowingFollowersBinding activityFollowingFollowersBinding;
    private ViewPagerFollowingFollowersAdapter viewPagerFollowingFollowersAdapter;
    private String FirstFragmentName,SecondFragmentName;
    private int tabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFollowingFollowersBinding = ActivityFollowingFollowersBinding.inflate(getLayoutInflater());
        setContentView(activityFollowingFollowersBinding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getIntentData();
        setAdapter();
        setListeners();
    }

    private void setListeners() {
        activityFollowingFollowersBinding.ivBack.setOnClickListener(this);
    }

    private void getIntentData() {
        FirstFragmentName = getIntent().getStringExtra("firstFragment");
        SecondFragmentName = getIntent().getStringExtra("secondFragment");
        tabPosition = getIntent().getIntExtra("tabPosition",-1);
    }

    private void setAdapter() {
        viewPagerFollowingFollowersAdapter = new ViewPagerFollowingFollowersAdapter(getSupportFragmentManager(),FirstFragmentName,SecondFragmentName);
        activityFollowingFollowersBinding.vpFollowingFollowers.setAdapter(viewPagerFollowingFollowersAdapter);
        activityFollowingFollowersBinding.tlFollowingFollowers.setupWithViewPager(activityFollowingFollowersBinding.vpFollowingFollowers);
        activityFollowingFollowersBinding.vpFollowingFollowers.setCurrentItem(tabPosition);
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