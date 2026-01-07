package com.example.dataviewerapp.network;
import com.example.dataviewerapp.models.Post;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/posts")
    Call<List<Post>> getPosts();
}