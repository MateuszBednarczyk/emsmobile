package com.example.example;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("users/login")
    Call<UserCredentials> login(@Body UserCredentials userCredentials, @Header("Content-Type") String contentType);
}
