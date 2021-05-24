package com.example.laptop_gearx;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//tuong tac vs server
public class APIRetrofitClient {
    private static Retrofit retrofit=null;//cấp phát bộ nhớ tránh trường hợp gọi k có dl sẽ bị lỗi
    public static Retrofit getClient(String base_url){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                                    .writeTimeout(10000, TimeUnit.MILLISECONDS)
                                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                                    .retryOnConnectionFailure(true)
                                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                                    .build();
        Gson gson=new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        return retrofit;
    }
}