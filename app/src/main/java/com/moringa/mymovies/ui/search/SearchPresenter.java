package com.moringa.mymovies.ui.search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;


import com.moringa.mymovies.models.MoviesResponse;
import com.moringa.mymovies.networks.NetworkClient;
import com.moringa.mymovies.networks.NetworkInterface;

import java.util.concurrent.TimeUnit;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.moringa.mymovies.constants.Constant.TMDB_TOKEN;
import io.reactivex.functions.Predicate;
import io.reactivex.ObservableSource;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.functions.Function;

import io.reactivex.observers.DisposableObserver;


public class SearchPresenter implements SearchPresenterInterface {
    private String TAG = "SearchPresenter";
    SearchViewInterface searchviewInterface;

    public SearchPresenter(SearchViewInterface searchViewInterface) {
        this.searchviewInterface = searchViewInterface;
    }

    @Override
    public void getResultsBasedOnQuery(SearchView searchView) {

        getObservableQuery(searchView)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        if(s.equals("")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                })
                .debounce(2, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<MoviesResponse>>() {
                    @Override
                    public Observable<MoviesResponse> apply(@NonNull String s) throws Exception {
                        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                                .getMoviesBasedOnQuery(TMDB_TOKEN ,s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());


    }

    private Observable<String> getObservableQuery(SearchView searchView){

        final PublishSubject<String> publishSubject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                publishSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                publishSubject.onNext(newText);
                return true;
            }
        });

        return publishSubject;
    }

    public DisposableObserver<MoviesResponse> getObserver(){
        return new DisposableObserver<MoviesResponse>() {

            @Override
            public void onNext(@NonNull MoviesResponse MoviesResponse) {
                Log.d(TAG,"OnNext"+ MoviesResponse.getTotalResults());
                searchviewInterface.displayResult(MoviesResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                searchviewInterface.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }
}
