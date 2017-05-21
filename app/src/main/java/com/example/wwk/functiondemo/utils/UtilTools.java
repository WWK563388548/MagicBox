package com.example.wwk.functiondemo.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by wwk on 17/5/21.
 */

public class UtilTools {

    // set new font
    public static void setFont(Context mContext, TextView textView) {
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/ROBOT.ttf");
        textView.setTypeface(fontType);
    }
}
