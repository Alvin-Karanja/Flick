package com.moringa.mymovies.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringa.mymovies.R;
import com.moringa.mymovies.models.Movies;
//import com.moringa.mymovies.ui.ReviewsActivity;
import com.moringa.mymovies.ui.ReviewsActivity;
import com.moringa.mymovies.ui.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.moringa.mymovies.constants.Constant.FIREBASE_CHILD_MOVIES;

public class FirebaseMovieViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;
    public ImageView mMovieImageView;
    public FirebaseMovieViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindMovies(Movies movies) {
        mMovieImageView = (ImageView) mView.findViewById(R.id.movieImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.movieNameTextView);
        TextView genreTextView = (TextView) mView.findViewById(R.id.genreTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

        Picasso.get().load(movies.getPosterPath()).into(mMovieImageView);

        nameTextView.setText(movies.getTitle());
        genreTextView.setText(movies.getGenreIds().get(0));
        ratingTextView.setText("Rating: " + movies.getRating() + "/10");
    }
    @Override
    public void onItemSelected(){
        //Log.d("Animation", "onItemSelected");
        // we will add animations here
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }

    @Override
    public void onItemClear(){
        //Log.d("Animation", "onItemClear");
        // we will add animations here
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }
}
