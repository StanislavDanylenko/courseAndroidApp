package com.example.stanislav.myapplication.retrofit.interfaze;

import com.example.stanislav.myapplication.entity.location.Country;
import com.example.stanislav.myapplication.entity.model.UserCredentialsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LocationSevice {

    @GET("mobile/location/full")
    Call<List<Country>> getFullLocation(@Header("Cookie") String cookie);

}
