package org.rob.diary_257779.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter {
	
	static final String DIARY = "diary";
	static final String ID = "id";
	static final String TITLE = "title";
	
	private SQLiteDatabase db;
	private Context context;
	private BaseDBOpenHelper dbHelper;
	
	public DatabaseAdapter(Context context) {
		this.context = context;
		dbHelper = new BaseDBOpenHelper(this.context);
	}
	
	public void close() {
		db.close();     
	}
	
	public void open() throws SQLiteException {   
		try {   
			db = dbHelper.getWritableDatabase();            
		}catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase();       
		}
	}
	
	public long insertDiary(String title) {
		ContentValues diary = new ContentValues();  
		diary.put(TITLE, title);
		return db.insert(DIARY, null, diary);   
	}
	
	public void updateDiary(long id, String title) {
		ContentValues diary = new ContentValues();
		diary.put(TITLE, title);
		db.update(DIARY, diary, ID+"="+id, null);
	}
	
	public void deleteDiary(long id) {
		db.delete(DIARY, ID+"="+id, null);
	}
	
	public void deleteAllFromTable(String table) {
		db.delete(table, null, null);
	}
	
	public void setupDiary(String[] title){
		for(int i=0; i < title.length; i++){
			insertDiary(title[i]);    
		}
	}
	
	public int countRows(String table){    
		Cursor cursor = db.query(table,  null, null, null, null, null, null);      
		return cursor.getCount();
	}
	
	public int getId(String table, String signature){      
		int id=0;
		Cursor cursor = db.query(table,  null, "signature=?", new String[]{signature}, null, null, null);
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			id = cursor.getInt(cursor.getColumnIndex(ID));  
		}
		return id;
	}
	
	public Cursor generalWhereStatemet(String table, String column, int value){
		Cursor cursor = db.query(table,  null, column+"=?", new String[]{Integer.toString(value)}, null, null, null);      
		return cursor;
	}
	
	public ArrayList<String[]> get_all_from_table(String table){
		ArrayList<String[]>rows = new ArrayList<String[]>();    
		Cursor cursor = db.query(table,  null, null, null, null, null, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				do{
					String [] content = new String[2];
					content[0] = Integer.toString(cursor.getInt(cursor.getColumnIndex(ID)));
					content[1] = cursor.getString(cursor.getColumnIndex(TITLE));
					rows.add(content);
				}while (cursor.moveToNext());
			}
		}
		return rows;
	}
	
	public int getDataId(String table){      
		int id=-1;
		Cursor cursor = db.query(table,  null, null, null, null, null, null);
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			id = cursor.getInt(cursor.getColumnIndex("_id"));  
		}      
		return id;
	}
	
	public Cursor getSingleRecordFromTable(String table, String id, int p){    
		Cursor cursor = db.query(table,  null, null, null, null, null, null);
		cursor.moveToPosition(p);
		return cursor;
	}
	
	static class BaseDBOpenHelper extends SQLiteOpenHelper {
		
		public BaseDBOpenHelper(Context context) {
			super(context, "diaries.db", null, 1); 
		}
		
		private static final String CREATE_DIARY = "create table " +DIARY+
				" (" +ID+" integer primary key autoincrement, "+TITLE+
				" text null); ";
           
		@Override
		public void onCreate(SQLiteDatabase db) {  
			db.execSQL(CREATE_DIARY);      
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " +DIARY);    
			onCreate(db);
		}
	}
}
