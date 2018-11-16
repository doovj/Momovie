package com.doovj.momovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovie";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORIT = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s INTEGER NOT NULL UNIQUE," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s REAL NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL)",
            DatabaseContract.TABLE_FAVORIT,
            _ID,
            DatabaseContract.MovieColumns.ID,
            DatabaseContract.MovieColumns.POSTER,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.DESKRIPSI,
            DatabaseContract.MovieColumns.TANGGALRILIS,
            DatabaseContract.MovieColumns.RATING,
            DatabaseContract.MovieColumns.BACKDROP,
            DatabaseContract.MovieColumns.VOTECOUNT
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAVORIT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORIT);
        onCreate(sqLiteDatabase);
    }
}
