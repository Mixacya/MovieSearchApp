package com.movietest.moviesearchapp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.movietest.moviesearchapp.R;
import com.movietest.moviesearchapp.db.dto.SearchDTO;
import com.movietest.moviesearchapp.mvp.presenter.ListFragmentPresenter;
import com.movietest.moviesearchapp.mvp.view.ListFragmentView;
import com.movietest.moviesearchapp.ui.OnItemClickListener;
import com.movietest.moviesearchapp.ui.PaginationScrollListener;
import com.movietest.moviesearchapp.ui.activity.MovieDetailsActivity;
import com.movietest.moviesearchapp.ui.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListFragment extends BaseFragment implements ListFragmentView {

    private static final String EXTRA_PAGE_TYPE = "EXTRA_PAGE_TYPE";

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_search)
    ImageButton btnSearch;
    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    @InjectPresenter
    ListFragmentPresenter presenter;

    private ListAdapter searchListAdapter;
    private final List<SearchDTO> searchDTOList = new ArrayList<>();

    private boolean hasNoMoreData;
    private boolean isLoading;

    public static ListFragment newInstance(final int type) {
        final ListFragment listFragment = new ListFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_PAGE_TYPE, type);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle args = getArguments();
        if (args != null) {
            presenter.setType(args.getInt(EXTRA_PAGE_TYPE));
        }

        LinearLayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 2);
        }
        rvItems.setLayoutManager(layoutManager);
        final int itemPadding = getContext().getResources().getDimensionPixelSize(R.dimen.half_8dp);
        rvItems.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull final Rect outRect, @NonNull final View view, @NonNull final RecyclerView parent, @NonNull final RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = itemPadding;
                outRect.left = itemPadding;
                outRect.right = itemPadding;
                if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = itemPadding;
                }
            }
        });
        rvItems.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                presenter.loadMore();
            }

            @Override
            public int getTotalPageCount() {
                return 100;
            }

            @Override
            public boolean isLastPage() {
                return hasNoMoreData;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        switch (presenter.getType()) {
            case TYPE_SAVED_MOVIES:
                backToSaved();
                presenter.loadMore();
                break;
            case TYPE_SEARCH_MOVIES:
                backToSearch();
                break;
        }
    }

    @OnClick(R.id.btn_search)
    public void onSearchClick() {
        hideKeyboard(etSearch);
        showProgress();
        final String line = etSearch.getText().toString().trim();
        if (line == null || line.isEmpty()) {
            return;
        }
        presenter.requestSearch(line);
    }

    @Override
    public void bindData(final List<SearchDTO> searchDTOList) {
        if (presenter.getPage() <= 1) {
            this.searchDTOList.clear();
        }
        if (searchDTOList != null) {
            this.searchDTOList.addAll(searchDTOList);
        }
        if (searchListAdapter == null) {
            searchListAdapter = new ListAdapter(this.searchDTOList, onItemClick);
            rvItems.setAdapter(searchListAdapter);
        } else {
            searchListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void goList() {
        etSearch.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
        hideProgress();
        rvItems.setVisibility(View.VISIBLE);
    }

    @Override
    public void setHasNoMoreResults(final boolean hasNoMoreResults) {
        this.hasNoMoreData = hasNoMoreResults;
    }

    @Override
    public void setIsLoading(final boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public boolean onBackPressed() {
        if (presenter.getType() == TYPE_SEARCH_MOVIES && rvItems.getVisibility() == View.VISIBLE) {
            backToSearch();
            return true;
        }
        return super.onBackPressed();
    }

    private void backToSaved() {
        rvItems.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
        if (pbLoading.getVisibility() == View.VISIBLE) {
            hideProgress();
        }
    }

    private void backToSearch() {
        rvItems.setVisibility(View.GONE);
        etSearch.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.VISIBLE);
        if (pbLoading.getVisibility() == View.VISIBLE) {
            hideProgress();
        }
    }

    private OnItemClickListener onItemClick = searchDTO -> {
        if (searchDTO != null) {
            final Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.SEARCH_KEY, searchDTO);
            startActivity(intent);
        }
    };

}
