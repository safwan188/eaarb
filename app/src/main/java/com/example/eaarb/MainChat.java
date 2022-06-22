package com.example.eaarb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.eaarb.adapters.ContactAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;


public class MainChat extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;

    @Override
            protected void onCreate(Bundle savedInstanceState) {
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB").allowMainThreadQueries().build();
        userDao = db.userDao();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        setUserName();
        RoundedImageView imageButton = findViewById(R.id.image);
        imageButton.setOnClickListener(v -> {
            Intent i = new Intent(this, add_Contact.class);
            startActivity(i);
        });
        ImageView image = findViewById(R.id.defpage_main);
        image.setOnClickListener(v -> {
            Intent i = new Intent(this, Definitions.class);
            startActivity(i);
        });


        RecyclerView contacts = findViewById(R.id.contactsList);
        ContactAdapter adapter = new ContactAdapter(this);
        contacts.setAdapter(adapter);
        contacts.setLayoutManager(new LinearLayoutManager(this));
        LoginUser login = new LoginUser();
        UserWithContacts userWithContacts;
        userWithContacts = userDao.getContacts(login.getUsername());
        if (userWithContacts.contacts == null) {
            showToast("Add Contacts please");
        } else {
            adapter.setContacts(userWithContacts.contacts);
            int y = userWithContacts.contacts.size();
            ArrayList<Message> messages = new ArrayList<Message>();
            for (int i = 0; i < y; i++) {
                ContactWithMessages contact = userDao.getMessages(userWithContacts.contacts.get(i).contName);
                if (!contact.messages.isEmpty()) {
                    messages.add(contact.messages.get(contact.messages.size() - 1));
                } else {
                    messages.add(new Message(700," "," ", " ", true));

                }
            }
            adapter.setMessages(messages);

        }
    }
        private void showToast(String m) {
                Toast.makeText(this,m,Toast.LENGTH_SHORT).show();
            }

        private void setUserName() {
            TextView username = findViewById(R.id.Username);
            LoginUser loginUser = new LoginUser();
            User user = userDao.getUser(loginUser.getUsername());
            username.setText(user.Name);

        }


}