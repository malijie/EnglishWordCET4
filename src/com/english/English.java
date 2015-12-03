package com.english;

import android.app.Application;
import android.content.Context;

/**
 * Created by vic_ma on 15/12/3.
 */
public class English extends Application{
    public static Context mContext = null;
    @Override
    public void onCreate() {
        mContext = getApplicationContext();
    }
}
