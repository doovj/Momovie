package com.doovj.momovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doovj.momovie.MovieItems;
import com.doovj.momovie.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieAdapter extends BaseAdapter {
    private ArrayList<MovieItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private static final String URL_POSTER = "http://image.tmdb.org/t/p/w185";

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItems> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public MovieItems getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_movie, null);
            holder.ivPoster = (ImageView)convertView.findViewById(R.id.poster_film);
            holder.tvNamaFilm = (TextView)convertView.findViewById(R.id.tv_nama_film);
            holder.tvDeskripsi = (TextView)convertView.findViewById(R.id.tv_deskripsi);
            holder.tvTanggalRilis = (TextView)convertView.findViewById(R.id.tv_tanggal_rilis);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(this.context).load(URL_POSTER + mData.get(position).getPoster()).into(holder.ivPoster);
        holder.tvNamaFilm.setText(mData.get(position).getNama());
        holder.tvDeskripsi.setText(mData.get(position).getDeskripsi());
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String tanggalrilis = mData.get(position).getTanggalrilis();
            Date date = inputFormat.parse(tanggalrilis);
            String dateRilis = outputFormat.format(date);
            holder.tvTanggalRilis.setText(dateRilis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView ivPoster;
        TextView tvNamaFilm;
        TextView tvDeskripsi;
        TextView tvTanggalRilis;
    }
}
