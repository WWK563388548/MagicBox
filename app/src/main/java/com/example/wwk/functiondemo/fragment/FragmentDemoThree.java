package com.example.wwk.functiondemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.adapter.ImageAdapter;
import com.example.wwk.functiondemo.entity.ImagesData;
import com.example.wwk.functiondemo.ui.ExternalDialog;
import com.example.wwk.functiondemo.utils.L;
import com.example.wwk.functiondemo.utils.PicassoUtils;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by wwk on 17/5/18.
 */

public class FragmentDemoThree extends Fragment {

    public static final String IMAGE_URL_FROM_GANK = "http://gank.io/api/search/query/listview/category/";
    private GridView mGridView;
    private List<ImagesData> mList = new ArrayList<>();
    private ImageAdapter mAdapter;
    private ExternalDialog mDialog;
    // Preview
    private ImageView mImage;
    // Data of images' url
    private List<String> mListUrl = new ArrayList<>();
    // PhotoView
    private PhotoViewAttacher mAttacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_three, container, false);
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {

        mGridView = (GridView) view.findViewById(R.id.grid_view_of_images);
        // Initialize dialog
        mDialog = new ExternalDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                R.layout.dialog_picture, R.style.dialog_theme, Gravity.CENTER,R.style.anim_style);
        mImage = (ImageView) mDialog.findViewById(R.id.image_of_dialog);

        String welfare = null;
        try {
            // Gank升級 需要转码
            welfare = URLEncoder.encode("福利", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Parse
        RxVolley.get(IMAGE_URL_FROM_GANK + welfare + "/count/50/page/1", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.information("Girl Json:" + t);
                parsingJson(t);
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Parse picture
                PicassoUtils.loadImageView(getActivity(), mListUrl.get(position), mImage);
                // Zoom
                mAttacher = new PhotoViewAttacher(mImage);
                // Refresh
                mAttacher.update();
                mDialog.show();
            }
        });
    }

    private void parsingJson(String t) {

        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                mListUrl.add(url);
                ImagesData data = new ImagesData();
                data.setmImageUrl(url);
                mList.add(data);
            }

            mAdapter = new ImageAdapter(getActivity(), mList);
            mGridView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
