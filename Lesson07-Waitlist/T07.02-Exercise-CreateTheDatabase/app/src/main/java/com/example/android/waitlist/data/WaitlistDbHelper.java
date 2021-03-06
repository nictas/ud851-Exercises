package com.example.android.waitlist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.waitlist.data.WaitlistContract.WaitlistEntry;

public class WaitlistDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "waitlist.db";
    private static final int DATABASE_VERSION = 1;

    public WaitlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateWaitlistTable = "CREATE TABLE " + WaitlistEntry.TABLE_NAME + " ("
                + WaitlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WaitlistEntry.COLUMN_GUEST_NAME + " TEXT NOT NULL,"
                + WaitlistEntry.COLUMN_PARTY_SIZE + " INTEGER NOT NULL,"
                + WaitlistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";
        db.execSQL(sqlCreateWaitlistTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlDropWaitlistTable = "DROP TABLE IF EXISTS " + WaitlistEntry.TABLE_NAME + ";";
        db.execSQL(sqlDropWaitlistTable);
        onCreate(db);
    }

}