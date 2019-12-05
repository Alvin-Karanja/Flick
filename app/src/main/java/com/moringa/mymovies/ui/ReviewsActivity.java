package com.moringa.mymovies.ui;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringa.mymovies.R;
import com.moringa.mymovies.models.Genres;
import com.moringa.mymovies.models.Movies;
import com.moringa.mymovies.models.Review;
import com.moringa.mymovies.networks.MovieRep;
import com.moringa.mymovies.services.GetGenreCallBack;
import com.moringa.mymovies.services.GetMovieCallBack;
import com.moringa.mymovies.services.GetMoviesCallBack;
import com.moringa.mymovies.services.GetReviewsCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


import static com.moringa.mymovies.constants.Constant.FIREBASE_CHILD_MOVIES;
import static com.moringa.mymovies.constants.Constant.IMAGE_BASE_URL;
import static com.moringa.mymovies.constants.Constant.MOVIE_ID;
import static com.moringa.mymovies.constants.Constant.PREFERENCES_REVIEW_KEY;
import static com.moringa.mymovies.constants.Constant.TMDB_TOKEN;



public class ReviewsActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences mSharedPreferences;
    private String mRecentAddress;
    private Movies mMovies;
    private MovieRep movieRep;
    private int movieId;

    @BindView(R.id.reviewTextView)
    TextView mReviewTextView;
    @BindView(R.id.saveMovieButton)
    TextView mSaveMovieButton;
    //    @BindView(R.id.movieNameTextView)
//    TextView movieTitle;
//    @BindView(R.id.movieReviews)
//    TextView movieReviews;
//    @BindView(R.id.reviewsLabel)
    TextView reviewsLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Postings");

        Intent intent = getIntent();
        String reviews = intent.getStringExtra("Reviews");

        mReviewTextView.setText("Recent reviews: " + reviews);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(PREFERENCES_REVIEW_KEY, null);
        Log.d("Shared Pref Location", mRecentAddress);

        mSaveMovieButton.setOnClickListener(this);

        //getMovie();
    }
    public void onClick(View v) {
        if (v == mSaveMovieButton) {
            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(FIREBASE_CHILD_MOVIES);
            restaurantRef.push().setValue(mMovies);
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
        }

    }
//    private void getMovie() {
//        movieRep.getMovie(movieId, new GetMoviesCallBack() {
//            @Override
//            public void onSuccess(Movies movies) {
//                movieTitle.setText(movies.getTitle());
//                getReviews(movies);
//            }
//
//            @Override
//            public void onError() {
//                finish();
//            }
//        });
//    }
//    private void getReviews(Movies movies) {
//        movieRep.getReviews(movies.getId(), new GetReviewsCallBack() {
//            @Override
//            public void onSuccess(List<Review> reviews) {
//                reviewsLabel.setVisibility(View.VISIBLE);
//                movieReviews.clearComposingText();
//                for (Review review : reviews) {
//                    View parent = getLayoutInflater().inflate(R.layout.review, movieReviews, false);
//                    TextView author = parent.findViewById(R.id.reviewAuthor);
//                    TextView content = parent.findViewById(R.id.reviewContent);
//                    author.setText(review.getAuthor());
//                    content.setText(review.getContent());
//                    movieReviews.addView(parent);
//                }
//            }
//            @Override
//            public void onError() {
//                // Do nothing
//            }
//        });
//    }




}
