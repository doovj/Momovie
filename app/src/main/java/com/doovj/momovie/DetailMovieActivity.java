package com.doovj.momovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doovj.momovie.helper.MovieHelper;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.doovj.momovie.db.DatabaseContract.CONTENT_URI;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.BACKDROP;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.ID;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.POSTER;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.RATING;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.TANGGALRILIS;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.TITLE;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.VOTECOUNT;

public class DetailMovieActivity extends AppCompatActivity {

    ImageView ivposter, ivbackdrop;
    TextView tvjudul, tvdeskripsi, tvtanggalrilis, tvrating;

    String judul, deskripsi, tanggal_rilis, poster_path, backdrop_path, idku;
    Integer vote_count, id;
    Double rating;
    MaterialFavoriteButton favoritku;

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_JUDUL = "EXTRA_JUDUL";
    public static final String EXTRA_DESKRIPSI = "EXTRA DESKRIPSI";
    public static final String EXTRA_TANGGAL_RILIS = "EXTRA_TANGGAL_RILIS";
    public static final String EXTRA_POSTER = "EXTRA POSTER";
    public static final String EXTRA_BACKDROP = "EXTRA_BACKDROP";
    public static final String EXTRA_RATING = "EXTRA_RATING";
    public static final String EXTRA_VOTE_COUNT = "EXTRA_VOTE_COUNT";

    private static final String URL_POSTER = "http://image.tmdb.org/t/p/w342";
    private static final String URL_BACKDROP = "http://image.tmdb.org/t/p/w780";

    private MovieHelper movieHelper;
    private MovieItems movieItems;
    Cursor cursor;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvjudul = (TextView)findViewById(R.id.tv_judul);
        tvdeskripsi = (TextView)findViewById(R.id.tv_deskripsi);
        tvtanggalrilis = (TextView)findViewById(R.id.tv_tanggal_rilis);
        tvrating = (TextView)findViewById(R.id.tv_rating);
        ivbackdrop = (ImageView)findViewById(R.id.ivbackdrop);
        ivposter = (ImageView)findViewById(R.id.ivposterfilm);
        favoritku = (MaterialFavoriteButton)findViewById(R.id.fav);

        movieHelper = new MovieHelper(this);
        movieHelper.open();


        final Uri uri = getIntent().getData();
        if (uri != null) {
            cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) movieItems = new MovieItems(cursor);
                cursor.close();
            }

            id = movieItems.getId();
            judul = movieItems.getNama();
            deskripsi = movieItems.getDeskripsi();
            tanggal_rilis = movieItems.getTanggalrilis();
            rating = movieItems.getRating();
            vote_count = movieItems.getVote_count();
            poster_path = movieItems.getPoster();
            backdrop_path = movieItems.getBackdrop();

        } else {
            id = getIntent().getIntExtra(EXTRA_ID, 0);
            judul = getIntent().getStringExtra(EXTRA_JUDUL);
            deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);
            tanggal_rilis = getIntent().getStringExtra(EXTRA_TANGGAL_RILIS);
            rating = getIntent().getDoubleExtra(EXTRA_RATING, 0);
            vote_count = getIntent().getIntExtra(EXTRA_VOTE_COUNT, 0);
            poster_path = getIntent().getStringExtra(EXTRA_POSTER);
            backdrop_path = getIntent().getStringExtra(EXTRA_BACKDROP);
        }

        idku = String.valueOf(id);

        boolean check = movieHelper.checkAlreadyExist(idku);
        if (check) {
            favoritku.setFavorite(true);
        } else
            favoritku.setFavorite(false);

        tvjudul.setText(judul);
        tvdeskripsi.setText(deskripsi);
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        Glide.with(DetailMovieActivity.this).load(URL_POSTER + poster_path).into(ivposter);
        Glide.with(DetailMovieActivity.this).load(URL_BACKDROP + backdrop_path).into(ivbackdrop);

        favoritku.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if (favorite) {
                    movieHelper.open();

                    ContentValues values = new ContentValues();
                    values.put(ID, id);
                    values.put(TITLE,judul);
                    values.put(DESKRIPSI, deskripsi);
                    values.put(TANGGALRILIS, tanggal_rilis);
                    values.put(RATING, rating);
                    values.put(VOTECOUNT, vote_count);
                    values.put(POSTER, poster_path);
                    values.put(BACKDROP, backdrop_path);

                    getContentResolver().insert(CONTENT_URI, values);

                    movieHelper.close();

                    UpdateBroadcast(context);
                } else {
                    movieHelper.open();

                    if (uri != null) {
                        getContentResolver().delete(getIntent().getData(), null, null);
                    } else {
                        movieHelper.delete(id);
                    }

                    movieHelper.close();

                    // UpdateBroadcast(context);
                }
            }
        });

        movieHelper.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void UpdateBroadcast(Context context)
    {
        Intent i = new Intent(context, WidgetFavorit.class);
        i.setAction(WidgetFavorit.UPDATE_WIDGET);
        context.sendBroadcast(i);
    }
}
