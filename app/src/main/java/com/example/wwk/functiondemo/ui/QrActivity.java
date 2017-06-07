package com.example.wwk.functiondemo.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.wwk.functiondemo.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * Created by wwk on 17/6/7.
 *
 * Create QR code
 */

public class QrActivity extends BaseActivity {

    // Qr code
    private ImageView mQRImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        initializeView();
    }

    private void initializeView() {

        mQRImage = (ImageView) findViewById(R.id.qr_code_image);
        // Width of screen
        int width = getResources().getDisplayMetrics().widthPixels;
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("My QR code", width / 2, width / 2,
                BitmapFactory.decodeResource(getResources(), R.drawable.assistant));
        mQRImage.setImageBitmap(qrCodeBitmap);
    }
}
