package com.example.visioneerapp;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @POST("auth/login")
    Call<LoginResponse>loginUser(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<RegisterResponse>registerUser(@Body RegisterRequest registerRequest);

    @POST("conv-image")
    Call<DWG_Response>DWG_Converter(@Body DWG_Request dwgRequest);

//    @PUT("users/{userId}")
//    Call<UpdateUserResponse> updateUser(@Header("Authorization") String token, @Path("userId") String userId, @Body UpdateUserRequest updateUserRequest);
//    @PUT("users/{userId}")
//    Call<UpdateUserResponse> updateUser(@Path("userId") String userId, @Body UpdateUserRequest updateUserRequest, @Header("X-Auth-Token") String token);
    @PUT("users/{userId}")
    Call<UpdateUserResponse> updateUser(@Path("userId") String userId, @Body UpdateUserRequest updateUserRequest, @HeaderMap Map<String, String> headers);

    @POST("ai-image/{userId}")
    Call<GenerateImageResponse> generateImage(@Path("userId") String userId, @Body GenerateImageRequest generateImageRequest, @HeaderMap Map<String, String> headers);

    @POST("ai-image2/{userId}")
    Call<GenerateImageResponse2> generateImage2(@Path("userId") String userId, @Body GenerateImageRequest2 generateImageRequest2, @HeaderMap Map<String, String> headers);
}
