package com.example.eaarb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Definitions extends AppCompatActivity {
    public AppDB db;
    public UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definitions);
        //data base
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class,"UsersDB").allowMainThreadQueries().build();
        userDao = db.userDao();


        ImageButton contacts_def = findViewById(R.id.contacts_def);
        contacts_def.setOnClickListener(v -> {
            Intent i = new Intent(this,MainChat.class);
            startActivity(i);
        });


        ImageButton circle_def = findViewById(R.id.circle_def);
        circle_def.setOnClickListener(v -> {
            Intent i = new Intent(this,MyContacts.class);
        startActivity(i);
        });
        Button editInformation = findViewById(R.id.EditInformation);
        editInformation.setOnClickListener(v -> {
        boolean ok = isDetaliesOnDefinition();
        if (ok){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        });
    }

        public boolean isDetaliesOnDefinition() {

            boolean serverOk;
            boolean nameOk;
            boolean passwordOk;
                serverOk = change_server();
                nameOk = changeName();
               passwordOk = changePassword();
           if (!serverOk && !nameOk && !passwordOk) {
               return false;
           }
            return true;
        }
    private void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    private boolean change_server() {
        EditText server = findViewById(R.id.serverchange);
        if (server.getText().toString().isEmpty()) {
            return false;
        }
        LoginUser loginUser = new LoginUser();
        User user = userDao.getUser(loginUser.getUsername());
        user.server = server.getText().toString();
        showToast("your server is been changed");
        return true;
    }
    private boolean changeName(){
        EditText name = findViewById(R.id.Editname);
        if (name.getText().toString().isEmpty()) {
            return false;
        }
        LoginUser loginUser = new LoginUser();
        User user = userDao.getUser(loginUser.getUsername());
        user.Name = name.getText().toString();
        showToast("your name has been changed");
        return true;
    }
    private boolean changePassword() {
        EditText passwordNow = findViewById(R.id.yourPassword);
        EditText passwordNew = findViewById(R.id.newPassword);
        EditText ConfirmPasswordNew = findViewById(R.id.ConfirmNewPassword);
        if (passwordNew.getText().toString().isEmpty()) {
            showToast("Add your new Password");
        return false;
        }
        if (passwordNow.getText().toString().isEmpty()) {
            showToast("Put your password now");
        return false;
        }
        if (ConfirmPasswordNew.getText().toString().isEmpty()) {
            showToast("Confirm your new Password");
            return false;
        }
        LoginUser loginUser = new LoginUser();
        User user = userDao.getUser(loginUser.getUsername());
        if (user.Password.equals(passwordNow.getText().toString())) {
            if (passwordNew.getText().toString().equals(ConfirmPasswordNew.getText().toString())) {
                user.Password = passwordNew.getText().toString();
                showToast("your message is been changed");
                return true;
            } else {
                showToast("NewPassword & confirm New password must be same");
            return false;
            }
        } else {
                showToast("put your password correct");

        return false;}
    }

}