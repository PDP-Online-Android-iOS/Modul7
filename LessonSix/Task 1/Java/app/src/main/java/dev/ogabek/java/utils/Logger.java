package dev.ogabek.java.utils;

import android.util.Log;

public class Logger {

    public static boolean IS_TESTER = true;
    public static void d(String TAG, String msg) {
        if (IS_TESTER) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (IS_TESTER) {
            Log.e(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (IS_TESTER) {
            Log.i(TAG, msg);
        }
    }

    public static void v(String TAG, String msg) {
        if (IS_TESTER) {
            Log.v(TAG, msg);
        }
    }

}
