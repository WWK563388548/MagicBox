package com.example.wwk.functiondemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.adapter.ChatBotAdapter;
import com.example.wwk.functiondemo.entity.ChatBotData;
import com.example.wwk.functiondemo.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwk on 17/5/18.
 *
 * Implement a function of Chat bot in FragmentDemoOne
 */

public class FragmentDemoOne extends Fragment implements View.OnClickListener {

    // Chat bot's key
    public static final String CHAT_BOT_KEY = "6fc208f967f22ebcb5e6d2ad153db96c";
    private ListView mChatListView;
    private List<ChatBotData> mList = new ArrayList<>();
    private ChatBotAdapter adapter;
    // Input field
    private EditText mChatEditText;
    // Button of send a message
    private Button mSendButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_one, container, false);
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {

        mChatListView = (ListView) view.findViewById(R.id.mChatListView);
        mChatEditText = (EditText) view.findViewById(R.id.input_message_edit);
        mSendButton = (Button) view.findViewById(R.id.send_message_button);
        mSendButton.setOnClickListener(this);

        // Set adapter
        adapter = new ChatBotAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);
        addLeftItem("你好，我是chat bot。");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message_button:
                // Get content from EditText
                String text = mChatEditText.getText().toString();
                // Estimate input whether or not is empty
                if (!TextUtils.isEmpty(text)) {
                    // Estimate length of input whether or not is greater than 30
                    if (text.length() > 30) {
                        Toast.makeText(getActivity(), "The input can not greater than 30", Toast.LENGTH_LONG).show();
                    } else {
                        // Clear input field
                        mChatEditText.setText("");
                        // Add message to right of screen
                        addRightItem(text);
                        // Send message to chat bot then return content
                        String url = "http://op.juhe.cn/robot/index?info=" + text
                                + "&key=" + CHAT_BOT_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                L.information("Json" + t);
                                parsingJson(t);
                            }
                        });
                    }
                } else {
                    Toast.makeText(getActivity(), "The input field can not be empty", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonresult = jsonObject.getJSONObject("result");
            // Get value of return
            String text = jsonresult.getString("text");
            // Add chat bot's returned value to left
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Add text to left
    private void addLeftItem(String text) {

        ChatBotData data = new ChatBotData();
        data.setType(ChatBotAdapter.VALUE_LEFT_TEXT);
        data.setTextOfChat(text);
        mList.add(data);
        // Circularize adapter to refresh
        adapter.notifyDataSetChanged();
        // Get to bottom
        mChatListView.setSelection(mChatListView.getBottom());
    }

    // Add text to right
    private void addRightItem(String text) {

        ChatBotData data = new ChatBotData();
        data.setType(ChatBotAdapter.VALUE_RIGHT_TEXT);
        data.setTextOfChat(text);
        mList.add(data);
        // Circularize adapter to refresh
        adapter.notifyDataSetChanged();
        // Get to bottom
        mChatListView.setSelection(mChatListView.getBottom());
    }
}
