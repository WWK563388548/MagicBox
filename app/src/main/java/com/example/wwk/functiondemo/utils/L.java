package com.example.wwk.functiondemo.utils;

import android.util.Log;

/**
 * Created by wwk on 17/5/21.
 *
 * Encapsulate Log class
 */

public class L {

    // switch handle
    public static final boolean DEBUG = true;
    // LOG-TAG
    public static final String LOG_TAG = "functiondemo";

    // Six classes of Log (verbose, debug, Information, warning, error, fatal)
    public static void verbose(String text) {
        if (DEBUG) {
            Log.v(LOG_TAG, text);
        }
    }

    public static void debug(String text) {
        if (DEBUG) {
            Log.d(LOG_TAG, text);
        }
    }

    public static void information(String text){
        if (DEBUG) {
            Log.i(LOG_TAG, text);
        }
    }

    public static void warning(String text) {
        if (DEBUG) {
            Log.w(LOG_TAG, text);
        }
    }

    public static void error(String text) {
        if (DEBUG) {
            Log.e(LOG_TAG, text);
        }
    }

    // Fatal --> wt(very rare)
}
