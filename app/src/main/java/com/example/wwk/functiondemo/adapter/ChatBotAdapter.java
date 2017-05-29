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

    // Type of left
    public static final int VALUE_LEFT_TEXT = 1;
    // Type of right
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context mContext;
    private LayoutInflater inflater;
    private ChatBotData data;
    private List<ChatBotData> mChatList;

    public ChatBotAdapter(Context mContext, List<ChatBotData> mChatList) {
        this.mContext = mContext;
        this.mChatList = mChatList;
        // Get service of system
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mChatList.size();
    }

    @Override
    public Object getItem(int position) {
        return mChatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        //Get current type, and  according to this type to distinguish data of loading
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    convertView = inflater.inflate(R.layout.left_item_chat, null);
                    viewHolderLeftText.mChatTextLeft = (TextView) convertView.findViewById(R.id.chat_left_text_view);
                    convertView.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    convertView = inflater.inflate(R.layout.right_item_chat, null);
                    viewHolderRightText.mChatTextRight = (TextView) convertView.findViewById(R.id.chat_right_text_view);
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

        // Set data
        ChatBotData data = mChatList.get(position);
        switch (type){
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.mChatTextLeft.setText(data.getTextOfChat());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.mChatTextRight.setText(data.getTextOfChat());
                break;
        }

        return convertView;
    }

    // According to position of data to decide how to returns(display) item
    @Override
    public int getItemViewType(int position) {
        ChatBotData data = mChatList.get(position);
        int type = data.getType();
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return mChatList.size() + 1; // 3 ?
    }

    // Left text of chat
    class ViewHolderLeftText {
        private TextView mChatTextLeft;
    }

    // Right text of chat
    class ViewHolderRightText {
        private TextView mChatTextRight;
    }

}
