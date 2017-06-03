package com.example.wwk.functiondemo.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.entity.ImagesData;
import com.example.wwk.functiondemo.utils.PicassoUtils;

import java.util.List;

/**
 * Created by wwk on 17/6/3.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<ImagesData> mImagesList;
    private LayoutInflater inflater;
    private ImagesData data;
    private WindowManager windowManager;
    private int width;

    public ImageAdapter(Context mContext, List<ImagesData> mImagesList) {
        this.mContext = mContext;
        this.mImagesList = mImagesList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
    }

    @Override
    public int getCount() {
        return mImagesList.size();
    }

    @Override
    public Object getItem(int position) {
        return mImagesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.image_item,null);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.mImageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mImagesList.get(position);
        // Parse image
        String url = data.getmImageUrl();
        PicassoUtils.loadImageViewSize(mContext, url, width/2, 500, viewHolder.mImageView);

        return convertView;
    }

    class ViewHolder{
        private ImageView mImageView;
    }
}
