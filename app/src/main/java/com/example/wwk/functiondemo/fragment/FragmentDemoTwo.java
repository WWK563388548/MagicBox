package com.example.wwk.functiondemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.adapter.NewsAdapter;
import com.example.wwk.functiondemo.entity.NewsData;
import com.example.wwk.functiondemo.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwk on 17/5/18.
 *
 * List of News
 */

public class FragmentDemoTwo extends Fragment {

    public static final String NEWS_KEY = "3065c847ddb881f825b59ae339a92b66";
    private ListView mNewsListView;
    private List<NewsData> newsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_two, container, false);
        initializeView(view);

        return view;
    }

    private void initializeView(View view) {
        mNewsListView = (ListView) view.findViewById(R.id.news_list_view);
        // Parse API
        String url = "http://v.juhe.cn/weixin/query?key=" + NEWS_KEY + "&ps=100";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.information("Json of News:" + t);
                parsingJson(t);
            }
        });


    }

    private void parsingJson(String t) {

        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonList = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject json = (JSONObject) jsonList.get(i);
                NewsData data = new NewsData();
                String newsTitle = json.getString("title");
                String url = json.getString("url");

                data.setmTitle(newsTitle);
                data.setmSource(json.getString("source"));
                data.setmImageUrl(json.getString("firstImg"));
                newsList.add(data);
            }
            NewsAdapter adapter = new NewsAdapter(getActivity(), newsList);
            mNewsListView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
