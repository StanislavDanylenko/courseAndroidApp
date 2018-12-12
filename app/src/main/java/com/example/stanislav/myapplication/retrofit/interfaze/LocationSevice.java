package com.example.stanislav.myapplication.retrofit.interfaze;

import com.example.stanislav.myapplication.entity.location.Country;
import com.example.stanislav.myapplication.entity.model.UserCredentialsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationSevice {

    @POST("mobile/location/full")
    Call<List<Country>> getUser(@Body UserCredentialsModel model);

}
