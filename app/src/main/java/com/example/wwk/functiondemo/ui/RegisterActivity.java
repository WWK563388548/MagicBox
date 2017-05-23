package com.example.wwk.functiondemo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.entity.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wwk on 17/5/23.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    // Bmob's Key
    public static final String BMOB_APP_ID = "4d345003be01bddf52e5e1bfbe0f2ea2";
    private EditText mEditUsers;
    private EditText mEditAge;
    private EditText mEditDescription;
    private RadioGroup mRadioGroup;
    private EditText mEnterPassword;
    private EditText mPasswordAgain;
    private EditText mEditEmail;
    private Button mNewRegisterButton;
    // define gender's variable
    private boolean isGender = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Bmob
        Bmob.initialize(this, BMOB_APP_ID);
        initializeView();
    }

    private void initializeView() {
        mEditUsers = (EditText) findViewById(R.id.edit_username);
        mEditAge = (EditText) findViewById(R.id.edit_age);
        mEditDescription = (EditText) findViewById(R.id.edit_intro);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mEnterPassword = (EditText) findViewById(R.id.edit_password);
        mPasswordAgain = (EditText) findViewById(R.id.edit_password_again);
        mEditEmail = (EditText) findViewById(R.id.edit_email);
        mNewRegisterButton = (Button) findViewById(R.id.register);
        mNewRegisterButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                // Get value from EditText
                String username = mEditUsers.getText().toString().trim();
                String age = mEditAge.getText().toString().trim();
                String description = mEditDescription.getText().toString().trim();
                String password = mEnterPassword.getText().toString().trim();
                String passwordAgain = mPasswordAgain.getText().toString().trim();
                String email = mEditEmail.getText().toString().trim();

                // Estimate whether or not is empty
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(password)
                        & !TextUtils.isEmpty(passwordAgain) & !TextUtils.isEmpty(email)) {

                    // Estimate passwords whether or not is equal that were getting from input
                    if (password.equals(passwordAgain)) {

                        // Estimate User's gender
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.button_male) {
                                    isGender = true;
                                } else if (checkedId == R.id.button_female){
                                    isGender = false;
                                }
                            }
                        });

                        // Estimate description whether or not is empty
                        if (TextUtils.isEmpty(description)) {
                            description = "This man is lazy and nothing is left.";
                        }

                        // Register
                        MyUser user = new MyUser();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setAge(Integer.parseInt(age));
                        user.setGender(isGender);
                        user.setIntroduction(description);

                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {

                                if (e == null) {
                                    Toast.makeText(RegisterActivity.this, "Successful Register", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Failure to Register" + e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(this, "Inputted passwords are not equal", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(this, "Input fields can not be empty", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
}
