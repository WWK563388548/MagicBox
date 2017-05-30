package com.example.wwk.functiondemo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wwk on 17/5/29.
 *
 * No attribution to inquiries
 */

public class PhoneActivity extends BaseActivity implements View.OnClickListener {

    public static final String PHONE_INFOR_KEY = "fbc1d484714d9123a4b9d4ec8d2aa077";
    // Input field
    private EditText mInputNumber;
    // Logo
    private ImageView mCompanyLogo;
    // Query result
    private TextView mPhoneResult;
    private Button mButton1, mButton2, mButton3, mButton4, mButton5, mButton6,
            mButton7, mButton8, mButton9, mButton0, mButtonDelete, mButtonQuery;

    private boolean flag = false;


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

                if (flag) {
                    flag = false;
                    inputString = "";
                    mInputNumber.setText("");
                }

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
                } else {
                    Toast.makeText(this, "Input can not be empty", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    // Get information of phone number
    private void getPhoneNumberInfor(String inputString) {
        String url = "http://apis.juhe.cn/mobile/get?phone=" + inputString + "&key=" + PHONE_INFOR_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.information("Information:" + t);
                parsingJson(t);
            }
        });
    }

    // Parse data of Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            mPhoneResult.setText("Local:" + province + city + "\n"
                    + "Areacode:" + areacode + "\n"
                    + "Zipcode:" + zip + "\n"
                    + "Operator:" + company);

            // Displays logo of Operators
            switch (company) {
                case "移动":
                    mCompanyLogo.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    mCompanyLogo.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    mCompanyLogo.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }
            flag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

