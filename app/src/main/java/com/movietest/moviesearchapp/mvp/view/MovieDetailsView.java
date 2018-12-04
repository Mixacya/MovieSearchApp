package com.movietest.moviesearchapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.movietest.moviesearchapp.db.dto.MovieInfoDTO;

public interface MovieDetailsView extends MvpView {

    void bindData(MovieInfoDTO dto);
    void showLoading();
    void hideLoading();
    void setIsMovieSaved(boolean isSaved);

}
