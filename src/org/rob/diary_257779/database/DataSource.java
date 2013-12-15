package org.rob.diary_257779.database;

import java.util.ArrayList;
import java.util.List;

import org.rob.diary_257779.classes.Diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

	private SQLiteDatabase database;
	private DataBaseHelper dbHelper;
	private String[] allDiaryColumns = { DataBaseContract.DiaryTable._ID,
			DataBaseContract.DiaryTable.NAME };

	public DataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	// Methods about Diary

	public Diary createDiary(String name) {
		ContentValues values = new ContentValues();
		values.put(DataBaseContract.DiaryTable.NAME, name);
		long insertId = database.insert(DataBaseContract.DiaryTable.NAME, null,
				values);
		Cursor cursor = database.query(DataBaseContract.DiaryTable.NAME,
				allDiaryColumns, DataBaseContract.DiaryTable._ID + " = "
						+ insertId, null, null, null, null);
		cursor.moveToFirst();
		Diary newDiary = cursorToDiary(cursor);
		cursor.close();
		return newDiary;

	}

	public void deleteDiary(Diary diary) {
		long id = diary.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(DataBaseContract.DiaryTable.TABLE_NAME, DataBaseContract.DiaryTable._ID
	        + " = " + id, null);
	}
	
	public List<Diary> getAllDiaries() {
		List<Diary> diaries = new ArrayList<Diary>();
	    Cursor cursor = database.query(DataBaseContract.DiaryTable.TABLE_NAME,
	        allDiaryColumns, null, null, null, null, null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Diary diary = cursorToDiary(cursor);
	      diaries.add(diary);
	      cursor.moveToNext();
	    }
	    cursor.close();
	    return diaries;
	}

	private Diary cursorToDiary(Cursor cursor) {
		return new Diary(cursor.getLong(0), cursor.getString(1));
	}

	// Methods about Calendar
	
	// TODO
	// Calendar createDate()
	// void deleteDate(Calendar date)
	// List<Calendar> getAllDates()

	// TODO
	// Entry createEntry()
	// void deleteEntry(Entry entry)
	// List<Entry> getAllEntries()
}
