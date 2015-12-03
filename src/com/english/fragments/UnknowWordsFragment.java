package com.english.fragments;

import java.util.List;

import com.english.activity.UnknownWordDetailActivity;
import com.english.ad.AdUtil;
import com.english.adapter.UnknownWordsAdapter;
import com.english.cet4.R;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.model.WordInfo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class UnknowWordsFragment extends Fragment{
	private ListView unknownListView = null;
	private List<WordInfo> uWordsList = null;
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	private UnknownWordsAdapter uAdapter = null;
	private View viewUnknownWord;
	private LinearLayout layoutAd1 = null;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewUnknownWord = inflater.inflate(R.layout.unknown_words_layout, container,false);
		initData();
		initView();
		return viewUnknownWord;
	}
	
	private void initView() {
		layoutAd1 = (LinearLayout) viewUnknownWord.findViewById(R.id.unknown_layout_ad1);
		unknownListView = (ListView) viewUnknownWord.findViewById(R.id.unkonwn_words_listview);
		UnknownWordsAdapter uAdapter = new UnknownWordsAdapter(getActivity(), uWordsList);
		unknownListView.setAdapter(uAdapter);
		
		unknownListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				WordInfo mWordInfo = uWordsList.get(position);
				Intent it = new Intent(getActivity(),UnknownWordDetailActivity.class);
				it.putExtra("wordinfo", mWordInfo);
				startActivity(it);
			}
		}); 
		
		//AdUtil.showMiniAd(getActivity(), layoutAd1, 10);
	}

	private void initData() {
		initDB();
		uWordsList = eOperate.getAllUnknownWords();
	}
	
	private void initDB(){
		eHelper = new EnglishDatabaseHelper(getActivity());
		eOperate = new EnglishDBOperate(eHelper.getReadableDatabase(), getActivity());
	}
	
	
	
	@Override
	public void onResume() {
		refreshData();
		super.onResume();
	}

	private void refreshData() {
		initData();
		uAdapter = new UnknownWordsAdapter(getActivity(), uWordsList);
		unknownListView.setAdapter(uAdapter);
	}
	
	

}
