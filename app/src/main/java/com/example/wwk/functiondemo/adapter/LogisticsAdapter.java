package com.example.wwk.functiondemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.entity.LogisticsData;

import java.util.List;

/**
 * Created by wwk on 17/5/28.
 *
 * An adapter of inquiry Logistics' information
 */

public class LogisticsAdapter extends BaseAdapter {

    private Context mContext;
    private List<LogisticsData> mLogisticsList;
    // Get inflater of layout
    private LayoutInflater inflater;
    private LogisticsData data;

    public LogisticsAdapter(Context mContext, List<LogisticsData> mLogisticsList) {
        this.mContext = mContext;
        this.mLogisticsList = mLogisticsList;
        // Get service of system
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mLogisticsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mLogisticsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        // First inflate
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_logistics_item,null);
            viewHolder.mRemark = (TextView) convertView.findViewById(R.id.remark_text_view);
            viewHolder.mZone = (TextView) convertView.findViewById(R.id.zone_text_view);
            viewHolder.mDatetime = (TextView) convertView.findViewById(R.id.datetime_text_view);
            // Set cache
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set data
        data = mLogisticsList.get(position);
        viewHolder.mRemark.setText(data.getRemark());
        viewHolder.mZone.setText(data.getZone());
        viewHolder.mDatetime.setText(data.getDatetime());
        return convertView;
    }

    class ViewHolder {

        private TextView mRemark;
        private TextView mZone;
        private TextView mDatetime;
    }
}
