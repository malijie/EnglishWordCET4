package com.english.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.english.ad.AdUtil;
import com.english.cet4.R;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.media.EnglishMediaPlayer;
import com.english.model.WordInfo;

public class UnknownWordDetailActivity extends Activity implements OnClickListener{
	private WordInfo mWordInfo = null;
	private TextView textWord = null;
	private TextView textSymbol = null;
	private TextView textContent = null;
	private TextView textExample = null;
 	private Button butDelete = null;
    //单词音频
    private ImageButton buttonVoice = null;
 	private LinearLayout adLayout = null;
 	private EnglishDatabaseHelper eHelper = null;
 	private EnglishDBOperate eOperate = null;
 	private boolean isDeleted = false;

    private EnglishMediaPlayer mPlayer = null;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.unknown_words_detail_layout);
		
		initData();
		initView();
		setData();
		
	} 
	
	private void setData() {
		textWord.setText(mWordInfo.getWord());
		textSymbol.setText(mWordInfo.getSymbols());
		textExample.setText(Html.fromHtml(mWordInfo.getExample())); 
		textContent.setText(mWordInfo.getContent());
		textExample.setMovementMethod(ScrollingMovementMethod.getInstance());
		//AdUtil.showMiniAd(this, adLayout,10);
	}
 
	private void initView() {
		textWord = (TextView) super.findViewById(R.id.unkonwn_words_detail_text_word);
		textExample = (TextView) super.findViewById(R.id.unkonwn_words_detail_text_example);
		textSymbol = (TextView) super.findViewById(R.id.unkonwn_words_detail_text_symbol);
		textContent = (TextView) super.findViewById(R.id.unkonwn_words_detail_text_content);
		butDelete = (Button) super.findViewById(R.id.unkonwn_words_detail_button_delete);
		adLayout = (LinearLayout) super.findViewById(R.id.unkonwn_words_detail_layout_ad);
        buttonVoice = (ImageButton) super.findViewById(R.id.unkonwn_words_detail_button_volume);
        buttonVoice.setOnClickListener(this);
		butDelete.setOnClickListener(this);
	}

	private void initData() {
		mWordInfo = (WordInfo) getIntent().getSerializableExtra("wordinfo");
		eHelper = new EnglishDatabaseHelper(this);
		eOperate = new EnglishDBOperate(eHelper.getWritableDatabase(), this);
        mPlayer = EnglishMediaPlayer.getInstance(UnknownWordDetailActivity.this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.unkonwn_words_detail_button_delete:
			if(!isDeleted){
				eOperate.updateUnknownWordStatusById(mWordInfo.getId());
				Toast.makeText(UnknownWordDetailActivity.this,"已删除", Toast.LENGTH_SHORT).show();
				isDeleted = true;
				UnknownWordDetailActivity.this.finish();
				Intent it = new Intent(UnknownWordDetailActivity.this,UnknownWordActivity.class);
				startActivity(it);	
				UnknownWordDetailActivity.this.finish();
			}
            case R.id.unkonwn_words_detail_button_volume:
                mPlayer.playTheWordTune(mWordInfo.getWord());
                break;
		}
	}

	@Override
	protected void onDestroy() {
		if(eHelper != null){
			eHelper.close();
			eHelper = null;
		}
		super.onDestroy();

        mPlayer.stopPlay();
	}
	
}
