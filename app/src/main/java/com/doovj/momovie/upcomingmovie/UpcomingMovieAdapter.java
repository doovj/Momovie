package com.doovj.momovie.upcomingmovie;

import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.Date;

public class UpcomingMovieAdapter extends RecyclerView.Adapter<UpcomingMovieAdapter.UpcomingMovieHolder> {
    private ArrayList<MovieItems> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    private static final String URL_POSTER = "http://image.tmdb.org/t/p/w342";

    UpcomingMovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItems> items) {
        mData = items;
        notifyDataSetChanged();
    }

    @Override
    public UpcomingMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_movie, parent, false);
        return new UpcomingMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(UpcomingMovieHolder holder, int position) {

        Glide.with(context)
                .load(URL_POSTER + mData.get(position).getPoster())
                .into(holder.imgPoster);
        holder.tvJudul.setText(mData.get(position).getNama());
        holder.tvDeskripsi.setText(mData.get(position).getDeskripsi());
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String tanggalrilis = mData.get(position).getTanggalrilis();
            Date date = inputFormat.parse(tanggalrilis);
            String dateRilis = outputFormat.format(date);
            holder.tvTanggalrilis.setText(dateRilis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                Intent cvintent = new Intent(context, DetailMovieActivity.class);

                cvintent.putExtra(DetailMovieActivity.EXTRA_ID, mData.get(position).getId());
                cvintent.putExtra(DetailMovieActivity.EXTRA_JUDUL, mData.get(position).getNama());
                cvintent.putExtra(DetailMovieActivity.EXTRA_DESKRIPSI, mData.get(position).getDeskripsi());
                cvintent.putExtra(DetailMovieActivity.EXTRA_TANGGAL_RILIS, mData.get(position).getTanggalrilis());
                cvintent.putExtra(DetailMovieActivity.EXTRA_RATING, mData.get(position).getRating());
                cvintent.putExtra(DetailMovieActivity.EXTRA_VOTE_COUNT, mData.get(position).getVote_count());
                cvintent.putExtra(DetailMovieActivity.EXTRA_POSTER, mData.get(position).getPoster());
                cvintent.putExtra(DetailMovieActivity.EXTRA_BACKDROP, mData.get(position).getBackdrop());

                context.startActivity(cvintent);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class UpcomingMovieHolder extends RecyclerView.ViewHolder{
        ImageView imgPoster;
        TextView tvJudul, tvDeskripsi, tvTanggalrilis;
        Button btnDetail, btnShare;
        UpcomingMovieHolder(View itemView) {
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
