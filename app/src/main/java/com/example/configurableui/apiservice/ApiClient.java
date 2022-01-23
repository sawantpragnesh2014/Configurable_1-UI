    package com.example.configurableui.apiservice;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static ApiEndPoint authApiEndpoint = null;
    private static final int TIMEOUT_PERIOD = 120;

    public static ApiEndPoint getAuthApi() {
        if (authApiEndpoint == null) {
            Retrofit retrofit = getRetrofitInstanceScalar();
            authApiEndpoint = retrofit.create(ApiEndPoint.class);
        }
        return authApiEndpoint;
    }

    @NonNull
    public static Retrofit getRetrofitInstanceScalar() {
        String baseUrl = "http://127.0.0.1:8081/";

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getNormalOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private static OkHttpClient getNormalOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT_PERIOD, TimeUnit.SECONDS) //Sets the default read timeout for new connections
                .writeTimeout(TIMEOUT_PERIOD, TimeUnit.SECONDS) //set the default write timeout for new connections
                .connectTimeout(TIMEOUT_PERIOD, TimeUnit.SECONDS); //Sets the default connect timeout for new connections.

        okHttpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        return okHttpClientBuilder.build();
    }

    private static Retrofit retrofit = null;
    /*static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();



        return retrofit;
    }*/
}
