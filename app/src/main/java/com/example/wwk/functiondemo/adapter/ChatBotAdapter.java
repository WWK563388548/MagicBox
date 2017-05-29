package com.example.wwk.functiondemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.entity.ChatBotData;

import java.util.List;

/**
 * Created by wwk on 17/5/28.
 *
 * An Adapter for chat bot
 */

public class ChatBotAdapter extends BaseAdapter {

    // type of left
    public static final int VALUE_LEFT_TEXT = 1;
    // type of right
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context mContext;
    private LayoutInflater inflater;
    private ChatBotData data;
    private List<ChatBotData> mList;

    public ChatBotAdapter(Context mContext, List<ChatBotData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        // Get service of system
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        // Get type that is need to display currently, and according to this type to distinguish load of data
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    convertView = inflater.inflate(R.layout.left_item_chat, null);
                    viewHolderLeftText.mLeftText = (TextView) convertView.findViewById(R.id.chat_left_text_view);
                    convertView.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    convertView = inflater.inflate(R.layout.right_item_chat, null);
                    viewHolderRightText.mRightText = (TextView) convertView.findViewById(R.id.chat_right_text_view);
                    convertView.setTag(viewHolderRightText);
                    break;
            }
        } else {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) convertView.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) convertView.getTag();
                    break;
            }
        }

        // Set value
        ChatBotData data = mList.get(position);
        switch (type){
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.mLeftText.setText(data.getTextOfChat());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.mRightText.setText(data.getTextOfChat());
                break;
        }
        return convertView;
    }

    // Return item that is need to display according to position
    @Override
    public int getItemViewType(int position) {
        ChatBotData data = mList.get(position);
        int type = data.getType();
        return type;
    }

    // Return all of data of layout
    @Override
    public int getViewTypeCount() {
        return 3; //mList.size + 1
    }

    // Text of left
    class ViewHolderLeftText {
        private TextView mLeftText;
    }

    // Text of right
    class ViewHolderRightText {
        private TextView mRightText;
    }

}
