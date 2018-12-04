package com.movietest.moviesearchapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.movietest.moviesearchapp.db.dto.SearchDTO;

import java.util.List;

public interface ListFragmentView extends MvpView {

    int TYPE_SAVED_MOVIES = 0;
    int TYPE_SEARCH_MOVIES = 1;

    void setHasNoMoreResults(boolean hasNoMoreResults);

    void setIsLoading(boolean isLoading);

    void bindData(final List<SearchDTO> searchDTOList);

    void showProgress();

    void hideProgress();

    void goList();

}
