package org.rob.diary_257779.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {
	
	private SQLiteDatabase database;
	private DataBaseHelper dbHelper;
	
	public DataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}
	
	public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	}

	public void close() {
	    dbHelper.close();
	}
	
	//TODO
	//Diary createDiary()
	//void deleteDiary(Diary diary)
	//List<Diary> getAllDiaries()
	
	//TODO
	//Calendar createDate()
	//void deleteDate(Calendar date)
	//List<Calendar> getAllDates()
	
	//TODO
	//Entry createEntry()
	//void deleteEntry(Entry entry)
	//List<Entry> getAllEntries()
}
