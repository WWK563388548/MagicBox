package com.example.wwk.functiondemo.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.wwk.functiondemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwk on 17/5/21.
 *
 * This is a guide page When users are using this app firstly.
 */

public class GuideActivity extends AppCompatActivity {

    private ViewPager mGuideViewPager;
    // A container of three guide pages
    private List<View> mList = new ArrayList<>();
    private View view1, view2, view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initializeView();
    }

    // Initialize view
    private void initializeView() {

        mGuideViewPager = (ViewPager) findViewById(R.id.guide_view_pager);
        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);
        view3 = View.inflate(this, R.layout.pager_item_three, null);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        // Create a Adapter
        mGuideViewPager.setAdapter(new GuideAdapter());
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(mList.get(position));
        }
    }
}
