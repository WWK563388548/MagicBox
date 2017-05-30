package com.example.wwk.functiondemo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wwk.functiondemo.R;

/**
 * Created by wwk on 17/5/29.
 *
 * No attribution to inquiries
 */

public class PhoneActivity extends BaseActivity implements View.OnClickListener {

    // Input field
    private EditText mInputNumber;
    // Logo
    private ImageView mCompanyLogo;
    // Query result
    private TextView mPhoneResult;
    private Button mButton1, mButton2, mButton3, mButton4, mButton5, mButton6,
            mButton7, mButton8, mButton9, mButton0, mButtonDelete, mButtonQuery;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initializeView();
    }

    // Initialize View
    private void initializeView() {

        mInputNumber = (EditText) findViewById(R.id.input_phone_number_edit);
        mCompanyLogo = (ImageView) findViewById(R.id.company_image_view);
        mPhoneResult = (TextView) findViewById(R.id.phone_result_text_view);

        mButton1 = (Button) findViewById(R.id.kb_button_1);
        mButton1.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.kb_button_2);
        mButton2.setOnClickListener(this);
        mButton3 = (Button) findViewById(R.id.kb_button_3);
        mButton3.setOnClickListener(this);
        mButton4 = (Button) findViewById(R.id.kb_button_4);
        mButton4.setOnClickListener(this);
        mButton5 = (Button) findViewById(R.id.kb_button_5);
        mButton5.setOnClickListener(this);
        mButton6 = (Button) findViewById(R.id.kb_button_6);
        mButton6.setOnClickListener(this);
        mButton7 = (Button) findViewById(R.id.kb_button_7);
        mButton7.setOnClickListener(this);
        mButton8 = (Button) findViewById(R.id.kb_button_8);
        mButton8.setOnClickListener(this);
        mButton9 = (Button) findViewById(R.id.kb_button_9);
        mButton9.setOnClickListener(this);
        mButton0 = (Button) findViewById(R.id.kb_button_0);
        mButton0.setOnClickListener(this);
        mButtonDelete = (Button) findViewById(R.id.kb_button_delete);
        mButtonDelete.setOnClickListener(this);
        mButtonQuery = (Button) findViewById(R.id.kb_button_query);
        mButtonQuery.setOnClickListener(this);

        // Set a event when users long click delete button
        mButtonDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mInputNumber.setText("");
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

        // Get inputted value from EditText
        String inputString = mInputNumber.getText().toString().trim();
        switch (v.getId()) {
            case R.id.kb_button_1:
            case R.id.kb_button_2:
            case R.id.kb_button_3:
            case R.id.kb_button_4:
            case R.id.kb_button_5:
            case R.id.kb_button_6:
            case R.id.kb_button_7:
            case R.id.kb_button_8:
            case R.id.kb_button_9:
            case R.id.kb_button_0:
                // Add number when you click keyboard
                mInputNumber.setText(inputString + ((Button) v).getText());
                // Move cursor
                mInputNumber.setSelection(inputString.length() + 1);
                break;
            
            case R.id.kb_button_delete:
                if (!TextUtils.isEmpty(inputString) && inputString.length() > 0) {
                    // Remove number when you click del button
                    mInputNumber.setText(inputString.substring(0, inputString.length() - 1));
                    // Move cursor
                    mInputNumber.setSelection(inputString.length() - 1);
                }
                break;
            
            case R.id.kb_button_query:
                if (!TextUtils.isEmpty(inputString)) {
                    getPhoneNumberInfor(inputString);
                }
                break;
        }

    }

    // Get information of phone number
    private void getPhoneNumberInfor(String inputString) {
    }
}

