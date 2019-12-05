package com.moringa.mymovies.constants;

import com.moringa.mymovies.BuildConfig;

public final class Constant {
public static final String TMDB_TOKEN = BuildConfig.TMDB_TOKEN;
public static final String BASE_URL = "https://api.themoviedb.org/3/";
public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
public static final String LANGUAGE = "en-US";
public static final String MOVIE_ID = "movie_id";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final String UPCOMING = "upcoming";

    private static  String YOUTUBE_BASE_URL = "http://image.tmdb.org/t/p/w780";
    private static String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s";
    private static String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg";


public static final String PREFERENCES_REVIEW_KEY = "reviews";
public static final String FIREBASE_CHILD_MOVIES= "movies";
public static final String FIREBASE_CHILD_SEARCHED_MOVIES= "searchedMovies";
    public static final String PREFERENCES_SEARCH_KEY = "search";
    public static final String FIREBASE_QUERY_INDEX = "index";
}
