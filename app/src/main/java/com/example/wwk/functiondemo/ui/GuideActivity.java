package com.example.wwk.functiondemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wwk.functiondemo.MainActivity;
import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.utils.L;

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
    // Little points on the guide page
    private ImageView point1, point2, point3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initializeView();
    }

    // Initialize view
    private void initializeView() {

        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);
        point3 = (ImageView) findViewById(R.id.point3);
        // Set default images of point
        setPointImage(true, false, false);

        mGuideViewPager = (ViewPager) findViewById(R.id.guide_view_pager);
        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);
        view3 = View.inflate(this, R.layout.pager_item_three, null);
        view3.findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.start_button:
                        startActivity(new Intent(GuideActivity.this, MainActivity.class));
                        finish();
                        break;
                }
            }
        });

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        // Create a Adapter
        mGuideViewPager.setAdapter(new GuideAdapter());
        // Monitor the ViewPager's slide
        mGuideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // Page change
            @Override
            public void onPageSelected(int position) {
                L.information("Position: " + position);
                switch (position) {
                    case 0:
                        setPointImage(true, false, false);
                        break;
                    case 1:
                        setPointImage(false, true, false);
                        break;
                    case 2:
                        setPointImage(false, false, true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

    // Set effect of points that it's being selected
    private void setPointImage(boolean isSelected1, boolean isSelected2, boolean isSelected3) {

        if (isSelected1) {
            point1.setBackgroundResource(R.drawable.point_on);
        } else {
            point1.setBackgroundResource(R.drawable.point_off);
        }

        if (isSelected2) {
            point2.setBackgroundResource(R.drawable.point_on);
        } else {
            point2.setBackgroundResource(R.drawable.point_off);
        }

        if (isSelected3) {
            point3.setBackgroundResource(R.drawable.point_on);
        } else {
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }
}
