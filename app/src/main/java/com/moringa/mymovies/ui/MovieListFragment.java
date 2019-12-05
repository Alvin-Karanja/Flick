package com.moringa.mymovies.ui;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moringa.mymovies.R;
import com.moringa.mymovies.adapters.MoviesAdapter;
import com.moringa.mymovies.models.Genres;
import com.moringa.mymovies.models.Movies;
import com.moringa.mymovies.networks.MovieRep;
import com.moringa.mymovies.services.GetMovieCallBack;
import com.moringa.mymovies.ui.util.OnStartDragListene;
import com.moringa.mymovies.ui.util.SimpleItemTouchHelperCallback;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment implements OnStartDragListene {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    private MoviesAdapter adapter;
    private MovieRep movieRep;
    private List<Movies> mMovies;
    private List<Genres> movieGenres;
    private boolean isFetchingMovies;
    private int currentPage = 1;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;



    private ItemTouchHelper mItemTouchHelper;
    public MovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        // Instructs fragment to include menu options:
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
