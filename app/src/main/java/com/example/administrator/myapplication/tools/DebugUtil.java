//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.administrator.myapplication.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class DebugUtil {
    public static boolean I_DEBUG = true;
    public static boolean D_DEBUG = true;
    public static boolean V_DEBUG = true;
    public static boolean W_DEBUG = true;
    public static boolean E_DEBUG = true;

    public DebugUtil() {
    }

    public static void toast(Context context, String content) {
        Toast.makeText(context, content, 0).show();
    }

    public static void v(String tag, String msg) {
        if(V_DEBUG) {
            Log.v(tag, msg);
        }

    }

    public static void d(String tag, String msg) {
        if(D_DEBUG) {
            Log.d(tag, msg);
        }

    }

    public static void i(String tag, String msg) {
        if(I_DEBUG) {
            Log.i(tag, msg);
        }

    }

    public static void w(String tag, String msg) {
        if(W_DEBUG) {
            Log.w(tag, msg);
        }

    }

    public static void e(String tag, String msg) {
        if(E_DEBUG) {
            Log.e(tag, msg);
        }

    }
}
