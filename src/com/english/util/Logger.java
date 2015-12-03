package com.english.util;

import android.util.Log;

/**
 * Created by vic_ma on 15/12/3.
 */
public class Logger {

    public static void d(String TAG,String msg){
        if(Profile.LOG_SWITCH){
            Log.d(TAG,msg);
        }
    }

    public static void e(String TAG,String msg){
        if(Profile.LOG_SWITCH){
            Log.e(TAG,msg);
        }
    }

    public static void i(String TAG,String msg){
        if(Profile.LOG_SWITCH){
            Log.i(TAG,msg);
        }
    }

    public static void w(String TAG,String msg){
        if(Profile.LOG_SWITCH){
            Log.w(TAG,msg);
        }
    }

    public static void v(String TAG,String msg){
        if(Profile.LOG_SWITCH){
            Log.v(TAG,msg);
        }
    }
}
