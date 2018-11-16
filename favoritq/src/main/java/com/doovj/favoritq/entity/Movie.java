package com.doovj.favoritq.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.BACKDROP;
import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.POSTER;
import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.RATING;
import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.TANGGALRILIS;
import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.TITLE;
import static com.doovj.favoritq.db.DatabaseContract.MovieColumns.VOTECOUNT;
import static com.doovj.favoritq.db.DatabaseContract.getColumnDouble;
import static com.doovj.favoritq.db.DatabaseContract.getColumnInt;
import static com.doovj.favoritq.db.DatabaseContract.getColumnString;

public class Movie implements Parcelable {
    private int id;
    private String nama;
    private String deskripsi;
    private String tanggalrilis;
    private String poster;
    private Double rating;
    private String backdrop;
    private int vote_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.deskripsi);
        dest.writeString(this.tanggalrilis);
        dest.writeString(this.poster);
        dest.writeValue(this.rating);
        dest.writeString(this.backdrop);
        dest.writeInt(this.vote_count);
    }

    public Movie() {
    }

    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.poster = getColumnString(cursor, POSTER);
        this.nama = getColumnString(cursor, TITLE);
        this.deskripsi = getColumnString(cursor, DESKRIPSI);
        this.tanggalrilis = getColumnString(cursor, TANGGALRILIS);
        this.rating = getColumnDouble(cursor, RATING);
        this.vote_count = getColumnInt(cursor, VOTECOUNT);
        this.backdrop = getColumnString(cursor, BACKDROP);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.nama = in.readString();
        this.deskripsi = in.readString();
        this.tanggalrilis = in.readString();
        this.poster = in.readString();
        this.rating = (Double) in.readValue(Double.class.getClassLoader());
        this.backdrop = in.readString();
        this.vote_count = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
