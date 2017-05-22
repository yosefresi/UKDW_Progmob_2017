package com.yosefresi.testdb;

/**
 * Created by yosef on 15-May-17.
 */

public class Comment {
    private String nama;
    private String isiComment;

    public Comment(String nama, String isiComment){
        this.setIsiComment(isiComment);
        this.setNama(nama);
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIsiComment() {
        return isiComment;
    }

    public void setIsiComment(String isiComment) {
        this.isiComment = isiComment;
    }
}

