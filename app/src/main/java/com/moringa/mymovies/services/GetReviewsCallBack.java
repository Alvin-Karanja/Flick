package com.moringa.mymovies.services;

import com.moringa.mymovies.models.Review;

import java.util.List;

public interface GetReviewsCallBack {
    void onSuccess(List<Review> reviews);

    void onError();
}
