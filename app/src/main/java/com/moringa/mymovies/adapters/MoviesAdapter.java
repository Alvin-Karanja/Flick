package com.moringa.mymovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.moringa.mymovies.R;
import com.moringa.mymovies.models.Genres;
import com.moringa.mymovies.models.Movies;
import com.moringa.mymovies.ui.MovieDetailActivity;
import com.moringa.mymovies.ui.util.ItemTouchHelperAdapter;


import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.moringa.mymovies.constants.Constant.IMAGE_BASE_URL;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> implements ItemTouchHelperAdapter {
    private List<Movies> mMovies;
    private List<Genres> allGenres;


    public MoviesAdapter(List<Movies> movies, List<Genres> allGenres) {
        mMovies = movies;
        this.allGenres = allGenres;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void appendMovies(List<Movies> moviesToAppend) {
        mMovies.addAll(moviesToAppend);
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView releaseDate;
        TextView title;
        TextView rating;
        ImageView poster;
        TextView genres;

        private Context mContext;

        public MovieViewHolder(View itemView) {
            super(itemView);
            releaseDate = itemView.findViewById(R.id.item_movie_release_date);
            title = itemView.findViewById(R.id.item_movie_title);
            rating = itemView.findViewById(R.id.item_movie_rating);
            poster = itemView.findViewById(R.id.item_movie_poster);
            genres = itemView.findViewById(R.id.item_movie_genre);

            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext,MovieDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("movies", Parcels.wrap(mMovies));
            mContext.startActivity(intent);
        }

        public void bind(Movies movies) {
//            this.movies = movies;
            releaseDate.setText(movies.getReleaseDate().split("-")[0]);
            title.setText(movies.getTitle());
            rating.setText(String.valueOf(movies.getRating()));
            genres.setText(getGenres(movies.getGenreIds()));
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movies.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(poster);
        }

        private String getGenres(List<Integer> genreIds) {
            List<String> movieGenres = new ArrayList<>();
            for (Integer genreId : genreIds) {
                for (Genres genre : allGenres) {
                    if (genre.getId() == genreId) {
                        movieGenres.add(genre.getName());
                        break;
                    }
                }
            }
            return TextUtils.join(", ", movieGenres);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        mMovies.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mMovies, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mMovies, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
    public void clearMovies() {
        mMovies.clear();
        notifyDataSetChanged();
    }


}
