package com.example.symphonia.Service;

import com.example.symphonia.Constants;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RetrofitApi {

    @DELETE("api/v1/me/following")
    Call<Void> unFollowArtists(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, String> params
    );

    @PATCH("api/v1/users/resetpassword/{token}")
    Call<JsonObject> resetPassword(
            @Path("token") String token
            , @Body Map<String,String> passwords
    );

    @PATCH("api/v1/users/activate/{token}")
    Call <JsonObject> applyArtist(@Path("token") String token);

    @POST("api/v1/users/{id}/playlists")
    Call<JsonObject> createPlaylist(
            @HeaderMap Map<String, String> headers,
            @Path("id") String id,
            @Body Map<String, Object> body
    );

    @GET("api/v1/playlists/{id}")
    Call<JsonObject> getPlaylist(@Path("id") String id);
}
