package com.example.eaarb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public interface UserDao {
    // get user...
    @Query("SELECT * FROM User WHERE username = :username")
    User getUser(String username);
    //get the messages with this contact...
    @Transaction
    @Query("SELECT * FROM Contact WHERE contName = :contName")
    ContactWithMessages getMessages(String contName);

    @Transaction
    @Query("SELECT * FROM Contact WHERE contName = :contName")
    Contact getContact(String contName);

    @Query("SELECT * FROM User WHERE username = :username")
    UserWithContacts getContacts(String username);

    @Insert
    void insert(User... users);
    @Delete
    void delete(User... users);
    @Update
    void update(User... users);
    @Insert
    void insert(Contact... contacts);
    @Delete
    void delete(Contact... contacts);
    @Update
    void update(Contact... contacts);
    @Insert
    void insert(Message... messages);
    @Delete
    void delete(Message... messages);
    @Update
    void update(Message... messages);

}