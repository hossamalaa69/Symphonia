package com.example.symphonia.Service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

public interface RetrofitApi {

    @DELETE("api/v1/me/following")
    Call<Void> unFollowArtists(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, String> params
    );
}
