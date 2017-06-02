package com.example.wwk.functiondemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by wwk on 17/6/2.
 *
 * Encapsulate Picasso
 */

public class PicassoUtils {

    // Load picture acquiescently
    public static void loadImageView(Context mContext, String url, ImageView imageView) {
        Picasso.with(mContext).load(url).into(imageView);
    }

    // Load picture acquiescently(Specify picture size)
    public static void loadImageViewSize(Context mContext, String url, int width, int height, ImageView imageView) {
        Picasso.with(mContext).load(url).config(Bitmap.Config.RGB_565).resize(width, height).centerCrop().into(imageView);
    }

    // Load picture that it has default picture
    public static void loadImageViewHolder(Context mContext, String url, int loadImg, int errorImg, ImageView imageView) {
        Picasso.with(mContext).load(url).placeholder(loadImg).error(errorImg).into(imageView);
    }

    // Tailor picture
    public static void loadImageViewCrop(Context mContext, String url,ImageView imageView) {
        Picasso.with(mContext).load(url).transform(new CropSquareTransformation()).into(imageView);
    }

    // Tailor picture at the ratio
    private static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "wwk";
        }
    }
}
