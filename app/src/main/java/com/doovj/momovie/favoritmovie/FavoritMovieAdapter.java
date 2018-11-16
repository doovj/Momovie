package com.doovj.momovie.favoritmovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doovj.momovie.CustomOnItemClickListener;
import com.doovj.momovie.DetailMovieActivity;
import com.doovj.momovie.MovieItems;
import com.doovj.momovie.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.doovj.momovie.db.DatabaseContract.CONTENT_URI;

public class FavoritMovieAdapter extends RecyclerView.Adapter<FavoritMovieAdapter.FavoritMovieHolder> {
    private Cursor listMovies;
    private Context context;
    private static final String URL_POSTER = "http://image.tmdb.org/t/p/w342";

    public FavoritMovieAdapter(Context context) {
        this.context = context;
    }

    public void setListMovies(Cursor listMovies) {
        this.listMovies = listMovies;
    }

    @Override
    public FavoritMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_movie, parent, false);
        return new FavoritMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoritMovieHolder holder, int position) {
        final MovieItems movieItems = getItem(position);

        Glide.with(context)
                .load(URL_POSTER + movieItems.getPoster())
                .into(holder.imgPoster);
        holder.tvJudul.setText(movieItems.getNama());
        holder.tvDeskripsi.setText(movieItems.getDeskripsi());
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
            DateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String tanggalrilis = movieItems.getTanggalrilis();
            Date date = inputFormat.parse(tanggalrilis);
            String dateRilis = outputFormat.format(date);
            holder.tvTanggalrilis.setText(dateRilis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, DetailMovieActivity.class);

                Uri uri = Uri.parse(CONTENT_URI + "/" + movieItems.getId_favorit());
                intent.setData(uri);
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (listMovies == null) return 0;
        return listMovies.getCount();
    }

    private MovieItems getItem(int position) {
        if (!listMovies.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItems(listMovies);
    }

    class FavoritMovieHolder extends RecyclerView.ViewHolder{
        ImageView imgPoster;
        TextView tvJudul, tvDeskripsi, tvTanggalrilis, tvid;
        Button btnDetail, btnShare;
        FavoritMovieHolder(View itemView) {
            super(itemView);
            imgPoster = (ImageView)itemView.findViewById(R.id.img_poster_film);
            tvJudul = (TextView)itemView.findViewById(R.id.tv_movie_name);
            tvDeskripsi = (TextView)itemView.findViewById(R.id.tv_movie_deskripsi);
            tvTanggalrilis = (TextView)itemView.findViewById(R.id.tv_tanggal_rilis);
            btnDetail = (Button)itemView.findViewById(R.id.btn_set_detail);
            btnShare = (Button)itemView.findViewById(R.id.btn_set_share);
        }
    }
}
