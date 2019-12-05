package com.moringa.mymovies.services;

import com.moringa.mymovies.models.Trailer;

import java.util.List;

public interface GetTrailersCallback {
    void onSuccess(List<Trailer> trailers);

    void onError();
}
