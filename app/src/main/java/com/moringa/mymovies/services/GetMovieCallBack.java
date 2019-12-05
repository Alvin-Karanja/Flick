package com.moringa.mymovies.services;

import com.moringa.mymovies.models.Movies;

import java.util.List;

public interface GetMovieCallBack {
    void onSuccess(int page, List<Movies> movies);

    void onError();
}
