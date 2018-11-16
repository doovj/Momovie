package com.doovj.favoritq;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doovj.favoritq.entity.Movie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    ImageView ivposter, ivbackdrop;
    TextView tvjudul, tvdeskripsi, tvtanggalrilis, tvrating;

    String judul, deskripsi, tanggal_rilis, poster_path, backdrop_path;
    Integer vote_count, id;
    Double rating;

    private Movie movie = null;

    private static final String URL_POSTER = "http://image.tmdb.org/t/p/w342";
    private static final String URL_BACKDROP = "http://image.tmdb.org/t/p/w780";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvjudul = (TextView)findViewById(R.id.tv_judul);
        tvdeskripsi = (TextView)findViewById(R.id.tv_deskripsi);
        tvtanggalrilis = (TextView)findViewById(R.id.tv_tanggal_rilis);
        tvrating = (TextView)findViewById(R.id.tv_rating);
        ivbackdrop = (ImageView)findViewById(R.id.ivbackdrop);
        ivposter = (ImageView)findViewById(R.id.ivposterfilm);

        final Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null){

                if(cursor.moveToFirst()) movie = new Movie(cursor);
                cursor.close();
            }
        }

        id = movie.getId();
        judul = movie.getNama();
        deskripsi = movie.getDeskripsi();
        tanggal_rilis = movie.getTanggalrilis();
        rating = movie.getRating();
        vote_count = movie.getVote_count();
        poster_path = movie.getPoster();
        backdrop_path = movie.getBackdrop();

        tvjudul.setText(judul);
        tvdeskripsi.setText(deskripsi);
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
            DateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            Date date = inputFormat.parse(tanggal_rilis);
            String dateRilis = outputFormat.format(date);
            tvtanggalrilis.setText(dateRilis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String rate = String.valueOf(rating);
        String vote = String.valueOf(vote_count);

        String penilaian = rate + " " + getResources().getString(R.string.from) + " " + vote + " " + getResources().getString(R.string.voters);
        tvrating.setText(penilaian);
        Glide.with(this).load(URL_POSTER + poster_path).into(ivposter);
        Glide.with(this).load(URL_BACKDROP + backdrop_path).into(ivbackdrop);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
