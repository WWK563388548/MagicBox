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

    private EditText mNowPassword;
    private EditText mNewPassword;
    private EditText mNewPasswordAgain;
    private Button mUpdatePassword;

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

        mNowPassword = (EditText) findViewById(R.id.edit_password_now);
        mNewPassword = (EditText) findViewById(R.id.edit_password_new);
        mNewPasswordAgain = (EditText) findViewById(R.id.edit_password_new_again);
        mUpdatePassword = (Button) findViewById(R.id.update_new_password);
        mUpdatePassword.setOnClickListener(this);


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

            case R.id.update_new_password:
                String nowPassword = mNowPassword.getText().toString().trim();
                String newPassword = mNewPassword.getText().toString().trim();
                String newPasswordAgain = mNewPasswordAgain.getText().toString().trim();

                // Estimate inputted password whether or not is empty
                if (!TextUtils.isEmpty(nowPassword) & !TextUtils.isEmpty(newPassword)
                        & !TextUtils.isEmpty(newPasswordAgain)) {

                    // Estimate inputted password twice whether or not are same
                    if (newPassword.equals(newPasswordAgain)) {
                        // Reset password
                        MyUser.updateCurrentUserPassword(nowPassword, newPassword, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Successful reset", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, "Failure to reset" + e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Inputted password twice are not same", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Passwords can not be empty", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
