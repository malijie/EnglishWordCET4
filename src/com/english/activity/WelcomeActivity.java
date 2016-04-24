package com.english.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import cn.waps.AppConnect;

import com.english.English;
import com.english.ad.AdUtil;
import com.english.cet4.R;
import com.english.config.Config;
import com.english.config.Const;
import com.english.util.FileUtil;
import com.english.util.Logger;
import com.english.util.SharedPreferenceUtil;
import com.english.util.Util;

public class WelcomeActivity extends Activity{ 
	private static final String TAG = WelcomeActivity.class.getSimpleName();

	private ImageView imgBackgroud = null;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȫ����ʾ
    	super.setContentView(R.layout.welcome_layout);  
    	
//    	AdUtil.init(this);
    	
    	imgBackgroud = (ImageView) super.findViewById(R.id.welcome_image);
    	AlphaAnimation alpha = new AlphaAnimation(0.1f, 1.0f);
    	alpha.setDuration(2000);
    	imgBackgroud.setAnimation(alpha);
    	alpha.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				initData();
			}   
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			 
			@Override
			public void onAnimationEnd(Animation animation) {
				
				Intent itMain = new Intent(WelcomeActivity.this,MainActivity.class);
				//itMain.putExtra("networkavailable", networkAvailable);
				startActivity(itMain);
				WelcomeActivity.this.finish();
			}
		});
    	
	}
	
	private void initData() {
		FileUtil.copyDB2Phone(WelcomeActivity.this);
        //解压单词到sd卡
        handleUnzipWordsEvent();
	}

	/**
	 * 解压单词到SD卡
	 */
	private void handleUnzipWordsEvent() {
		boolean isZiped = SharedPreferenceUtil.getWordsUnzipStatus(English.mContext);

		//已经解压过就不再解压了
		if(isZiped){
			Logger.d(TAG, "words.zip already have been unziped...");
			return;
		}

		Util.unZipFile2SdCard(WelcomeActivity.this,
				Const.UNZIP_WORDS_FILE_NAME, Config.UNZIP_WORDS_FILE_PATH);
	}
}
