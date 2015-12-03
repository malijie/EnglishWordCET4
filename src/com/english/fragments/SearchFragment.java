package com.english.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.english.activity.SearchDetailActivity;
import com.english.ad.AdUtil;
import com.english.adapter.SearchAdapter;
import com.english.cet4.R;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.model.WordInfo;

public class SearchFragment extends Fragment{
	private View viewSearch = null;
	private LinearLayout adLayout = null;
	private ListView searchListView = null;
	private ImageButton buttonSearch = null;
	private EditText editInput = null;
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	private List<WordInfo> wordInfos = null;
	private SearchAdapter sAdapter = null;
	private String keyWord;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		  
		viewSearch = inflater.inflate(R.layout.search_layout, container, false);
		initView();
		initDatabase();
		
		return viewSearch;
	}
	
	private void initDatabase() {
		eHelper = new EnglishDatabaseHelper(getActivity());
		eOperate = new EnglishDBOperate(eHelper.getReadableDatabase(), getActivity());
	}
	
	private void searchWordInfos(String keyWord){
		wordInfos.clear(); 
		wordInfos = eOperate.getSearchResult(keyWord);
	}
	 
	
	private void initView() {
		searchListView = (ListView) viewSearch.findViewById(R.id.search_layout_listview_result);
		buttonSearch = (ImageButton) viewSearch.findViewById(R.id.search_layout_button_search);
		editInput = (EditText) viewSearch.findViewById(R.id.search_layout_edittext_input);
		buttonSearch.setOnClickListener(new OnClickListenerImpl()); 
		adLayout = (LinearLayout) viewSearch.findViewById(R.id.search_layout_layout_ad);
		
		wordInfos = new ArrayList<WordInfo>();
		searchListView.setOnItemClickListener(new OnItemClickListenerImpl());
		//AdUtil.showMiniAd(getActivity(), adLayout,15);
	}

	private class OnClickListenerImpl implements OnClickListener{

		@Override 
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.search_layout_button_search: 
				searchListView.setVisibility(View.VISIBLE);
				keyWord = editInput.getText().toString().trim();

				if(keyWord == null || keyWord.toString().trim().equals("")){
					Toast.makeText(getActivity(), "请输入要查询的单词...", Toast.LENGTH_SHORT).show();
					return;
				} 
				searchWordInfos(keyWord);
				if(wordInfos.size() > 0){
					sAdapter = new SearchAdapter(getActivity(), wordInfos);
					searchListView.setAdapter(sAdapter);
				}else{
					Toast.makeText(getActivity(), "查无此词...", Toast.LENGTH_SHORT).show();
					wordInfos.clear(); 
				}
				hideInput();
				break;
			}
		}
		
	}
	 
	private void hideInput(){
		((InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getActivity()
                                                .getCurrentFocus().getWindowToken(),
                                                InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	private class OnItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			WordInfo wordInfo = wordInfos.get(position);
			Intent it = new Intent(getActivity(),SearchDetailActivity.class);
			it.putExtra("word_info", wordInfo);
			startActivity(it);
		}

	}

}
