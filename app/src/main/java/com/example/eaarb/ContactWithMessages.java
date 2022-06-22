package com.example.eaarb;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ContactWithMessages {
    @Embedded
    public Contact contact;
    @Relation(
            parentColumn = "contName",
            entityColumn = "contactId"
    )
    public List<Message> messages;
}

