    package com.moringa.mymovies.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.moringa.mymovies.R;
import com.moringa.mymovies.adapters.FirebaseMovieListAdapter;
import com.moringa.mymovies.adapters.FirebaseMovieViewHolder;
import com.moringa.mymovies.models.Movies;
import com.moringa.mymovies.ui.util.OnStartDragListene;
import com.moringa.mymovies.ui.util.SimpleItemTouchHelperCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.moringa.mymovies.constants.Constant.FIREBASE_CHILD_MOVIES;
import static com.moringa.mymovies.constants.Constant.FIREBASE_QUERY_INDEX;

    public class SavedMoviesListActivity extends AppCompatActivity implements OnStartDragListene {
        private DatabaseReference mMovieReference;
        private FirebaseMovieListAdapter mFirebaseAdapter;
        private ItemTouchHelper mItemTouchHelper;

        @BindView(R.id.list) RecyclerView mRecyclerView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_item);
            ButterKnife.bind(this);
            getSupportActionBar().setTitle("Saved reviews");

            setUpFirebaseAdapter();
        }

        private void setUpFirebaseAdapter(){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            Query query = FirebaseDatabase.getInstance().getReference(FIREBASE_CHILD_MOVIES).child(uid).orderByChild(FIREBASE_QUERY_INDEX);
            FirebaseRecyclerOptions<Movies> options =
                    new FirebaseRecyclerOptions.Builder<Movies>()
                            .setQuery(query, Movies.class)
                            .build();

            mFirebaseAdapter = new FirebaseMovieListAdapter(options, query, this, this);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mFirebaseAdapter);
            mRecyclerView.setHasFixedSize(true);
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        }

        @Override
        protected void onStart() {
            super.onStart();
            mFirebaseAdapter.startListening();
        }

        @Override
        protected void onStop() {
            super.onStop();
            if(mFirebaseAdapter!= null) {
                mFirebaseAdapter.stopListening();
            }
        }
        public void onStartDrag(RecyclerView.ViewHolder viewHolder){
            mItemTouchHelper.startDrag(viewHolder);
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            mFirebaseAdapter.stopListening();
        }
    }