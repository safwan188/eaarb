package com.example.eaarb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eaarb.api.ContactAPI;
import com.example.eaarb.api.WebServiceAPI;

public class MainActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // data
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class,"UsersDB").allowMainThreadQueries().build();
        userDao = db.userDao();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // create new account text
        TextView create = findViewById(R.id.createNewAccount);
        create.setOnClickListener(view -> {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        });
        // checks sign in
        Button signIn = findViewById(R.id.signIn_btn);
        signIn.setOnClickListener(v -> {
            boolean ok = isValidSignUPDetails();
            if (ok) {
                EditText username = findViewById(R.id.username_login);
                EditText password = findViewById(R.id.password_login);
                User user = userDao.getUser(username.getText().toString());
                if (user == null){
                    showToast("please Sign up");
                } else {
                    if (password.getText().toString().equals(user.Password)){
                        LoginUser loginUser = new LoginUser();
                        loginUser.setUsername(username.getText().toString());
                        MutableLiveData<Boolean> d=new MutableLiveData<>();



                        ContactAPI contactAPI=new ContactAPI(d);
                        WebServiceAPI.LoginInformation loginInformation = new WebServiceAPI.LoginInformation();
                        loginInformation.username=username.getText().toString();
                        loginInformation.password=password.getText().toString();
                        contactAPI.Login(loginInformation.username.toString(),loginInformation.password.toString());
                        Boolean c=d.getValue();
                        d.observe(this,kk->{
                            if (kk){
                                Intent i = new Intent(this, MainChat.class);
                                startActivity(i);
                            }  else {
                                showToast("Check your information");
                            }
                        });}
                    else {
                        showToast("Check your information");
                    }
                }
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    private  Boolean isValidSignUPDetails() {
        EditText username = findViewById(R.id.username_login);
        EditText password = findViewById(R.id.password_login);
        if (username.getText().toString().isEmpty()) {
            showToast("Enter Name");
            return false;
        } else if (password.getText().toString().isEmpty()) {
            showToast("Enter Password");
            return false;
        }
        return true;
    }
}