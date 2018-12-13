package com.example.stanislav.myapplication;

import android.app.Application;

import com.example.stanislav.myapplication.entity.User;
import com.example.stanislav.myapplication.entity.UserAuth;
import com.example.stanislav.myapplication.entity.enumeration.Localization;
import com.example.stanislav.myapplication.entity.location.Country;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SpeeerApplication extends Application {

    private UserAuth credentials;
    private User user;
    private User updatedUser;
    private List<Country> location;
    public static String BASE_URL = "http://c3dadf42.ngrok.io";

    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public UserAuth getCredentials() {
        return credentials;
    }

    public void setCredentials(UserAuth credentials) {
        this.credentials = credentials;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Country> getLocation() {
        return location;
    }

    public void setLocation(List<Country> location) {
        this.location = location;
    }


    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }
}
