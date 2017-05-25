package com.example.wwk.functiondemo.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.wwk.functiondemo.R;

/**
 * Created by wwk on 17/5/25.
 */

public class ExternalDialog extends Dialog {

    // Define templates
    public ExternalDialog(Context context,int layout,int style) {
        this(context, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }

    // Define attributes
    public ExternalDialog(Context context,int width,int height,int layout,int style,int gravity,int anim){
        super(context,style);
        // Set attributes
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }

    // Instance
    public ExternalDialog(Context context,int width,int height,int layout,int style,int gravity){
        this(context,width,height,layout,style,gravity,R.style.anim_style);
    }
}
