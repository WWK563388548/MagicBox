package com.example.wwk.functiondemo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

/**
 * Created by wwk on 17/5/27.
 *
 * You can inquiry information about Logistics
 */

public class LogisticsActivity extends BaseActivity implements View.OnClickListener {

    public static final String LOGISTICSKey = "4a03c8db4cacecb615ca6b88a3a1c805";
    private EditText mEditExpressName;
    private EditText mEditExpressNumber;
    private Button mGetInforLogisticsButton;
    private ListView mLogisticsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);

        initializeView();
    }

    private void initializeView() {

        mEditExpressName = (EditText) findViewById(R.id.edit_express_name);
        mEditExpressNumber = (EditText) findViewById(R.id.edit_express_number);
        mGetInforLogisticsButton = (Button) findViewById(R.id.button_get_logistics_infor);
        mGetInforLogisticsButton.setOnClickListener(this);
        mLogisticsList = (ListView) findViewById(R.id.inquiry_logistics_list);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_get_logistics_infor:
                // Get content from EditText
                String name = mEditExpressName.getText().toString().trim();
                String number = mEditExpressNumber.getText().toString().trim();

                // The url of inquiring information
                String url = "http://v.juhe.cn/exp/index?key=" + LOGISTICSKey
                        + "&com=" + name + "&no=" + number;

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    // Get data by Json
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(LogisticsActivity.this, t, Toast.LENGTH_LONG).show();
                            L.information("Json: " + t);
                        }
                    });

                } else {
                    Toast.makeText(this, "Input can not be empty", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
}

