package com.doovj.momovie;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    ListView listView;
    MovieAdapter adapter;
    EditText etcarifilm;
    Button btncari;

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new MovieAdapter(this);
        adapter.notifyDataSetChanged();
        listView = (ListView)findViewById(R.id.lv_movie);

        listView.setAdapter(adapter);

        etcarifilm = (EditText)findViewById(R.id.et_cari);
        btncari = (Button)findViewById(R.id.btn_cari);

        btncari.setOnClickListener(myListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItems movieitem = (MovieItems)parent.getItemAtPosition(position);

                Intent listintent = new Intent(MainActivity.this, DetailMovieActivity.class);

                listintent.putExtra(DetailMovieActivity.EXTRA_ID, movieitem.getId());
                listintent.putExtra(DetailMovieActivity.EXTRA_JUDUL, movieitem.getNama());
                listintent.putExtra(DetailMovieActivity.EXTRA_DESKRIPSI, movieitem.getDeskripsi());
                listintent.putExtra(DetailMovieActivity.EXTRA_TANGGAL_RILIS, movieitem.getTanggalrilis());
                listintent.putExtra(DetailMovieActivity.EXTRA_RATING, movieitem.getRating());
                listintent.putExtra(DetailMovieActivity.EXTRA_VOTE_COUNT, movieitem.getVote_count());
                listintent.putExtra(DetailMovieActivity.EXTRA_POSTER, movieitem.getPoster());
                listintent.putExtra(DetailMovieActivity.EXTRA_BACKDROP, movieitem.getBackdrop());

                startActivity(listintent);
            }
        });

        String movie = etcarifilm.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, movie);

        getSupportLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String pencarian = "";
        if (args != null) {
            pencarian = args.getString(EXTRAS_MOVIE);
        }
        return new MovieAsyncTaskLoader(this, pencarian);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String movie = etcarifilm.getText().toString();
            if (TextUtils.isEmpty(movie))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, movie);
            getSupportLoaderManager().restartLoader(0, bundle, MainActivity.this);
        }
    };

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
}
