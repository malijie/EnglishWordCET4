package com.english.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.english.cet4.R;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;

public class WordExampleDetailActivity extends Activity implements OnClickListener{

	private TextView txtSymbols = null;
	private TextView txtWord = null;
	private TextView txtContent = null;
	private TextView txtExample = null;		
	private Button butDelete = null;
	private int id;
	private String sSymbols;
	private String sWord;
	private String sContent;
	private String sExample;
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.words_example_layout);
		
		initData();  
		initDatabase();
		initView();
	}


	private void initDatabase() {
		eHelper = new EnglishDatabaseHelper(this);
		eOperate = new EnglishDBOperate(eHelper.getWritableDatabase(),this);
	}


	private void initView() {
		txtWord = (TextView) super.findViewById(R.id.word_example_word);
		txtSymbols = (TextView) super.findViewById(R.id.word_example_symbol);
		txtContent = (TextView) super.findViewById(R.id.word_example_content);
		txtExample = (TextView) super.findViewById(R.id.word_example_detail);
		butDelete = (Button) super.findViewById(R.id.word_example_button_delete);
		
		txtSymbols.setText(Html.fromHtml(sSymbols));
		txtWord.setText(Html.fromHtml(sWord));
		txtContent.setText(Html.fromHtml(sContent)); 
		txtExample.setText(Html.fromHtml(sExample));
		txtWord.setMovementMethod(ScrollingMovementMethod.getInstance()); //���ô�������������
		txtExample.setMovementMethod(ScrollingMovementMethod.getInstance());
		
		butDelete.setOnClickListener(this);
	}
	
	private void initData(){
		Intent it = getIntent();
		id = it.getIntExtra("id", 1);
		sSymbols = it.getStringExtra("symbols");
		sWord = it.getStringExtra("word");
		sContent = it.getStringExtra("content");
		sExample = it.getStringExtra("example");
		
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.word_example_button_delete: 
			eOperate.updateWordIsKnownById(false, id);
			Toast.makeText(WordExampleDetailActivity.this, "已删除！", Toast.LENGTH_SHORT).show();
			break;
		} 
	}
 
	@Override
	protected void onStop() {
		eHelper.close();
		super.onStop();
	}
	
	
}
