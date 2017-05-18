package com.example.wwk.functiondemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wwk.functiondemo.R;

/**
 * Created by wwk on 17/5/18.
 */

public class FragmentDemoFour extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_four, container, false);
        return view;
    }
}
