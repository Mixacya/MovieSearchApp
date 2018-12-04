package com.movietest.moviesearchapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.movietest.moviesearchapp.db.dto.SearchDTO;
import com.movietest.moviesearchapp.mvp.model.MovieInfoModel;
import com.movietest.moviesearchapp.mvp.view.MovieDetailsView;

@InjectViewState
public class MovieDetailsPresenter extends MvpPresenter<MovieDetailsView> {

    private MovieInfoModel model;

    public MovieDetailsPresenter() {
        model = new MovieInfoModel(this);
    }

    public void findMovie(final SearchDTO searchDTO) {
        if (searchDTO != null) {
            model.findMovieInfo(searchDTO.getImdbID());
        }
    }

    public void updateUI() {
        model.bindData();
    }

    public void saveMovie() {
        model.saveMovie();
    }

}
