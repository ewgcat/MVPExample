package com.yijian.person.util;


import android.util.Log;

import com.yijian.person.BuildConfig;


public final class Logger {
    private static final String DEFAULT_TAG = "BaoliManager_Log";
    private static String TAG = DEFAULT_TAG;
    private static boolean debug = BuildConfig.DEBUG;


    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setTag(String tag) {
        this.TAG = tag;
    }


    public static void v(String message) {
        if (debug) {
            Log.v(TAG, message);
        }
    }

    public static void d(String message) {
        if (debug) {
            Log.d(TAG, message);
        }
    }

    public static void i(String message) {
        if (debug) {
            Log.i(TAG, message);
        }
    }

    public static void w(String message) {
        if (debug) {
            Log.w(TAG, message);
        }
    }

    public static void e(String message) {
        if (debug) {
            Log.e(TAG, message);
        }
    }

    public static void wtf(String message) {
        if (debug) {
            Log.wtf(TAG, message);
        }
    }

    public static void v(String tag, String message) {
        if (debug) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (debug) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (debug) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (debug) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (debug) {
            Log.e(tag, message);
        }
    }

    public static void wtf(String tag, String message) {
        if (debug) {
            Log.wtf(tag, message);
        }
    }

}
