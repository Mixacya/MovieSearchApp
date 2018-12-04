package com.movietest.moviesearchapp.mvp.model;

import android.support.v7.app.AlertDialog;

import com.movietest.moviesearchapp.BuildConfig;
import com.movietest.moviesearchapp.db.DBManager;
import com.movietest.moviesearchapp.db.dao.MovieInfo;
import com.movietest.moviesearchapp.db.dto.MovieInfoDTO;
import com.movietest.moviesearchapp.mvp.presenter.MovieDetailsPresenter;
import com.movietest.moviesearchapp.rest.ApiManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieInfoModel {

    private final MovieDetailsPresenter presenter;
    private MovieInfoDTO movieInfoDTO;

    public MovieInfoModel(final MovieDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    public void findMovieInfo(final String omdbId) {
        if (omdbId == null || omdbId.isEmpty()) {
            return;
        }
        final MovieInfo movieInfo = DBManager.getInstance().getSavedMovie(omdbId);
        if (movieInfo != null) {
            movieInfoDTO = MovieInfoDTO.fromMovieInfo(movieInfo);
            bindData();
            return;
        }
        presenter.getViewState().showLoading();
        ApiManager.getInstance().getSearchResponse()
                .movieInformation(BuildConfig.OMDB_API_KEY, omdbId)
                .enqueue(new Callback<MovieInfoDTO>() {
                    @Override
                    public void onResponse(final Call<MovieInfoDTO> call, final Response<MovieInfoDTO> response) {
                        movieInfoDTO = response.body();
                        bindData();
                    }

                    @Override
                    public void onFailure(final Call<MovieInfoDTO> call, final Throwable t) {
                        presenter.getViewState().hideLoading();
                    }
                });
    }

    public void bindData() {
        if (movieInfoDTO != null) {
            presenter.getViewState().bindData(movieInfoDTO);
            final MovieInfo movieInfo = DBManager.getInstance().getSavedMovie(movieInfoDTO.getImdbID());
            presenter.getViewState().hideLoading();
            presenter.getViewState().setIsMovieSaved(movieInfo != null);
        }
    }

    public void saveMovie() {
        DBManager.getInstance().createMovieInfo(movieInfoDTO);
        presenter.getViewState().setIsMovieSaved(true);
    }

}
