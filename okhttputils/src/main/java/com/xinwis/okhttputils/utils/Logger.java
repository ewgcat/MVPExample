package com.xinwis.okhttputils.utils;

import android.util.Log;

/**
 * Created by zhy on 15/11/6.
 */
public class Logger {
    private static boolean debug = false;

    public void setDebug(boolean debug){
        this.debug=debug;
    }

    public static void e(String tag,String msg) {
        if (debug) {
            Log.e(tag, msg);
        }
    }

    public static void i(String tag,String msg) {
        if (debug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag,String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }

}

