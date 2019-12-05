package com.moringa.mymovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("page")
    @Expose
     int page;

    @SerializedName("total_results")
    @Expose
     int totalResults;

    @SerializedName("results")
    @Expose
     List<Movies> movies;

    @SerializedName("total_pages")
    @Expose
     int totalPages;
//    @SerializedName("results")
//    @Expose
//    private List<Movies> results = null;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Movies> getMovies() {
        return movies;
    }

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
