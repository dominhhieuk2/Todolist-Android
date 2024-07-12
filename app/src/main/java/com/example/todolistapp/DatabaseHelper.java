package com.example.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TodoList.db";
    private static final String TABLE_NAME = "tasks";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TITLE";
    private static final String COL_3 = "CONTENT";
    private static final String COL_4 = "DATE";
    private static final String COL_5 = "TYPE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, CONTENT TEXT, DATE TEXT, TYPE TEXT)");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (TITLE, CONTENT, DATE, TYPE) VALUES ('Học Java', 'Học Java cơ bản', '27/2/2023', 'Dễ')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (TITLE, CONTENT, DATE, TYPE) VALUES ('Học React Native', 'Học React Native cơ bản', '24/3/2023', 'Trung bình')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (TITLE, CONTENT, DATE, TYPE) VALUES ('Học Kotlin', 'Học Kotlin cơ bản', '1/4/2023', 'Khó')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY TITLE", null);
    }

    public boolean insertData(String title, String content, String date, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, content);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, type);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean updateData(String id, String title, String content, String date, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, content);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, type);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
