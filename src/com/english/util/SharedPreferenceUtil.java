package com.english.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtil {
	private SharedPreferences sp = null;
	private Editor e = null;
	private Context mContext = null;
	private final String SP_WORD_PROGRESS = "word_progress";
	private final String SP_WORD_PROGRESS_KEY = "lesson";

	public SharedPreferenceUtil(Context context){
		sp = context.getSharedPreferences("english_setting", Context.MODE_PRIVATE);
		mContext = context;
	}
	
	public void setFontSize(String key, int value){
		e = sp.edit();
		e.putInt(key, value);
		e.commit();
	}
	
	public int getFontSize(String key){
		if(sp != null){
			return sp.getInt(key, 17);
		}
		return 17;
	}

	/**
	 * 保存单词学习进度
	 * @param progress
	 */
	public void saveWordProgress(int lesson,int progress){
		mContext.getSharedPreferences(SP_WORD_PROGRESS,Context.MODE_PRIVATE).edit().putInt(SP_WORD_PROGRESS_KEY + lesson,progress).commit();
	}

	/**
	 * 读取单词进度
	 * @return
	 */
	public int loadWordProgress(int lesson){
		return mContext.getSharedPreferences(SP_WORD_PROGRESS,Context.MODE_PRIVATE).getInt(SP_WORD_PROGRESS_KEY + lesson,0);
	}
}