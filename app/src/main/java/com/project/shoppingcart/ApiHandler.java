package com.project.shoppingcart;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {

    private static final String TAG = "ApiHandler";
    private static Retrofit retrofit = null;


    public static Retrofit getRetrofit() {
        retrofit=null;
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().
                connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(15, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("https://www.mocky.io/")
                    .client(okHttpClient.build())
                    .build();
        return retrofit;
    }





}



