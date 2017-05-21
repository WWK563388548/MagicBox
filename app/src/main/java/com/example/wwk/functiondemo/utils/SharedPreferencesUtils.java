package com.example.wwk.functiondemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wwk on 17/5/21.
 * Encapsulate SharedPreferences class
 */

public class SharedPreferencesUtils {

    public static final String NAME = "config";

    // Put data of String(key : value)
    public static void putString(Context mContext, String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    // Get data of String(key : value)
    public static String getString(Context mContext, String key, String defaultValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    // Put data of int(key : value)
    public static void putInt(Context mContext, String key, int value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).commit();
    }

    // Get data of int(key : value)
    public static int getInt(Context mContext, String key, int defaultValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Put data of boolean(key : value)
    public static void putBoolean(Context mContext, String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    // Get data of boolean(key : value)
    public static boolean getBoolean(Context mContext, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Put data of float(key : value)
    public static void putFloat(Context mContext, String key, float value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, MODE_PRIVATE);
        sharedPreferences.edit().putFloat(key, value).commit();
    }

    // Get data of float(key : value)
    public static float getFloat(Context mContext, String key, float defaultValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    // Delete single data of SharedPreferences
    public static void deleteSingleData(Context mContext, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, MODE_PRIVATE);
        sharedPreferences.edit().remove(key).commit();
    }

    // Delete all of data of SharedPreferences
    public static void deleteAllData(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(NAME, MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
