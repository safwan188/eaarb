package com.example.eaarb;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.eaarb.api.ContactAPI;
import com.example.eaarb.api.WebServiceAPI;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SignUp extends AppCompatActivity {
    //private String encodedImage;
    public AppDB db;
    public UserDao userDao;
    private  byte[] byteArray;
    private ImageView IVPreviewImage;

    int SELECT_PICTURE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class,"UsersDB").allowMainThreadQueries().build();
        userDao = db.userDao();
        TextView signIn = findViewById(R.id.signInText);
        signIn.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
        Button sign = findViewById(R.id.signUp_btn);
        sign.setOnClickListener(v -> {
           signUp();
        });
        ImageView imageView;
        RoundedImageView SelectImage = findViewById(R.id.BSelectImage);
        SelectImage.setOnClickListener(v -> {
            imageChooser();
          //  Bitmap bmp = BitmapFactory.decodeByteArray(temp.image, 0, temp.image.length);
          //  image.setImageBitmap(bmp);
        });



    }

private void signUp(){
    boolean ok = isValidSignUPDetails();
    if (ok) {
        EditText name = findViewById(R.id.Name_signup);
        EditText username = findViewById(R.id.username_SignUp);
        EditText password = findViewById(R.id.password_SignUp);
        if (userDao.getUser(username.getText().toString()) != null){
            showToast("your username is on data");
        }else{
            MutableLiveData<Boolean> d=new MutableLiveData<>();
            ContactAPI contactAPI=new ContactAPI(d);
            WebServiceAPI.LoginInformation loginInformation = new WebServiceAPI.LoginInformation();
            contactAPI.Register(username.getText().toString(),password.getText().toString(),
                    name.getText().toString(),"A");

            d.observe(this,kk->{
                if (kk){
                    User user = new User(name.getText().toString(), username.getText().toString(), password.getText().toString(), "A", "A");
                    userDao.insert(user);
                    UserWithContacts userWithContacts = new UserWithContacts();
                    userWithContacts.user = user;
                    List<Contact> list = new ArrayList<Contact>();
                    userWithContacts.contacts = list;
                    showToast("Sign Up is succeed");
                }  else {
                    showToast("error");
                }
            });}

        }
    }


    private void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
}

    private Boolean isValidSignUPDetails(){
        EditText name = findViewById(R.id.Name_signup);
        EditText username = findViewById(R.id.username_SignUp);
        EditText password = findViewById(R.id.password_SignUp);
        EditText confirmPassword = findViewById(R.id.password_SignUp1);
        if (name.getText().toString().isEmpty()){
            showToast("Enter name");
            return false;
        }else if (username.getText().toString().length() == 0) {
            showToast("Enter UserName");
            return false;
        }else if(password.getText().toString().isEmpty()){
            showToast("Enter Password");
            return false;
        }else if (confirmPassword.getText().toString().isEmpty()){
            showToast("Confirm your Password");
            return false;
        }else if (!password.getText().toString().equals(confirmPassword.getText().toString())){
            showToast("Password & confirm password must be same");
            return false;
        }
        return true;
    }
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();
                        bitmap.recycle();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}