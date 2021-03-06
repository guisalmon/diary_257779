package org.rob.diary_257779.database;

import java.util.ArrayList;
import java.util.List;

import org.rob.diary_257779.classes.Diary;
import org.rob.diary_257779.classes.DiaryDate;
import org.rob.diary_257779.classes.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class DataSource {

	private SQLiteDatabase database;
	private DataBaseHelper dbHelper;
	private String[] allDiaryColumns = { DataBaseContract.DiaryTable._ID,
			DataBaseContract.DiaryTable.NAME };
	private String[] allDateColumns = { DataBaseContract.CalendarTable._ID,
			DataBaseContract.CalendarTable.DAY,
			DataBaseContract.CalendarTable.MONTH,
			DataBaseContract.CalendarTable.YEAR };
	private String[] allEntryColumns = { DataBaseContract.EntryTable._ID,
			DataBaseContract.EntryTable.TITLE,
			DataBaseContract.EntryTable.DESC, DataBaseContract.EntryTable.DATE,
			DataBaseContract.EntryTable.CUR_DATE,
			DataBaseContract.EntryTable.DIARY, };

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
		long insertId = database.insert(DataBaseContract.DiaryTable.TABLE_NAME,
				null, values);
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
		database.delete(DataBaseContract.DiaryTable.TABLE_NAME,
				DataBaseContract.DiaryTable._ID + " = " + id, null);
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

	public DiaryDate createDate(long day, long month, long year) {
		ContentValues values = new ContentValues();
		values.put(DataBaseContract.CalendarTable.DAY, day);
		values.put(DataBaseContract.CalendarTable.MONTH, month);
		values.put(DataBaseContract.CalendarTable.YEAR, year);
		long insertId = database.insert(
				DataBaseContract.CalendarTable.TABLE_NAME, null, values);
		Cursor cursor = database.query(
				DataBaseContract.CalendarTable.TABLE_NAME, allDateColumns,
				DataBaseContract.CalendarTable._ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		DiaryDate newDiaryDate = cursorToDiaryDate(cursor);
		cursor.close();
		return newDiaryDate;
	}

	public void deleteDate(DiaryDate date) {
		long id = date.getId();
		database.delete(DataBaseContract.CalendarTable.TABLE_NAME,
				DataBaseContract.CalendarTable._ID + " = " + id, null);
	}

	public List<DiaryDate> getAllDates() {
		List<DiaryDate> dates = new ArrayList<DiaryDate>();
		Cursor cursor = database.query(
				DataBaseContract.CalendarTable.TABLE_NAME, allDateColumns,
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			DiaryDate date = cursorToDiaryDate(cursor);
			dates.add(date);
			cursor.moveToNext();
		}
		cursor.close();
		return dates;
	}

	private long getDateId(long day, long month, long year) {
		String[] idColumn = { DataBaseContract.CalendarTable._ID };
		Cursor dateCursor = database.query(
				DataBaseContract.CalendarTable.TABLE_NAME, idColumn,
				DataBaseContract.CalendarTable.DAY + " = " + day + " AND "
						+ DataBaseContract.CalendarTable.MONTH + " = " + month
						+ " AND " + DataBaseContract.CalendarTable.YEAR + " = "
						+ year, null, null, null, null);
		if (dateCursor.moveToFirst() != false) {
			return dateCursor.getLong(0);
		} else {
			DiaryDate date = createDate(day, month, year);
			return date.getId();
		}
	}

	private DiaryDate cursorToDiaryDate(Cursor cursor) {
		return new DiaryDate(cursor.getLong(0), cursor.getLong(1),
				cursor.getLong(2), cursor.getLong(3));
	}

	// Methods about Entry

	public Entry createEntry(String title, String content, long day,
			long month, long year, Diary diary) {
		Time now = new Time();
		now.setToNow();

		ContentValues values = new ContentValues();
		values.put(DataBaseContract.EntryTable.TITLE, title);
		values.put(DataBaseContract.EntryTable.DESC, content);
		values.put(DataBaseContract.EntryTable.DATE,
				getDateId(day, month, year));
		values.put(DataBaseContract.EntryTable.CUR_DATE, title);
		values.put(DataBaseContract.EntryTable.DIARY,
				getDateId(now.monthDay, now.month, now.year));
		long insertId = database.insert(DataBaseContract.EntryTable.TABLE_NAME,
				null, values);
		Cursor cursor = database.query(DataBaseContract.EntryTable.TABLE_NAME,
				allEntryColumns, DataBaseContract.EntryTable._ID + " = "
						+ insertId, null, null, null, null);
		cursor.moveToFirst();
		Entry newEntry = cursorToEntry(cursor);
		cursor.close();
		return newEntry;
	}

	public void deleteEntry(Entry entry) {
		long id = entry.getId();
		database.delete(DataBaseContract.EntryTable.TABLE_NAME,
				DataBaseContract.EntryTable._ID + " = " + id, null);
	}

	public List<Entry> getAllEntries(){
		List<Entry> entries = new ArrayList<Entry>();
		Cursor cursor = database.query(
				DataBaseContract.EntryTable.TABLE_NAME, allEntryColumns,
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Entry entry = cursorToEntry(cursor);
			entries.add(entry);
			cursor.moveToNext();
		}
		cursor.close();
		return entries;
	}

	private Entry cursorToEntry(Cursor cursor) {
		return new Entry(cursor.getLong(0), cursor.getString(1),
				cursor.getString(2), cursor.getLong(3), cursor.getLong(4),
				cursor.getLong(5));
	}
}
