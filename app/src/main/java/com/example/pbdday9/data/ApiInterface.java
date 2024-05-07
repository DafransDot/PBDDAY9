package com.example.pbdday9.data;

import com.example.pbdday9.LoginActivity;
import com.example.pbdday9.RegisterActivity;
import com.example.pbdday9.data.model.Login;
import com.example.pbdday9.data.model.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
        @Field("username") String username,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> RegisterResponse(
            @Field("username") String username,
            @Field("name") String name,
            @Field("password") String password
    );

}
