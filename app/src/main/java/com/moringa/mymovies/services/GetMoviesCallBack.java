package com.moringa.mymovies.services;

import com.moringa.mymovies.models.Movies;

public interface GetMoviesCallBack {
    //
    void onSuccess(Movies movies );

    void onError();
}
