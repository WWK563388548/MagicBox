package com.example.wwk.functiondemo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by wwk on 17/5/24.
 */

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button mGetEmailForChange;
    private EditText mVerifyEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initializeView();
    }

    private void initializeView() {
        mGetEmailForChange = (Button) findViewById(R.id.get_email_button);
        mGetEmailForChange.setOnClickListener(this);
        mVerifyEmail = (EditText) findViewById(R.id.edit_verify_email);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.get_email_button:
                // Get inputted email
                final String verifyEmail = mVerifyEmail.getText().toString().trim();
                // Estimate whether or not is empty
                if (!TextUtils.isEmpty(verifyEmail)) {
                    // Get verify email
                    MyUser.resetPasswordByEmail(verifyEmail, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgotPasswordActivity.this, "Have send verify email to "+ verifyEmail, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Failure to send" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "Email can not be empty", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
