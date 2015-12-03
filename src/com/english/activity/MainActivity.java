package com.english.activity;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.waps.AppConnect;

import com.english.ad.AdUtil;
import com.english.cet4.R;
import com.english.fragments.SearchFragment;
import com.english.fragments.SettingFragment;
import com.english.fragments.UnknowWordsFragment;
import com.english.fragments.WordsFragment;
import com.english.util.Profile;

public class MainActivity extends Activity implements OnClickListener{
	
	private WordsFragment wordsFragment = null;
	private SearchFragment searchFragment = null;
	private UnknowWordsFragment unknownWordFragment = null;
	private SettingFragment settingFragment = null;
	 
	private RelativeLayout wordsLayout = null;
	private RelativeLayout settingLayout = null;
	private RelativeLayout searchLayout = null;
	private RelativeLayout unknownLayout = null;
	
	private ImageView searchImage = null;
	private ImageView wordsImage = null;
	private ImageView setImage = null;
	private ImageView unknownImage = null;
	
	private TextView searchText = null;
	private TextView wordText = null;
	private TextView unknownText = null;
	private TextView setText = null;
	private long waitTime = 2000;  
	private long touchTime = 0;
	
	private FragmentManager fragmentManager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
//		AdUtil.showPopAd(this);
		setTabSelection(0);
	} 
	
	

	private void initView() {
		wordsLayout = (RelativeLayout) super.findViewById(R.id.main_layout_relativelayout_words);
		settingLayout = (RelativeLayout) super.findViewById(R.id.main_layout_relativelayout_setting);
		searchLayout = (RelativeLayout) super.findViewById(R.id.main_layout_relativelayout_search);
		unknownLayout = (RelativeLayout) super.findViewById(R.id.main_layout_relativelayout_unknown);
		
		searchImage = (ImageView) super.findViewById(R.id.main_layout_button_search);
		unknownImage = (ImageView) super.findViewById(R.id.main_layout_button_unknown);
		wordsImage = (ImageView) super.findViewById(R.id.main_layout_button_words);
		setImage = (ImageView) super.findViewById(R.id.main_layout_button_setting);
		
		wordText = (TextView) super.findViewById(R.id.main_layout_textview_words);
		searchText = (TextView) super.findViewById(R.id.main_layout_textview_search);
		unknownText = (TextView) super.findViewById(R.id.main_layout_textview_unknown);
		setText = (TextView) super.findViewById(R.id.main_layout_textview_setting); 
		
		wordsLayout.setOnClickListener(this);
		searchLayout.setOnClickListener(this);
		unknownLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		
		fragmentManager = getFragmentManager();
		
		
	}
	 
	/** 
	 * ����tabѡ�еĽ���
	 * @param index ѡ�в���
	 */
	private void setTabSelection(int index) {
		clearSelection();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		
		switch(index){
		case 0:
			wordsImage.setImageResource(R.drawable.bottom_button_words_selected);
			wordText.setTextColor(getResources().getColor(R.color.bottom_textview_selected));
			if(wordsFragment == null){ 
				wordsFragment = new WordsFragment();
				transaction.add(R.id.main_layout_frame_content, wordsFragment);
			}else{
				transaction.show(wordsFragment);
			}
			break;
		case 1:
			searchImage.setImageResource(R.drawable.bottom_button_search_selected);
			searchText.setTextColor(getResources().getColor(R.color.bottom_textview_selected));
			if(searchFragment == null){
				searchFragment = new SearchFragment();
				transaction.add(R.id.main_layout_frame_content, searchFragment);
			}else{
				transaction.show(searchFragment);
			}
			break;
		case 2:
			unknownImage.setImageResource(R.drawable.bottom_button_newword_selected);
			unknownText.setTextColor(getResources().getColor(R.color.bottom_textview_selected));
			if(unknownWordFragment == null){
				unknownWordFragment = new UnknowWordsFragment();
				transaction.add(R.id.main_layout_frame_content, unknownWordFragment);
			}else{
				transaction.show(unknownWordFragment);
			}
			break;
		case 3:
			setImage.setImageResource(R.drawable.bottom_button_setting_selected);
			setText.setTextColor(getResources().getColor(R.color.bottom_textview_selected));
			if(settingFragment == null){
				settingFragment = new SettingFragment();
				transaction.add(R.id.main_layout_frame_content, settingFragment);
			}else{
				transaction.show(settingFragment);
			}
			break;
		}
		transaction.commit(); 
		
	}
	


 
	/**
	 * ����Fragment
	 * @param transaction
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if(wordsFragment != null){
			transaction.hide(wordsFragment);
		}
		if(searchFragment != null){
			transaction.hide(searchFragment);
		}
		if(unknownWordFragment != null){
			transaction.hide(unknownWordFragment);
		}
		if(settingFragment != null){
			transaction.hide(settingFragment);
		}
	}

	/**
	 * ���ѡ��״̬
	 */
	private void clearSelection() {
		unknownImage.setImageResource(R.drawable.bottom_button_newword_normal);
		searchImage.setImageResource(R.drawable.bottom_button_search_normal);
		setImage.setImageResource(R.drawable.bottom_button_setting_normal);
		wordsImage.setImageResource(R.drawable.bottom_button_words_normal);
		wordText.setTextColor(getResources().getColor(R.color.bottom_textview_normal));
		searchText.setTextColor(getResources().getColor(R.color.bottom_textview_normal));
		unknownText.setTextColor(getResources().getColor(R.color.bottom_textview_normal));
		setText.setTextColor(getResources().getColor(R.color.bottom_textview_normal));
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.main_layout_relativelayout_words:
			setTabSelection(Profile.BOTTOM_SELECT_WORD);
			break;
		
		case R.id.main_layout_relativelayout_search:
			setTabSelection(Profile.BOTTOM_SELECT_SEARCH);
			break;
			
		case R.id.main_layout_relativelayout_unknown:
			setTabSelection(Profile.BOTTOM_SELECT_UNKNOWNWORD);
			break;
		case R.id.main_layout_relativelayout_setting:
			setTabSelection(Profile.BOTTOM_SELECT_SETTING);
			break;
		}  
	}

 
 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {  
		        long currentTime = System.currentTimeMillis();  
		        if((currentTime-touchTime)>=waitTime) {  
		            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
		            touchTime = currentTime;  
		        }else {  
		            finish();  
		        }  
		        return true;  
	    }   
		
		return super.onKeyDown(keyCode, event);
	}
	
}
