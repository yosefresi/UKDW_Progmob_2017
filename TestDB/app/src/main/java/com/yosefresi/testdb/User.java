package com.yosefresi.testdb;

import java.util.List;

/**
 * Created by yosef on 11-May-17.
 */

public class User {
    private String id;
    private String nama;
    private String password;
    private String username;

    public User(String username, String password, String nama){
        this.setUsername(username);
        this.setPassword(password);
        this.setNama(nama);
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
