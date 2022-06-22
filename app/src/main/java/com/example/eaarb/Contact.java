package com.example.eaarb;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey
    @NonNull
    public String contName;
    public String userId;
    public String name;
    public String server;


    public Contact(@NonNull String contName, String name, String userid, String server) {
        this.contName = contName;
        this.server = server;
        this.userId = userid;
        this.name = name;
    }


    public Contact() {
    }
}
