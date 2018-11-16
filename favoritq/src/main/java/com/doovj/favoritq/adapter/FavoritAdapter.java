package com.doovj.favoritq.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doovj.favoritq.R;

import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.POSTER;
import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.TANGGALRILIS;
import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.TITLE;
import static com.doovj.favoritq.db.DatabaseContract.getColumnString;

public class FavoritAdapter extends CursorAdapter {
    private static final String URL_POSTER = "http://image.tmdb.org/t/p/w342";

    public FavoritAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_fav, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            ImageView poster = (ImageView)view.findViewById(R.id.img_poster_film);
            TextView title = (TextView)view.findViewById(R.id.tv_movie_name);
            TextView deskripsi = (TextView)view.findViewById(R.id.tv_movie_deskripsi);
            TextView tanggal = (TextView)view.findViewById(R.id.tv_tanggal_rilis);

            Glide.with(context).load(URL_POSTER + getColumnString(cursor, POSTER)).into(poster);
            title.setText(getColumnString(cursor, TITLE));
            deskripsi.setText(getColumnString(cursor, DESKRIPSI));
            tanggal.setText(getColumnString(cursor, TANGGALRILIS));
        }
    }
}
