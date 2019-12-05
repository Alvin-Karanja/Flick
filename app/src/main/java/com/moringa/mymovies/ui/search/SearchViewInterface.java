package com.moringa.mymovies.ui.search;

import com.moringa.mymovies.models.MoviesResponse;

public interface SearchViewInterface {
    void showToast(String str);
    void displayResult(MoviesResponse movieResponse);
    void displayError(String s);
}
