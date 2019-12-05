package com.moringa.mymovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.moringa.mymovies.R;
import com.moringa.mymovies.models.Movies;
import com.moringa.mymovies.models.Result;

import java.util.List;

import static com.moringa.mymovies.constants.Constant.IMAGE_BASE_URL;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviesHolder> {

    List<Result> movieList;
    Context context;

    public MovieAdapter(List<Result> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_items,parent,false);
        MoviesHolder mh = new MoviesHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {
        holder.bind(movieList.get(position));
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+movieList.get(position).getPosterPath()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MoviesHolder extends RecyclerView.ViewHolder {

        TextView releaseDate;
        TextView title;
        TextView rating;
        ImageView poster;
        TextView genres;


        public MoviesHolder(View v) {
            super(v);
            releaseDate = itemView.findViewById(R.id.item_movie_release_date);
            title = itemView.findViewById(R.id.item_movie_title);
            rating = itemView.findViewById(R.id.item_movie_rating);
            poster = itemView.findViewById(R.id.item_movie_poster);
            genres = itemView.findViewById(R.id.item_movie_genre);
        }
        public void bind(Result result) {

            releaseDate.setText(result.getReleaseDate().split("-")[0]);
            title.setText(result.getTitle());
            rating.setText(String.valueOf(result.getVoteCount()));
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + result.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(poster);
        }
    }



}