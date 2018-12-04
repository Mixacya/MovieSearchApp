package com.movietest.moviesearchapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.movietest.moviesearchapp.db.dto.SearchDTO;
import com.movietest.moviesearchapp.mvp.model.ListFragmentModel;
import com.movietest.moviesearchapp.mvp.view.ListFragmentView;

import java.util.List;

@InjectViewState
public class ListFragmentPresenter extends MvpPresenter<ListFragmentView> {

    private ListFragmentModel listModel;
    private String searchWord;
    private int page;
    private int type = -1;

    public ListFragmentPresenter() {
        listModel = new ListFragmentModel(this);
    }

    public void requestSearch(final String query) {
        searchWord = query;
        page = 1;
        getViewState().setHasNoMoreResults(false);
        requestSearch();
    }

    public void requestSearch() {
        listModel.search(searchWord, page);
    }

    public void loadMore() {
        if (type == ListFragmentView.TYPE_SEARCH_MOVIES) {
            ++page;
            requestSearch();
        } else {
            listModel.readSavedMovies();
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
    }

    public void bindModelList() {
        List<SearchDTO> list = listModel.getResultList();
        if (list != null && !list.isEmpty()) {
            getViewState().goList();
            getViewState().bindData(list);
        }
    }
}
