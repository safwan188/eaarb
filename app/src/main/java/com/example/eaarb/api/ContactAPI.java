package com.example.eaarb.api;

import com.example.eaarb.Contact;
import com.example.eaarb.LoginUser;
import com.example.eaarb.Message;
import com.example.eaarb.R;
import com.example.eaarb.UserDao;
import com.example.eaarb.UserDao_Impl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eaarb.adapters.ContactAdapter;
import com.example.eaarb.adapters.MessageAdapter;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

public class ContactAPI {
 private MutableLiveData<Boolean> postListData;
 private UserDao dao;
 private LoginUser loginUser;
 Retrofit retrofit;
 WebServiceAPI webServiceAPI;

         public ContactAPI(LoginUser user ,MutableLiveData<Boolean> data) {
         this.loginUser=user;
         this.postListData=data;
         retrofit = new Retrofit.Builder()
         .baseUrl(MyApplication.context.getString(R.string.BaseUrl)).client(getUnsafeOkHttpClient().build())
         .addConverterFactory(GsonConverterFactory.create())
         .build();
         webServiceAPI = retrofit.create(WebServiceAPI.class);
         }
         public void get() {
         Call<List<Contact>> call = webServiceAPI.getContacts(loginUser.getUsername());

         call.enqueue(new Callback<List<Contact>>() {
             @Override
             public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                 List<Contact> c=response.body();
             }

             @Override
             public void onFailure(Call<List<Contact>> call, Throwable t) {

             }
         });

         }
    public void getmessages(Contact contact){
        Call<List<Message>> call = webServiceAPI.getMessages(loginUser.getUsername(),contact.contName);
        call.enqueue(new Callback<List<Message>>() {
            @Override
                public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                List<Message> body = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {}
        });
    }

         public boolean Register(String username ,String password,String server,String image){
             return true;




         }
    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void Login(String username ,String password){
        Call<Boolean> call = webServiceAPI.Login(username,password);
        Boolean  c ;
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                postListData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }
    public void Loginpass(String username){
        Call<Boolean> call = webServiceAPI.Login(username,username);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean c=response.body();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }
    }

