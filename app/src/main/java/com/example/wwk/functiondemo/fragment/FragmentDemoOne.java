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
import com.example.wwk.functiondemo.utils.SharedPreferencesUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static cn.bmob.v3.Bmob.getApplicationContext;

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
    //TTS
    private SpeechSynthesizer mTts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_one, container, false);
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=5933f95b");
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {

        // 1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        // 2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        // 设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        // 设置语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        // 设置音量，范围0~100
        mTts.setParameter(SpeechConstant.VOLUME, "80");
        // 设置云端
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

        mChatListView = (ListView) view.findViewById(R.id.mChatListView);
        mChatEditText = (EditText) view.findViewById(R.id.input_message_edit);
        mSendButton = (Button) view.findViewById(R.id.send_message_button);
        mSendButton.setOnClickListener(this);

        // Set adapter
        adapter = new ChatBotAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);
        addLeftItem("你好，我是管家。");


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

        boolean isSpeak = SharedPreferencesUtils.getBoolean(getActivity(), "isSpeak", false);
        if (isSpeak) {
            startSpeak(text);
        }

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

    // Start to speak
    private void startSpeak(String text) {
        // Start to compound
        mTts.startSpeaking(text, mSynListener);
    }

    // 合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        // 缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        // 开始播放
        public void onSpeakBegin() {
        }

        // 暂停播放
        public void onSpeakPaused() {
        }

        // 播放进度回调
        // percent为播放进度0~100, beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        // 恢复播放回调接口
        public void onSpeakResumed() {
        }

        // 会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };
}
