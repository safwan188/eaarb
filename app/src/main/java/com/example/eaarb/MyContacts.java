package com.example.eaarb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eaarb.adapters.ContactAdapter;
import com.example.eaarb.adapters.MessageAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyContacts extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class,"UsersDB").allowMainThreadQueries().build();
        userDao = db.userDao();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);

        ImageButton imageButton = findViewById(R.id.myContacts);
                imageButton.setOnClickListener(v -> {
                    Intent i = new Intent(this,MainChat.class);
                    startActivity(i);
                });

        ImageButton defpage = findViewById(R.id.defpage);
        defpage.setOnClickListener(v -> {
            Intent i = new Intent(this,Definitions.class);
            startActivity(i);
        });

        setCName();

        RecyclerView messages = findViewById(R.id.MessagesList);
        MessageAdapter adapter = new MessageAdapter(this);
        messages.setAdapter(adapter);
        messages.setLayoutManager(new LinearLayoutManager(this));
        ContactConnected c = new ContactConnected();
         final ContactWithMessages contactWithMessages;
        contactWithMessages = userDao.getMessages(c.getConected());
        if (contactWithMessages.messages == null) {
        showToast("add new message");
        } else {
            adapter.setMessages(contactWithMessages.messages);
        }


        ImageButton sent = findViewById(R.id.sent);
        sent.setOnClickListener(v -> {
            EditText message = findViewById(R.id.newMessage);
            if (message.getText().toString().isEmpty()) {
            showToast("add message");
            } else {
                Message newMessage = new Message(0,c.getConected(),message.getText().toString(),getTime(),true);
               userDao.insert(newMessage);
               contactWithMessages.messages.add(newMessage);
            }
        });

    }
        private List<Contact> getContacts() {
            LoginUser loginUser = new LoginUser();
            UserWithContacts userWithContacts = userDao.getContacts(loginUser.getUsername());
    return userWithContacts.contacts;
        }
    public void setCName() {
        TextView name = findViewById(R.id.contactname1);
        ContactConnected c = new ContactConnected();
        String s = "Choose Contact";
        if (c.getConected() == null) {
            c.setConected(s);
        } else {
            name.setText(c.getConected());
        }
    }
        private void showToast(String message) {
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

}