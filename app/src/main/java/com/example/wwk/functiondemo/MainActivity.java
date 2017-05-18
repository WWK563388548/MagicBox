package com.example.wwk.functiondemo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wwk.functiondemo.fragment.FragmentDemoFour;
import com.example.wwk.functiondemo.fragment.FragmentDemoOne;
import com.example.wwk.functiondemo.fragment.FragmentDemoThree;
import com.example.wwk.functiondemo.fragment.FragmentDemoTwo;
import com.example.wwk.functiondemo.ui.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    // Titles for tabs
    private List<String> mTitle;
    private List<Fragment> mFragment;
    // FloatingActionButton
    private FloatingActionButton mFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // remove shadow of action bar
        getSupportActionBar().setElevation(0);

        initializeData();
        initializeView();
    }

    private void initializeData() {
        mTitle = new ArrayList<>();

        mTitle.add(getString(R.string.title_1));
        mTitle.add(getString(R.string.title_2));
        mTitle.add(getString(R.string.title_3));
        mTitle.add(getString(R.string.title_4));

        mFragment = new ArrayList<>();

        mFragment.add(new FragmentDemoOne());
        mFragment.add(new FragmentDemoTwo());
        mFragment.add(new FragmentDemoThree());
        mFragment.add(new FragmentDemoFour());

    }

    private void initializeView() {

        mFloatingButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        mFloatingButton.setOnClickListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        // Pre-load
        mViewPager.setOffscreenPageLimit(mFragment.size());

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            // Set titles
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        // Binding TabLayout with ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_action_button:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }
}
