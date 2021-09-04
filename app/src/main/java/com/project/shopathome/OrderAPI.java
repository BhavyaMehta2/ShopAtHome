package com.project.shopathome;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrderAPI {
    String BASE_URL = "https://fakestoreapi.com/";
    @GET("carts")
    Call<List<Order>> getItems();
}
