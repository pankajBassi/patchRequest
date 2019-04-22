package com.example.setrestapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);
}
