package com.english.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.widget.Toast;

import com.english.model.ReadingInfo;
import com.english.model.WordInfo;
import com.english.model.WrittingInfo;

public class EnglishDBOperate {
	private SQLiteDatabase db = null;
	private final String QUERY_ALL_WORDS = "select * from vocabulary";
	private final String QUERY_WORDS_BY_INDEX = "SELECT * FROM VOCABULARY WHERE ID BETWEEN ";
	private final String UPDATE_WORDS = "UPDATE VOCABULARY SET";
	private Context context;
	
	public EnglishDBOperate(SQLiteDatabase db, Context context){
		this.db = db;
		this.context = context;
	}
	
	/**
	 * 
	 * @return ���ؿγ�����
	 */

	public int getLessonsSize(){
		String strCount = "0";
		Cursor result = null;
		try{
			db.beginTransaction();
			db.setTransactionSuccessful();
			String sql = "select count(id) as total from vocabulary";
			result = db.rawQuery(sql, null);
			if(!result.isAfterLast() ){
				result.moveToFirst(); 
				strCount = result.getString(result.getColumnIndex("total"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
			}
			db.endTransaction();
		}
		
		return Integer.parseInt(strCount);
	}

    /**
     * 将当前课程的正确率置为0
     * @param index  课程号
     */
    public void resumeAccuracyCount(int index){
        db.beginTransaction();
        String sql = "update vocabulary set is_known='false' where id between " + (index*100+1) + " and " + (index*100+100);
        db.execSQL(sql);
        db.setTransactionSuccessful();
        db.endTransaction();
    }
	
	/**
	 * ��ݴ���Ŀγ̺ţ������Ҫ��ʾ�����
	 * @param lesson �γ̺�
	 * @return
	 */
	public List<Map<String, String>> getWordsListByLesson(int lesson){
		Cursor result = null;
		List<Map<String,String>> wordsList = null;
		try{
			db.beginTransaction();
			db.setTransactionSuccessful();
			wordsList = new ArrayList<Map<String,String>>();
			String sql = QUERY_WORDS_BY_INDEX + (lesson * 100 - 99) + " AND "  + lesson * 100;
			result = db.rawQuery(sql, null);
			for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
				Map<String, String> mWord = new HashMap<String, String>();
				mWord.put("id", result.getString(result.getColumnIndex("id")));
				mWord.put("symbols", result.getString(result.getColumnIndex("symbols")));
				mWord.put("word", result.getString(result.getColumnIndex("word")));
				mWord.put("content", result.getString(result.getColumnIndex("content")));
				mWord.put("example", result.getString(result.getColumnIndex("example")));
				mWord.put("note", result.getString(result.getColumnIndex("note")));
				wordsList.add(mWord);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
				db.endTransaction();
			}
		}
		return wordsList;
	}
	
	
	/**
	 * ���id���ҵ���
	 * @return ����
	 */
	public WordInfo getWordById(int index){
		
		WordInfo mWordInfo = null;
		Cursor result = null;
		try{
			db.beginTransaction();
			String sql = QUERY_ALL_WORDS + " where id=" + index;
			result = db.rawQuery(sql, null);
			if(!result.isAfterLast()){
				result.moveToFirst();
				mWordInfo = new WordInfo();
				mWordInfo.setId(result.getInt(result.getColumnIndex("id")));
				mWordInfo.setSymbols(result.getString(result.getColumnIndex("symbols")));
				mWordInfo.setWord(result.getString(result.getColumnIndex("word")));
				mWordInfo.setContent(result.getString(result.getColumnIndex("content")));
				mWordInfo.setExample(result.getString(result.getColumnIndex("example")));
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
			}
			db.endTransaction();
		}
		return mWordInfo;
	}
	
	/**
	 * ��ݿγ̺Ų�����γ��е�����Ϣ
	 * @param lesson�γ̺�
	 * @return
	 */
	public List<WordInfo> getWordsByLesson(int lesson){
		List<WordInfo> lessonWords = null;
		Cursor result = null;
		try{
			db.beginTransaction();
			lessonWords = new ArrayList<WordInfo>();
			String sql = QUERY_ALL_WORDS + " WHERE ID BETWEEN " + (lesson*100+1) + " AND " + (lesson*100+100);
			result = db.rawQuery(sql, null);
			for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
				WordInfo mWordInfo = new WordInfo();
				mWordInfo.setId(result.getInt(result.getColumnIndex("id")));
				mWordInfo.setSymbols(result.getString(result.getColumnIndex("symbols")));
				mWordInfo.setWord(result.getString(result.getColumnIndex("word")));
				mWordInfo.setContent(result.getString(result.getColumnIndex("content")));
				mWordInfo.setExample(result.getString(result.getColumnIndex("example")));
				lessonWords.add(mWordInfo);
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
				db.endTransaction();
			}
		}
		return lessonWords;
	}
	
	/**
	 * ���µ����Ƿ�Ϊ���״̬
	 * @param id id��
	 */
	public void updateWordIsStrangerById(boolean isStranger, int index){
		db.beginTransaction();
		try{
			int id = index + 1;
			String sql = UPDATE_WORDS + " is_stranger='" + isStranger + "' WHERE ID=" + id;
			db.execSQL(sql);
			db.setTransactionSuccessful();
		}catch(Exception e){
			Toast.makeText(context, "Sorry,����ʧ�ܣ����Ժ�����...", Toast.LENGTH_SHORT).show();
		}finally{
			db.endTransaction();
		}
		
	} 
	 
	/**
	 * ���µ����Ƿ�Ϊ���״̬
	 * @param index lessonWords�е����
	 */
	public void updateWordIsKnownByIndex(boolean isKnown, int index){
		db.beginTransaction();
		try{ 
			int id = index + 1; //lessonWords��index��ʼλ��Ϊ0�� ��ݿ���id��ʼλ��Ϊ1��������Ҫ+1
			String sql = UPDATE_WORDS + " is_known='" + isKnown + "' WHERE ID=" + id;
			db.execSQL(sql); 
			db.setTransactionSuccessful();
		}catch(Exception e){
			Toast.makeText(context, "Sorry,����ʧ�ܣ����Ժ�����...", Toast.LENGTH_SHORT).show();
		}finally{
			db.endTransaction();
		}
		
	} 
	
	public void updateWordIsKnownById(boolean isKnown, int id){
		db.beginTransaction();
		try{
			String sql = UPDATE_WORDS + " is_known='" + isKnown + "' WHERE ID=" + id;
			db.execSQL(sql);
			db.setTransactionSuccessful();
		}catch(Exception e){
			Toast.makeText(context, "Sorry,����ʧ�ܣ����Ժ�����...", Toast.LENGTH_SHORT).show();
		}finally{
			db.endTransaction();
		}
	}
	
	/**
	 * ���id���µ���������ʱ��
	 * @param date ������ʱ�� 
	 * @param id ����id 
	 */
	public void updateLastVisitDateById(String date, int id){
		db.beginTransaction();
		try{
			String sql = UPDATE_WORDS + " last_visit='" + date + "' WHERE ID=" + id;
			db.execSQL(sql);
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
			Toast.makeText(context, "Sorry,����ʧ�ܣ����Ժ�����...", Toast.LENGTH_SHORT).show();
		}finally{
			db.endTransaction(); 
		}
	}
	
	/**
	 * ��ݿγ̺Ų�ѯ������������ʱ��
	 * @param lesson �γ̺�
	 * @return ���пγ�����ʱ�伯��
	 */
	public List<String> getLastVisitDateListByLesson(int lesson){
		List<String> dateList = null;
		String sql = "";
		Cursor result = null;
		db.beginTransaction();
		try{
			dateList = new ArrayList<String>();
			for(int i=0;i<lesson;i++){
				sql = "select last_visit from vocabulary WHERE ID BETWEEN " + (i*100+1) + " AND " + (i*100+100) + " ORDER BY last_visit desc LIMIT 0,1";
				result =db.rawQuery(sql,null);
				for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
					dateList.add(result.getString(result.getColumnIndex("last_visit")));
				}
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
			}
			
			db.endTransaction();
		}
		return dateList;
	}
	
	/**
	 * ��ݿγ̺Ų�ѯÿ���е�һ�����ʵĵ��ʣ������Լ�����
	 */
	public List<Map<String,String>> getAbstractListByLesson(int lesson){
		List<Map<String,String>> abList = null;
		String sql = "";
		Cursor result = null;
		try{
			abList = new ArrayList<Map<String,String>>();
			db.beginTransaction();
			for(int i=0; i<lesson; i++){
			//	sql = "select word,symbols,content,example from vocabulary WHERE ID=" + (i*100+1);
//				sql = "select word,symbols from vocabulary WHERE ID=" + (i*100+1);
				sql = "select word,symbols,content,example from vocabulary WHERE ID=1";
				result =db.rawQuery(sql,null);
				for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
					Map<String,String> mapWordInfo = new HashMap<String,String>();
					mapWordInfo.put("word", result.getString(result.getColumnIndex("word")));
					mapWordInfo.put("symbols", result.getString(result.getColumnIndex("symbols")));
					mapWordInfo.put("content", result.getString(result.getColumnIndex("content")));
					mapWordInfo.put("example", result.getString(result.getColumnIndex("example")));
					abList.add(mapWordInfo); 
				}  
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
			}
			db.endTransaction();
		}
		return abList;
	}
	
	/**
	 * 
	 * @param lesson �γ̺�
	 * @return ÿ���лش���ȷ�ĵ�����
	 */
	public List<Integer> getAccuracyCountByListen(int lesson){
		List<Integer> acList = null;
		String sql = "";
		Cursor result = null;
		try{
			acList = new ArrayList<Integer>();
			db.beginTransaction();
			for(int i=0;i<1;i++){
				sql = "select count(id) as total from vocabulary where id between " + (i*100+1) + " and " + (i*100+100) + " and is_known='true'";
				result = db.rawQuery(sql, null);
				for(result.moveToFirst();!result.isAfterLast();result.moveToNext()){
					acList.add(result.getInt(result.getColumnIndex("total")));
				} 
			} 
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
			}
			db.endTransaction();
		}
		return acList;
	}
	
	/**
	 * 
	 * @return �����������
	 */
	public List<WordInfo> getAllUnknownWords(){
		List<WordInfo> uWordsList = null;
		String sql = null;
		Cursor result = null;
		try{
			uWordsList = new ArrayList<WordInfo>();
			db.beginTransaction();
			sql = "select * from vocabulary where is_known='false'";
			result = db.rawQuery(sql, null);
			for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
				WordInfo uWord = new WordInfo();
System.out.println("uWord-->" + uWord);
				uWord.setId(Integer.parseInt(result.getString(result.getColumnIndex("id"))));
				uWord.setSymbols(result.getString(result.getColumnIndex("symbols")));
				uWord.setContent(result.getString(result.getColumnIndex("content")));
				uWord.setWord(result.getString(result.getColumnIndex("word")));
				uWord.setExample(result.getString(result.getColumnIndex("example")));
				uWordsList.add(uWord);
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
			if(result != null){
				result.close();
			}
			db.close();
		}
		return uWordsList;
	}
	
	/**
	 * ���idֵ�������״̬
	 */
	public void updateUnknownWordStatusById(int id){
		String sql = "";
		try{
			db.beginTransaction(); 
			sql = UPDATE_WORDS + " is_known='true' where id=" + id;
			db.execSQL(sql);
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
			db.close();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ReadingInfo> getAllReadingInfoByDate(int date){
		List<ReadingInfo> readInfoList = null;
		String sql = "";
		Cursor result = null;
		try{
			db.beginTransaction();
			sql = "select * from reading where date=" + date;
			result = db.rawQuery(sql,null);
			readInfoList = new ArrayList<ReadingInfo>();
			for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
				ReadingInfo readingInfo = new ReadingInfo();
				readingInfo.setId(Integer.parseInt(result.getString(result.getColumnIndex("id"))));
				readingInfo.setTitle(result.getString(result.getColumnIndex("title")));
				readingInfo.setDate(Integer.parseInt(result.getString(result.getColumnIndex("date"))));
				readingInfo.setContent(result.getString(result.getColumnIndex("content")));
				readingInfo.setAnswer(result.getString(result.getColumnIndex("answer")));
				readInfoList.add(readingInfo);
			} 
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
			}
			db.endTransaction();
		}
		return readInfoList;
	}
	
	/**
	 * 
	 * ����д����Ϣ
	 * @return
	 */
	public List<WrittingInfo> getAllWrittingInfoByDate(){
		List<WrittingInfo> writtingInfoList = null;
		String sql = ""; 
		Cursor result = null;
		try{
			db.beginTransaction();
			sql = "select * from writting";
			result = db.rawQuery(sql,null);  
			writtingInfoList = new ArrayList<WrittingInfo>();
			for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
				WrittingInfo writtingInfo = new WrittingInfo();
				writtingInfo.setId(Integer.parseInt(result.getString(result.getColumnIndex("id"))));
				writtingInfo.setQuestion(result.getString(result.getColumnIndex("question")));
				writtingInfo.setDate(result.getString(result.getColumnIndex("date")));
				writtingInfo.setHaveImage(result.getString(result.getColumnIndex("have_image")));
				writtingInfo.setAnswer(result.getString(result.getColumnIndex("answer")));
				writtingInfo.setImagePath(result.getString(result.getColumnIndex("image_path")));
				writtingInfo.setTitle(result.getString(result.getColumnIndex("title"))); 
				writtingInfoList.add(writtingInfo);
			}
			db.setTransactionSuccessful(); 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
			}
			db.endTransaction();
		}
		return writtingInfoList;
	}

	public List<WordInfo> getSearchResult(String keyWord) {
		List<WordInfo> wordInfos = null;
		String sql = "";
		Cursor result1 = null;
		Cursor result2 = null;
		Cursor result3 = null;
		boolean isFoundInWord = true;
		boolean isFoundInContent = true;
		try{ 
			wordInfos = new ArrayList<WordInfo>();
			db.beginTransaction();
			sql = "select * from vocabulary where word='" + keyWord + "'"; 
			result1 = db.rawQuery(sql, null); 
			for(result1.moveToFirst();!result1.isAfterLast();result1.moveToNext()){
				WordInfo wordInfo = new WordInfo();
				wordInfo.setId(result1.getInt(result1.getColumnIndex("id")));
				wordInfo.setWord(result1.getString(result1.getColumnIndex("word")));
				wordInfo.setSymbols(result1.getString(result1.getColumnIndex("symbols")));
				wordInfo.setContent(result1.getString(result1.getColumnIndex("content")));
				wordInfo.setExample(Html.fromHtml(result1.getString(result1.getColumnIndex("example"))).toString());
				wordInfos.add(wordInfo);
				isFoundInWord = false; 
				isFoundInContent = false;
			}
			if(isFoundInWord){//����ģ���ѯ
				sql = "select * from vocabulary where word like '" + keyWord + "%'";
				result2 = db.rawQuery(sql, null);
				for(result2.moveToFirst(); !result2.isAfterLast(); result2.moveToNext()){
					WordInfo wordInfo = new WordInfo();
					wordInfo.setId(result2.getInt(result2.getColumnIndex("id")));
					wordInfo.setWord(result2.getString(result2.getColumnIndex("word")));
					wordInfo.setSymbols(result2.getString(result2.getColumnIndex("symbols")));
					wordInfo.setContent(result2.getString(result2.getColumnIndex("content")));
					wordInfo.setExample(result2.getString(result2.getColumnIndex("example")));
					wordInfos.add(wordInfo);
					isFoundInContent = false;
					
				} 
			}
			if(isFoundInContent){//���ҵ�����������
				sql = "select * from vocabulary where content like '%" + keyWord + "%'";
				result3 = db.rawQuery(sql, null);
				for(result3.moveToFirst(); !result3.isAfterLast(); result3.moveToNext()){
					WordInfo wordInfo = new WordInfo();
					wordInfo.setId(result3.getInt(result3.getColumnIndex("id")));
					wordInfo.setWord(result3.getString(result3.getColumnIndex("word")));
					wordInfo.setSymbols(result3.getString(result3.getColumnIndex("symbols")));
					wordInfo.setContent(result3.getString(result3.getColumnIndex("content")));
					wordInfo.setExample(result3.getString(result3.getColumnIndex("example")));
					wordInfos.add(wordInfo);
				}
			}
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result1 != null){
				result1.close();
				result1 = null;
			}
			if(result2 != null){
				result2.close();
				result2 = null;
			}
			if(result3 != null){
				result3.close();
				result3 = null;
			}
			db.endTransaction();
		}
		return wordInfos;
	}
	
	
	
	public List<Map<String,String>> test(int index){
		List<Map<String,String>> abList = null;
		String sql = "";
		Cursor result = null;
		try{
			abList = new ArrayList<Map<String,String>>();
			db.beginTransaction(); 
				sql = "select word,symbols,content,example from vocabulary WHERE ID=" + (index*100+1);
				result =db.rawQuery(sql,null);
				for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
					Map<String,String> mapWordInfo = new HashMap<String,String>();
					mapWordInfo.put("word", result.getString(result.getColumnIndex("word")));
					mapWordInfo.put("symbols", result.getString(result.getColumnIndex("symbols")));
					mapWordInfo.put("content", result.getString(result.getColumnIndex("content")));
					mapWordInfo.put("example", result.getString(result.getColumnIndex("example")));
					abList.add(mapWordInfo); 
				}  
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
			}
			db.endTransaction();
		}
		return abList;
	}
	
	public Integer test2(int index){
		String sql = "";
		Cursor result = null;
		int acCount = 0;
		try{
			db.beginTransaction();
				sql = "select count(id) as total from vocabulary where id between " + (index*100+1) + " and " + (index*100+100) + " and is_known='true'";
				result = db.rawQuery(sql, null);
				result.moveToFirst();
				if(!result.isAfterLast()){
					acCount = result.getInt(result.getColumnIndex("total"));
					result.moveToNext();
				}  
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(result != null){
				result.close();
			}
			db.endTransaction();
		}
		return acCount;
	}
	
}	
