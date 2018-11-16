package com.doovj.favoritq.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAVORIT = "favorit";

    public static final class MovieColumns implements BaseColumns {
        public static String ID = "id";
        public static String POSTER = "poster_path";
        public static String TITLE = "title";
        public static String DESKRIPSI = "overview";
        public static String TANGGALRILIS = "release_date";
        public static String RATING = "vote_average";
        public static String BACKDROP = "backdrop_path";
        public static String VOTECOUNT = "vote_count";
    }

    public static final String AUTHORITY = "com.doovj.momovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORIT)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }
}