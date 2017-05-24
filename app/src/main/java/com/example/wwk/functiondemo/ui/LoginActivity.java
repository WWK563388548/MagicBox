package com.example.wwk.functiondemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wwk.functiondemo.MainActivity;
import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.entity.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wwk on 17/5/23.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Bmob's Key
    public static final String BMOB_APP_ID = "4d345003be01bddf52e5e1bfbe0f2ea2";
    // Button of register
    private Button mRegisterButton;
    private EditText mInputUsername;
    private EditText mInputPassword;
    private Button mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Bmob
        Bmob.initialize(this, BMOB_APP_ID);
        initializeView();
    }

    private void initializeView() {

        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(this);
        mInputUsername = (EditText) findViewById(R.id.input_username_edit);
        mInputPassword = (EditText) findViewById(R.id.input_password_edit);
        mSignIn = (Button) findViewById(R.id.sign_in_button);
        mSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.sign_in_button:
                // Get value from Input fields
                String inputUserame = mInputUsername.getText().toString().trim();
                String inputPassword = mInputPassword.getText().toString().trim();
                // Estimate whether or not is empty
                if (!TextUtils.isEmpty(inputUserame) & !TextUtils.isEmpty(inputPassword)) {
                    // Login
                    final MyUser user = new MyUser();
                    user.setUsername(inputUserame);
                    user.setPassword(inputPassword);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
                                // Estimate email whether or not is verified
                                if (user.getEmailVerified()) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Please validate your email address ", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Failure to login: " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "Inputted fields can not be empty", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
