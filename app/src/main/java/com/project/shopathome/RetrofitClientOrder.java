package com.project.shopathome;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientOrder {

    private static RetrofitClientOrder instance = null;
    private OrderAPI myApi;

    private RetrofitClientOrder() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(OrderAPI.class);
    }

    public static synchronized RetrofitClientOrder getInstance() {
        if (instance == null) {
            instance = new RetrofitClientOrder();
        }
        return instance;
    }

    public OrderAPI getMyApi() {
        return myApi;
    }
}