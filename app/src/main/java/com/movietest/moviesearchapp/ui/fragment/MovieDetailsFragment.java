package com.movietest.moviesearchapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.movietest.moviesearchapp.R;
import com.movietest.moviesearchapp.db.DBManager;
import com.movietest.moviesearchapp.db.dto.MovieInfoDTO;
import com.movietest.moviesearchapp.db.dto.RatingDTO;
import com.movietest.moviesearchapp.db.dto.SearchDTO;
import com.movietest.moviesearchapp.mvp.presenter.MovieDetailsPresenter;
import com.movietest.moviesearchapp.mvp.view.MovieDetailsView;
import com.movietest.moviesearchapp.utils.GlideHelper;

import butterknife.BindView;

public class MovieDetailsFragment extends BaseFragment implements MovieDetailsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.iv_saved)
    ImageView ivSaved;
    @BindView(R.id.tv_release)
    TextView tvRelease;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_director)
    TextView tvDirection;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    @InjectPresenter
    MovieDetailsPresenter presenter;

    private SearchDTO searchDTO;
    private MenuItem saveMenuItem;
    private boolean isSaved = false;

    public static MovieDetailsFragment newInstance(final SearchDTO searchDTO) {
        final MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.searchDTO = searchDTO;
        return movieDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);

        setHasOptionsMenu(true);

        presenter.updateUI();
        presenter.findMovie(searchDTO);
    }

    @Override
    public void bindData(final MovieInfoDTO dto) {
        if (dto == null) {
            return;
        }
        Glide.with(getContext())
                .applyDefaultRequestOptions(GlideHelper.getDefaultOptions(getContext()))
                .load(dto.getPoster())
                .into(ivPoster);

        getActivity().setTitle(dto.getTitle());
        tvRelease.setText(dto.getRelease());
        tvDirection.setText(dto.getDirector());
        tvSummary.setText(dto.getPlot());
        if (dto.getRatingList() != null && !dto.getRatingList().isEmpty()) {
            final RatingDTO rating = dto.getRatingList().get(0);
            tvRating.setText(rating.getValue());
        }
    }

    @Override
    public void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void setIsMovieSaved(final boolean isSaved) {
        this.isSaved = isSaved;
        if (saveMenuItem != null) {
            saveMenuItem.setVisible(!isSaved);
        }
        ivSaved.setVisibility(isSaved ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_movie_details, menu);
        saveMenuItem = menu.findItem(R.id.action_save);
        saveMenuItem.setVisible(!isSaved);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                presenter.saveMovie();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
