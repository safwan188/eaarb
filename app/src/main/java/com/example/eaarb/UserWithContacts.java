package com.example.eaarb;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithContacts {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "username",
            entityColumn = "userId"
    )
    public List<Contact> contacts;

}

