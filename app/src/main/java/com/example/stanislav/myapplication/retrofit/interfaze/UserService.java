package com.example.stanislav.myapplication.retrofit.interfaze;

import com.example.stanislav.myapplication.entity.User;
import com.example.stanislav.myapplication.entity.UserAuth;
import com.example.stanislav.myapplication.entity.model.UpdatePasswordModel;
import com.example.stanislav.myapplication.entity.model.UserCredentialsModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @POST("login")
    Call<UserAuth> loginUser(@Query("email") String email,
                             @Query("password") String password);

    @GET("mobile/{id}")
    Call<User> getUser(@Header("Cookie") String cookie, @Path("id") Long id);

    @PUT("mobile/{id}")
    Call<User> updateUser(@Header("Cookie") String cookie, @Body User model, @Path("id") Long id);

    @POST("mobile/password")
    Call<User> updatePassword(@Header("Cookie") String cookie, @Body UpdatePasswordModel model);

}
