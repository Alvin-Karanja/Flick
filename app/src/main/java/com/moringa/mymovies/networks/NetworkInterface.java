package com.moringa.mymovies.networks;



import com.moringa.mymovies.models.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {
    @GET("search/movie")
    Observable<MoviesResponse> getMoviesBasedOnQuery(@Query("api_key") String api_key, @Query("query") String q);
    @GET("discover/movie")
    Observable<MoviesResponse> getMovies(@Query("api_key") String api_key);

}
