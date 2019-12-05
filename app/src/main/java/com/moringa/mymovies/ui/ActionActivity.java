package com.moringa.mymovies.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringa.mymovies.R;
import com.moringa.mymovies.networks.MovieRep;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.moringa.mymovies.constants.Constant.FIREBASE_CHILD_SEARCHED_MOVIES;
import static com.moringa.mymovies.constants.Constant.PREFERENCES_REVIEW_KEY;

public class ActionActivity extends AppCompatActivity  {
private DatabaseReference mReviewedMovieReference;
private ValueEventListener mReviewedMovieReferenceListener;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private MovieRep movieRep;
    private int movieId;

    @BindView(R.id.postActionButton)
    Button mButton;
    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.actionView)
    TextView mReviewsLabel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mReviewedMovieReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(FIREBASE_CHILD_SEARCHED_MOVIES);

        mReviewedMovieReferenceListener = mReviewedMovieReference.addValueEventListener(new ValueEventListener() { //attach listener

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //something changed!
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String review = locationSnapshot.getValue().toString();
                    Log.d("Review updated", "review: " + review); //log
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { //update UI here if error occurred.

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviews = mEditText.getText().toString();

                saveReviewToFirebase(reviews);
                //addToSharedPreferences(reviews);
                Intent intent = new Intent(ActionActivity.this, ReviewsActivity.class);
                intent.putExtra("Reviews", reviews);
                startActivity(intent);
            }

//            private void addToSharedPreferences(String reviews) {
//                mEditor.putString(PREFERENCES_REVIEW_KEY, reviews).apply();
//            }

            public void saveReviewToFirebase(String reviews) {
                mReviewedMovieReference.push().setValue(reviews);
            }
        });


    }


    private void addToSharedPreferences(String reviews) {
        mEditor.putString(PREFERENCES_REVIEW_KEY, reviews).apply();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReviewedMovieReference.removeEventListener(mReviewedMovieReferenceListener);
    }

}