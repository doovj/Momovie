package com.doovj.momovie.favoritmovie;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doovj.momovie.R;

import static com.doovj.momovie.db.DatabaseContract.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritFragment extends Fragment {
    private RecyclerView rvMovies;
    private FavoritMovieAdapter adapter;
    private Cursor list;


    public FavoritFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorit, container, false);
        rvMovies = (RecyclerView)v.findViewById(R.id.rv_movie_now);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavoritMovieAdapter(getActivity());
        adapter.setListMovies(list);
        rvMovies.setAdapter(adapter);

        // new LoadMovieAsync().execute();

        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);
        if (isVisible) {
            // new LoadMovieAsync().execute();
            // adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync().execute();
        adapter.notifyDataSetChanged();
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);

            list = movies;
            adapter.setListMovies(list);
            adapter.notifyDataSetChanged();
        }
    }
}
