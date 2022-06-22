package com.example.eaarb;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    public String Name;
    @PrimaryKey
    @NonNull
    public String username;
    public String Password;
    public String server;
    public String image;

    public User() {
    }
    public User(String name,String username, String password,String server,String image ) {
        this.image = image;
        this.server = server;
        this.username = username;
        Name = name;
        Password = password;
    }

}
