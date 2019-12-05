package com.moringa.mymovies.services;

import com.moringa.mymovies.models.Genres;

import java.util.List;

public interface GetGenreCallBack {
    void onSuccess(List<Genres> genres);

    void onError();
}
