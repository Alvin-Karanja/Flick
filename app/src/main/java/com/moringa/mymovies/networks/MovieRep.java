package com.moringa.mymovies.networks;

import android.util.Log;

import androidx.annotation.NonNull;

import com.moringa.mymovies.constants.Constant;
import com.moringa.mymovies.models.GenreResponse;
import com.moringa.mymovies.models.MovieResponse;
import com.moringa.mymovies.models.Movies;
import com.moringa.mymovies.models.ReviewResponse;
import com.moringa.mymovies.models.TrailerResponse;
import com.moringa.mymovies.services.GetGenreCallBack;
import com.moringa.mymovies.services.GetMovieCallBack;
import com.moringa.mymovies.services.GetMoviesCallBack;
import com.moringa.mymovies.services.GetReviewsCallBack;
import com.moringa.mymovies.services.GetTrailersCallback;
import com.moringa.mymovies.services.TmdbApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.moringa.mymovies.constants.Constant.LANGUAGE;
import static com.moringa.mymovies.constants.Constant.POPULAR;
import static com.moringa.mymovies.constants.Constant.TMDB_TOKEN;
import static com.moringa.mymovies.constants.Constant.TOP_RATED;
import static com.moringa.mymovies.constants.Constant.UPCOMING;

public class MovieRep {

    private static MovieRep rep;

    private TmdbApiInterface api;


    private MovieRep(TmdbApiInterface  api) {
        this.api = api;
    }

    public static MovieRep getInstance() {
        if (rep == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            rep = new MovieRep(retrofit.create(TmdbApiInterface .class));
        }

        return rep;
    }

    public void getMovies(int page,String sortBy, final GetMovieCallBack callback) {
        Callback<MovieResponse> call = new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse moviesResponse = response.body();
                    if (moviesResponse != null && moviesResponse.getMovies() != null) {
                        callback.onSuccess(moviesResponse.getPage(), moviesResponse.getMovies());
                    } else {
                        callback.onError();
                    }
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onError();
            }
        };

        switch (sortBy) {
            case TOP_RATED:
                api.getTopRatedMovies(TMDB_TOKEN, LANGUAGE, page)
                        .enqueue(call);
                break;
            case UPCOMING:
                api.getUpcomingMovies(TMDB_TOKEN, LANGUAGE, page)
                        .enqueue(call);
                break;
            case POPULAR:
            default:
                api.getPopularMovies(TMDB_TOKEN, LANGUAGE, page)
                        .enqueue(call);
                break;
        }
    }
    public void getGenres(final GetGenreCallBack callback) {
        api.getGenres(TMDB_TOKEN, LANGUAGE)
                .enqueue(new Callback<GenreResponse>() {
                    @Override
                    public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                        if (response.isSuccessful()) {
                            GenreResponse genresResponse = response.body();
                            if (genresResponse != null && genresResponse.getGenres() != null) {
                                callback.onSuccess(genresResponse.getGenres());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenreResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
    public void getMovie(int movieId, final GetMoviesCallBack callback) {
        api.getMovie(movieId, TMDB_TOKEN, LANGUAGE)
                .enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {
                        if (response.isSuccessful()) {
                            Movies movie = response.body();
                            if (movie != null) {
                                callback.onSuccess(movie);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
    public void getTrailers(int movieId, final GetTrailersCallback callback) {
        api.getTrailers(movieId, TMDB_TOKEN, LANGUAGE)
                .enqueue(new Callback<TrailerResponse>() {
                    @Override
                    public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                        if (response.isSuccessful()) {
                            TrailerResponse trailerResponse = response.body();
                            if (trailerResponse != null && trailerResponse.getTrailers() != null) {
                                callback.onSuccess(trailerResponse.getTrailers());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<TrailerResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getReviews(int movieId, final GetReviewsCallBack callback) {
        api.getReviews(movieId, TMDB_TOKEN, LANGUAGE)
                .enqueue(new Callback<ReviewResponse>() {
                    @Override
                    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                        if (response.isSuccessful()) {
                            ReviewResponse reviewResponse = response.body();
                            if (reviewResponse != null && reviewResponse.getReviews() != null) {
                                callback.onSuccess(reviewResponse.getReviews());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}

