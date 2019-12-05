package com.moringa.mymovies.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moringa.mymovies.models.Movies;
import com.moringa.mymovies.ui.MovieDetailFragment;

import java.util.ArrayList;

public class  MoviePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Movies> mMovies;

    public MoviePagerAdapter(FragmentManager fm, ArrayList<Movies> movies) {
        super(fm);
        mMovies = movies;
    }

    @Override
    public Fragment getItem(int position) {
        return MovieDetailFragment.newInstance(mMovies.get(position));
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMovies.get(position).getTitle();
    }
}
