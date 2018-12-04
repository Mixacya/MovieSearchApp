package com.movietest.moviesearchapp.mvp.model;

import android.os.AsyncTask;
import android.os.Looper;

import com.movietest.moviesearchapp.BuildConfig;
import com.movietest.moviesearchapp.db.DBManager;
import com.movietest.moviesearchapp.db.dao.MovieInfo;
import com.movietest.moviesearchapp.db.dao.Search;
import com.movietest.moviesearchapp.db.dto.ParentSearch;
import com.movietest.moviesearchapp.db.dto.SearchDTO;
import com.movietest.moviesearchapp.mvp.presenter.ListFragmentPresenter;
import com.movietest.moviesearchapp.rest.ApiManager;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragmentModel {

    private final ListFragmentPresenter presenter;
    private List<SearchDTO> resultList;

    public ListFragmentModel(final ListFragmentPresenter presenter) {
        this.presenter = presenter;
    }

    public void readSavedMovies() {
        resultList = DBManager.getInstance().getSearchList();
        presenter.bindModelList();
    }

    public void search(final String search, final int page) {
        presenter.getViewState().setIsLoading(true);
        ApiManager.getInstance().getSearchResponse().searchList(BuildConfig.OMDB_API_KEY, search, page).enqueue(new Callback<ParentSearch>() {
            @Override
            public void onResponse(final Call<ParentSearch> call, final Response<ParentSearch> response) {
                presenter.getViewState().setIsLoading(false);
                resultList = response.body().getSearchList();
                if (resultList != null) {
                    presenter.getViewState().bindData(resultList);
                    presenter.getViewState().goList();
                } else {
                    presenter.getViewState().setHasNoMoreResults(true);
                    presenter.getViewState().hideProgress();
                }
            }

            @Override
            public void onFailure(final Call<ParentSearch> call, final Throwable t) {
                presenter.getViewState().setIsLoading(false);
            }
        });
    }

    public List<SearchDTO> getResultList() {
        return resultList;
    }

}
