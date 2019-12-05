package com.moringa.mymovies.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.moringa.mymovies.R;
import com.moringa.mymovies.models.Genres;
import com.moringa.mymovies.models.Movies;
import com.moringa.mymovies.networks.MovieRep;
import com.moringa.mymovies.services.GetGenreCallBack;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.moringa.mymovies.constants.Constant.IMAGE_BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.movieImageView)
    ImageView mImageLabel;
    @BindView(R.id.movieNameTextView)
    TextView mNameLabel;
    @BindView(R.id.genreTextView)
    TextView mGenreLabel;
    @BindView(R.id.ratingTextView)
    TextView mRatingLabel;
    @BindView(R.id.websiteTextView)
    TextView mWebsiteLabel;
    @BindView(R.id.releaseTextView)
    TextView mReleaseTLabel;
    @BindView(R.id.overviewTextView)
    TextView mOverviewLabel;


    private Movies mMovies;
    private Genres mGenres;

    public static MovieDetailFragment newInstance(Movies movies)  {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("movies", Parcels.wrap(movies));
        movieDetailFragment.setArguments(args);
        return movieDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovies = Parcels.unwrap(getArguments().getParcelable("movies"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        Glide.with(MovieDetailFragment.this)
                .load(IMAGE_BASE_URL + mMovies.getBackdrop())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(mImageLabel);

        mWebsiteLabel.setOnClickListener(this);
        mNameLabel.setText(mMovies.getTitle());
        mGenreLabel.setText(android.text.TextUtils.join(", ", mMovies.getGenreIds()));
        mRatingLabel.setText(Float.toString(mMovies.getRating()) + "/10");
        mReleaseTLabel.setText(mMovies.getReleaseDate());
        mOverviewLabel.setText(mMovies.getOverview());

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mMovies.getWebsite()));
            startActivity(webIntent);
        }


    }
}