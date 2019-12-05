package com.moringa.mymovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;



@Parcel
public class Movies {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("genres")
    @Expose
     List<Genres> genres;

    @SerializedName("poster_path")
    @Expose
    String posterPath;

    @SerializedName("release_date")
    @Expose
     String releaseDate;
    @SerializedName("website")
    @Expose
    String website;
    @SerializedName("backdrop_path")
    @Expose
     String backdrop;

    @SerializedName("overview")
    @Expose
     String overview;

    @SerializedName("vote_average")
    @Expose
    float rating;

    @SerializedName("genre_ids")
    @Expose
     List<Integer> genreIds;

    String index;

    public Movies(){}

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List releaseDate) {
        this.genres = genres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getWebsite() {
        return "https://www.themoviedb.org";
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }
    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

//    public List<Genre> getGenres() {
//        return genres;
//    }
//
//    public void setGenres(List<Genre> genres) {
//        this.genres = genres;
//    }

   
}

