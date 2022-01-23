package com.example.configurableui.apiservice;

import com.example.configurableui.models.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiEndPoint {
    //http://127.0.0.1:8080/app/conf
    @GET("app/conf")
    Call<Root> fetchUI();
}
