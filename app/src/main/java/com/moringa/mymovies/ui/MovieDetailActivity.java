package com.moringa.mymovies.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.moringa.mymovies.R;
import com.moringa.mymovies.adapters.MoviePagerAdapter;
import com.moringa.mymovies.models.Movies;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    public static String MOVIE_ID = "movie_id";
    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public MoviePagerAdapter adapterViewPager;
    ArrayList<Movies> mMovies = new ArrayList<>();
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movieId = getIntent().getIntExtra(MOVIE_ID, movieId);
        ButterKnife.bind(this);
//        getSupportActionBar().setTitle("Details");

        mMovies = Parcels.unwrap(getIntent().getParcelableExtra("movies"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new MoviePagerAdapter(getSupportFragmentManager(), mMovies);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

    }


}
