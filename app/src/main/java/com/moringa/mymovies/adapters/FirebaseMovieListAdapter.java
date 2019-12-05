package com.moringa.mymovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.moringa.mymovies.R;
import com.moringa.mymovies.models.Movies;
import com.moringa.mymovies.models.MoviesResponse;
import com.moringa.mymovies.ui.MovieDetailActivity;
import com.moringa.mymovies.ui.util.ItemTouchHelperAdapter;
import com.moringa.mymovies.ui.util.OnStartDragListene;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseMovieListAdapter extends FirebaseRecyclerAdapter<Movies, FirebaseMovieViewHolder> implements ItemTouchHelperAdapter {
    private Query mRef;
    private OnStartDragListene mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Movies> mMovies = new ArrayList<>();

    public FirebaseMovieListAdapter(FirebaseRecyclerOptions<Movies> options,
                                         Query ref,
                                         OnStartDragListene onStartDragListener,
                                         Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mMovies .add(dataSnapshot.getValue(Movies.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onBindViewHolder(@NonNull final FirebaseMovieViewHolder firebaseMovieViewHolder, int position, @NonNull Movies movies) {
        firebaseMovieViewHolder.bindMovies(movies);
        firebaseMovieViewHolder.mMovieImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(firebaseMovieViewHolder);
                }
                return false;
            }
        });

        firebaseMovieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("position", firebaseMovieViewHolder.getAdapterPosition());
                intent.putExtra("movies", Parcels.wrap(mMovies));
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public FirebaseMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_drag, parent, false);
        return new FirebaseMovieViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mMovies, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }


    @Override
    public void stopListening() { super.stopListening(); mRef.removeEventListener(mChildEventListener); }

    @Override
    public void onItemDismiss(int position) {
        mMovies .remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Movies movies : mMovies) {
            int index = mMovies.indexOf(movies);
            DatabaseReference ref = getRef(index);
            movies.setIndex(Integer.toString(index));
            ref.setValue(movies);
        }
    }
}