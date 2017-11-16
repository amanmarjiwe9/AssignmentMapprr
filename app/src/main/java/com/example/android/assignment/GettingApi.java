package com.example.android.assignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface GettingApi {
    @GET("/search/repositories")
    Call<ConfigItems> loadRepos(@Query("q") String tags,@Query("sort")String watchers,@Query("order")String watcher);
   ;

}
