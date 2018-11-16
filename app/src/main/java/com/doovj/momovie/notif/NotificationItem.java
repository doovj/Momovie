package com.doovj.momovie.notif;

public class NotificationItem {
    private int id_pesan;
    private String judulna;
    private String pesan;

    public int getId_pesan() {
        return id_pesan;
    }

    public void setId_pesan(int id_pesan) {
        this.id_pesan = id_pesan;
    }

    public String getJudulna() {
        return judulna;
    }

    public void setJudulna(String judulna) {
        this.judulna = judulna;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public NotificationItem(int id_pesan, String judulna, String pesan) {
        this.id_pesan = id_pesan;
        this.judulna = judulna;
        this.pesan = pesan;
    }
}
