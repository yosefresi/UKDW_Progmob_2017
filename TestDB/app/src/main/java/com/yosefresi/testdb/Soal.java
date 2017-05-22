package com.yosefresi.testdb;

import android.widget.ImageView;

/**
 * Created by yosef on 03-May-17.
 */

public class Soal {
//    private String soalID;
    private String judul;
    private String mapel;
    private String kelas;
    private String keterangan;
    private String tanggal;
    private String author;
    private String jumlahComment;

    public Soal(String judul, String mapel, String kelas, String keterangan, String tanggal, String author, String jumlahComment){
        this.setJudul(judul);
        this.setJumlahComment(jumlahComment);
        this.setMapel(mapel);
        this.setKeterangan(keterangan);
        this.setTanggal(tanggal);
        this.setAuthor(author);
        this.setKelas(kelas);
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJumlahComment() {
        return jumlahComment;
    }

    public void setJumlahComment(String jumlahComment) {
        this.jumlahComment = jumlahComment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
