package com.example.wwk.functiondemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.entity.MyUser;
import com.example.wwk.functiondemo.ui.LoginActivity;
import com.example.wwk.functiondemo.utils.L;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by wwk on 17/5/18.
 *
 * An interface of User's information
 */

public class FragmentDemoFour extends Fragment implements View.OnClickListener {

    // Bmob's Key
    public static final String BMOB_APP_ID = "4d345003be01bddf52e5e1bfbe0f2ea2";
    private Button mExitLoginButton;
    private TextView mEditProfileText;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_four, container, false);
        // Initialize Bmob
        Bmob.initialize(getContext(), BMOB_APP_ID);
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {

        mExitLoginButton = (Button) view.findViewById(R.id.exit_login_button);
        mExitLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            // Exit login
            case R.id.exit_login_button:
                // clear user's cache
                MyUser.logOut();

                BmobUser currentUser = MyUser.getCurrentUser();
                L.information("currentUser's value: " + currentUser);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}
