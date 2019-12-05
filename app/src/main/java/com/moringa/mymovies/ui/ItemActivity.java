package com.moringa.mymovies.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;


import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.moringa.mymovies.R;
import com.moringa.mymovies.adapters.MoviesAdapter;
import com.moringa.mymovies.models.Genres;
import com.moringa.mymovies.models.Movies;
import com.moringa.mymovies.networks.MovieRep;
import com.moringa.mymovies.services.GetGenreCallBack;
import com.moringa.mymovies.services.GetMovieCallBack;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


import static com.moringa.mymovies.constants.Constant.POPULAR;
import static com.moringa.mymovies.constants.Constant.TOP_RATED;
import static com.moringa.mymovies.constants.Constant.UPCOMING;


public class ItemActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String sortBy = POPULAR;

    private MoviesAdapter adapter;
    private MovieRep movieRep;
    private List<Genres> movieGenres;
    private boolean isFetchingMovies;
    private int currentPage = 1;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        movieRep = MovieRep.getInstance();

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Popular movies");
        setupOnScrollListener();
        getGenres();

    }

    private void setupOnScrollListener() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetchingMovies) {
                        getMovies(currentPage + 1);
                    }
                }
            }
        });
    }

    private void getGenres() {
        movieRep.getGenres(new GetGenreCallBack() {
            @Override
            public void onSuccess(List<Genres> genres) {
                movieGenres = genres;
                getMovies(currentPage);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void getMovies(int page) {
        isFetchingMovies = true;
        movieRep.getMovies(page, sortBy, new GetMovieCallBack() {
            @Override
            public void onSuccess(int page, List<Movies> movies) {
                Log.d("MovieRep", "Current Page = " + page);
                if (adapter == null) {
                    adapter = new MoviesAdapter(movies, movieGenres);
                    mRecyclerView.setAdapter(adapter);
                } else {
                    if (page == 1) {
                        adapter.clearMovies();
                    }
                    adapter.appendMovies(movies);
                }
                currentPage = page;
                isFetchingMovies = false;

                setTitle();
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void setTitle() {
        switch (sortBy) {
            case POPULAR:
                setTitle(getString(R.string.popular));
                break;
            case TOP_RATED:
                setTitle(getString(R.string.top_rated));
                break;
            case UPCOMING:
                setTitle(getString(R.string.upcoming));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                showSortMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortMenu() {
        PopupMenu sortMenu = new PopupMenu(this, findViewById(R.id.sort));
        sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                /*
                 * Every time we sort, we need to go back to page 1
                 */
                currentPage = 1;

                switch (item.getItemId()) {
                    case R.id.popular:
                        sortBy = POPULAR;
                        getMovies(currentPage);
                        return true;
                    case R.id.top_rated:
                        sortBy = TOP_RATED;
                        getMovies(currentPage);
                        return true;
                    case R.id.upcoming:
                        sortBy = UPCOMING;
                        getMovies(currentPage);
                        return true;
                    default:
                        return false;
                }
            }
        });
        sortMenu.inflate(R.menu.menu_movies_sort);
        sortMenu.show();
    }


    private void showError() {
        Toast.makeText(ItemActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }
}