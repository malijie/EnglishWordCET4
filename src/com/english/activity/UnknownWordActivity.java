package com.english.activity;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.english.ad.AdUtil;
import com.english.adapter.UnknownWordsAdapter;
import com.english.cet4.R;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.model.WordInfo;


public class UnknownWordActivity extends Activity{
	private ListView unknownListView = null;
	private List<WordInfo> uWordsList = null;
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	private UnknownWordsAdapter uAdapter = null;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.unknown_words_layout);
		
		initData();
		initView();
	}


	private void initView() {
		unknownListView = (ListView) super.findViewById(R.id.unkonwn_words_listview);
		UnknownWordsAdapter uAdapter = new UnknownWordsAdapter(this, uWordsList);
		unknownListView.setAdapter(uAdapter);
		
		unknownListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				WordInfo mWordInfo = uWordsList.get(position);
				Intent it = new Intent(UnknownWordActivity.this,UnknownWordDetailActivity.class);
				it.putExtra("wordinfo", mWordInfo);
				startActivity(it);
			}
		}); 
		
	}

	private void initData() {
		initDB();
		uWordsList = eOperate.getAllUnknownWords();
		
		if(uWordsList.size() == 0){
			Toast.makeText(UnknownWordActivity.this,"暂时还没有生词哦", Toast.LENGTH_SHORT).show();
			
		}
	}
	
	private void initDB(){
		eHelper = new EnglishDatabaseHelper(this);
		eOperate = new EnglishDBOperate(eHelper.getReadableDatabase(), this);
	}
	
	@Override
	protected void onRestart() {
		refreshData();
		super.onRestart();
	}

	private void refreshData() {
		initData();
		uAdapter = new UnknownWordsAdapter(UnknownWordActivity.this, uWordsList);
		unknownListView.setAdapter(uAdapter);
	}
	
}
