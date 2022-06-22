package com.example.eaarb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class add_Contact extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class,"UsersDB").allowMainThreadQueries().build();
        userDao = db.userDao();
        Button addContact = findViewById(R.id.addcontact);
        addContact.setOnClickListener(v -> {
            addContact();
        });
        ImageButton back = findViewById(R.id.backcontacts);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(this,MainChat.class);
            startActivity(intent);
        });



    }



    private void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }



    private  Boolean isValidSignUPDetails(){
        EditText username = findViewById(R.id.UserNameContact);
        EditText name = findViewById(R.id.ContactName);
        EditText server = findViewById(R.id.ServerContact);
        if (username.getText().toString().isEmpty()) {
            showToast("Enter UserName");
            return false;
        }else if (name.getText().toString().isEmpty()) {
            showToast("Enter Name");
            return false;
        }else if (server.getText().toString().isEmpty()) {
            showToast("Enter your server");
            return false;
        }
        return true;
    }


    private void addContact() {
        boolean ok = isValidSignUPDetails();
        if (ok) {
            LoginUser loginUser = new LoginUser();
            EditText username = findViewById(R.id.UserNameContact);
            EditText name = findViewById(R.id.ContactName);
            EditText server = findViewById(R.id.ServerContact);
            UserWithContacts userWithContacts = userDao.getContacts(loginUser.getUsername());

            if (isContact()) {

                Contact contact = new Contact(username.getText().toString(),name.getText().toString(),loginUser.getUsername(),server.getText().toString());
                userDao.insert(contact);
                ContactWithMessages co = userDao.getMessages(contact.contName);
                co.messages.add(new Message(1," "," ", " " ,true));
                userWithContacts.contacts.add(contact);
                ContactWithMessages contactWithMessages = new ContactWithMessages();
                contactWithMessages.contact = contact;
                contactWithMessages.messages = new ArrayList<>();

            };
            showToast("have a new contact ");
        } else{
            showToast("this Contact in your Contacts");}
    }

    /**
     * check if you have this contact in your contacts...
     */
    private boolean isContact (){
        LoginUser loginUser = new LoginUser();
        EditText username = findViewById(R.id.UserNameContact);
        UserWithContacts userWithContacts = userDao.getContacts(loginUser.getUsername());
        if (userWithContacts == null) {
            return true;
        }
        for (int i = 0; i < userWithContacts.contacts.size(); i++) {
            if (userWithContacts.contacts.get(i).contName.equals(username.getText().toString())) {
                showToast("this Contact in your Contacts");
                return false;
            }
        }
        return true;
    }

}