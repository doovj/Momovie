package com.doovj.momovie.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.doovj.momovie.db.DatabaseHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.ID;
import static com.doovj.momovie.db.DatabaseContract.TABLE_FAVORIT;

public class MovieHelper {
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        database = databaseHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public int delete(int id){
        return database.delete(TABLE_FAVORIT, ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(TABLE_FAVORIT, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(TABLE_FAVORIT
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(TABLE_FAVORIT, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(TABLE_FAVORIT, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(TABLE_FAVORIT, _ID + " = ?", new String[]{id});
    }

    public boolean checkAlreadyExist(String id) {
        String query = "SELECT * FROM " + TABLE_FAVORIT + " WHERE " + ID + " =?";
        open();
        Cursor cursor = database.rawQuery(query, new String[]{id});
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        close();
        return true;
    }
}
