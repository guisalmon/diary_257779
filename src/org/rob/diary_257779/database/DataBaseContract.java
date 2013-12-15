package org.rob.diary_257779.database;

import android.provider.BaseColumns;

public final class DataBaseContract {

    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "database.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String INTEGER_TYPE       = " INTEGER";
    private static final String COMMA_SEP          = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DataBaseContract() {}

    public static abstract class DiaryTable implements BaseColumns {
        public static final String TABLE_NAME       = "diary";
        public static final String NAME = "diary_name";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                NAME + TEXT_TYPE + COMMA_SEP + " )";
        
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
    public static abstract class CalendarTable implements BaseColumns {
        public static final String TABLE_NAME = "calendar";
        public static final String DAY = "day";
        public static final String MONTH = "month";
        public static final String YEAR = "year";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                DAY + INTEGER_TYPE + COMMA_SEP +
                MONTH + INTEGER_TYPE + COMMA_SEP +
                YEAR + INTEGER_TYPE + COMMA_SEP + " )";
        
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
    public static abstract class EntryTable implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String TITLE = "entry_title";
        public static final String DESC = "entry_desc";
        public static final String DATE = "entry_date";
        public static final String CUR_DATE = "entry_creation_date";
        public static final String DIARY = "entry_diary";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                TITLE + TEXT_TYPE + COMMA_SEP +
                DESC + TEXT_TYPE + COMMA_SEP +
                DATE + INTEGER_TYPE + COMMA_SEP +
                CUR_DATE + INTEGER_TYPE + COMMA_SEP +
                DIARY + INTEGER_TYPE + COMMA_SEP +
                " FOREIGN KEY (" + DATE + ") REFERENCES " + CalendarTable.TABLE_NAME + " (" + CalendarTable._ID + ")" +
                " FOREIGN KEY (" + CUR_DATE + ") REFERENCES " + CalendarTable.TABLE_NAME + " (" + CalendarTable._ID + ")" +
                " FOREIGN KEY (" + DIARY + ") REFERENCES " + DiaryTable.TABLE_NAME + " (" + DiaryTable._ID + ")" +
                " )";
        
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
