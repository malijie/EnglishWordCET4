package com.english.fragments;

import com.english.activity.HelpAndTipActivity;
import com.english.ad.AdUtil;
import com.english.cet4.R;
import com.english.widget.FontDialog;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
 

public class SettingFragment extends Fragment implements OnClickListener{
	private View viewSetting = null;
	private Button buttonSetWord = null;
	private ImageButton buttonMoreWord = null;
	private Button buttonSetHelp = null;
	private Button buttonUpdate = null;
	private ImageButton buttonMoreHelp = null;
	private Button buttonFeedback = null;
	private static final int MODE_WORD = 0;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		viewSetting = inflater.inflate(R.layout.setting_layout, null, false);
		initView();
		return viewSetting;
	}  
	private void initView() {
		buttonSetWord = (Button) viewSetting.findViewById(R.id.setting_button_font_word);
		buttonMoreWord = (ImageButton) viewSetting.findViewById(R.id.setting_button_more_word);
		buttonSetHelp = (Button) viewSetting.findViewById(R.id.setting_button_help);
		buttonMoreHelp = (ImageButton) viewSetting.findViewById(R.id.setting_button_more_help);
		buttonUpdate = (Button) viewSetting.findViewById(R.id.setting_button_update);
		buttonFeedback = (Button) viewSetting.findViewById(R.id.setting_button_feedback);
		
		buttonSetWord.setOnClickListener(this);
		buttonMoreWord.setOnClickListener(this);
		buttonSetHelp.setOnClickListener(this);
		buttonMoreHelp.setOnClickListener(this);
		buttonUpdate.setOnClickListener(this);
		buttonFeedback.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.setting_button_font_word:
			setWordFont();
			break;
		case R.id.setting_button_more_word:
			setWordFont();
			break;
		case R.id.setting_button_help:
			showHelpTip();
			break;
		  
		case R.id.setting_button_more_help:
			showHelpTip();
			break;

		case R.id.setting_button_update:
			AdUtil.checkUpdate(getActivity());
			break;
		
		case R.id.setting_button_feedback:
			 //显示用户反馈
			AdUtil.showFeedback(getActivity());
			break;
		}  
	}
	private void showHelpTip() {
		Intent it = new Intent(getActivity(),HelpAndTipActivity.class);
		startActivity(it); 
	}
	
	private void setWordFont() {
		showFontDialog(MODE_WORD);
	}
	
	private void showFontDialog(int MODE){
		FontDialog fDialog = new FontDialog(getActivity(),MODE);
		fDialog.show();
	}
}
