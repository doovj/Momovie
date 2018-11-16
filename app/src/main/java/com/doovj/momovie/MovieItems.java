package com.doovj.momovie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.BACKDROP;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.ID;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.POSTER;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.RATING;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.TANGGALRILIS;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.TITLE;
import static com.doovj.momovie.db.DatabaseContract.MovieColumns.VOTECOUNT;
import static com.doovj.momovie.db.DatabaseContract.getColumnDouble;
import static com.doovj.momovie.db.DatabaseContract.getColumnInt;
import static com.doovj.momovie.db.DatabaseContract.getColumnString;

public class MovieItems implements Parcelable {
    private int id_favorit;
    private int id;
    private String nama;
    private String deskripsi;
    private String tanggalrilis;
    private String poster;
    private Double rating;
    private String backdrop;
    private int vote_count;

    public MovieItems (JSONObject object) {
        try {
            int id = object.getInt("id");
            String poster = object.getString("poster_path");
            String nama = object.getString("title");
            String deskripsi = object.getString("overview");
            String tanggalrilis = object.getString("release_date");
            Double rating = object.getDouble("vote_average");
            String backdrop = object.getString("backdrop_path");
            int vote_count = object.getInt("vote_count");

            this.id = id;
            this.poster = poster;
            this.nama = nama;
            this.deskripsi = deskripsi;
            this.tanggalrilis = tanggalrilis;
            this.rating = rating;
            this.backdrop = backdrop;
            this.vote_count = vote_count;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_favorit() {
        return id_favorit;
    }

    public void setId_favorit(int id_favorit) {
        this.id_favorit = id_favorit;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggalrilis() {
        return tanggalrilis;
    }

    public void setTanggalrilis(String tanggalrilis) {
        this.tanggalrilis = tanggalrilis;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }


    public MovieItems() {

    }

    public MovieItems(Cursor cursor) {
        this.id_favorit = getColumnInt(cursor, _ID);
        this.id = getColumnInt(cursor, ID);
        this.nama = getColumnString(cursor, TITLE);
        this.deskripsi = getColumnString(cursor, DESKRIPSI);
        this.tanggalrilis = getColumnString(cursor, TANGGALRILIS);
        this.poster = getColumnString(cursor, POSTER);
        this.rating = getColumnDouble(cursor, RATING);
        this.backdrop = getColumnString(cursor, BACKDROP);
        this.vote_count = getColumnInt(cursor, VOTECOUNT);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_favorit);
        dest.writeInt(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.deskripsi);
        dest.writeString(this.tanggalrilis);
        dest.writeString(this.poster);
        dest.writeValue(this.rating);
        dest.writeString(this.backdrop);
        dest.writeInt(this.vote_count);
    }

    protected MovieItems(Parcel in) {
        this.id_favorit = in.readInt();
        this.id = in.readInt();
        this.nama = in.readString();
        this.deskripsi = in.readString();
        this.tanggalrilis = in.readString();
        this.poster = in.readString();
        this.rating = (Double) in.readValue(Double.class.getClassLoader());
        this.backdrop = in.readString();
        this.vote_count = in.readInt();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}
