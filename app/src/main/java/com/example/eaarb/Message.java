package com.example.eaarb;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @NonNull
    @PrimaryKey(autoGenerate=true)
    public int Id;
    public String contactId;
    public String content;
    public String created;
    public boolean sent;

    public Message(int id, String contactid, String content, String created, boolean sent) {
        this.Id = id;
        this.contactId = contactid;
        this.content = content;
        this.created = created;
        this.sent = sent;

    }


    public Message() {
    }
}