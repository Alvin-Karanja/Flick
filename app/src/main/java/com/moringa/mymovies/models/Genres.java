package com.moringa.mymovies.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
@Parcel
public class Genres {
    @SerializedName("id")
    @Expose
     int id;

    @SerializedName("name")
    @Expose
     String name;

    public  Genres(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
