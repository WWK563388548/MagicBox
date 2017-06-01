package com.example.wwk.functiondemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.entity.NewsData;

import java.util.List;

/**
 * Created by wwk on 17/6/1.
 *
 * An Adapter of News
 */

public class NewsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<NewsData> mNewsList;
    private NewsData data;

    public NewsAdapter(Context mContext, List<NewsData> mNewsList) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
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
            convertView = inflater.inflate(R.layout.news_item, null);
            viewHolder.mNewsImage = (ImageView) convertView.findViewById(R.id.news_image);
            viewHolder.mNewsTitle = (TextView) convertView.findViewById(R.id.news_title_text);
            viewHolder.mNewsSource = (TextView) convertView.findViewById(R.id.news_source_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mNewsList.get(position);
        viewHolder.mNewsTitle.setText(data.getmTitle());
        viewHolder.mNewsSource.setText(data.getmSource());

        return convertView;
    }

    class ViewHolder {

        private ImageView mNewsImage;
        private TextView mNewsTitle;
        private TextView mNewsSource;
    }
}
