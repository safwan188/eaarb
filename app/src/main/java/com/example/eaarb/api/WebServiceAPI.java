package com.example.eaarb.api;
import com.example.eaarb.Contact;
import com.example.eaarb.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
 public class LoginInformation {
  public String username;
  public String password;
 }
 @GET("contacts/{Username}")
 Call<List<Contact>> getContacts(@Path("Username") String Username);
 @GET("contacts/Login/{username}/{password}")
 Call<Boolean> Login(@Path("username") String username,@Path("password") String password);

 @POST("contacts/Register")
 Call<Boolean>Register(@Body String username,@Body String password,@Body String server,@Body String image);
 //Body=Bind
 @DELETE("contacts/{id}")
 Call<Void> deletePost(@Path("id") int id);
 @GET("contacts/{Username}/{contName}")
 Call<List<Message>> getMessages(@Path("Username") String username,@Path("contName") String contName);
}
