package com.movietest.moviesearchapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movietest.moviesearchapp.R;
import com.movietest.moviesearchapp.db.dto.SearchDTO;
import com.movietest.moviesearchapp.ui.OnItemClickListener;
import com.movietest.moviesearchapp.ui.fragment.ListFragment;
import com.movietest.moviesearchapp.ui.viewholder.EmptyViewHolder;
import com.movietest.moviesearchapp.ui.viewholder.SearchResultViewHolder;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_CONTENT = 1;

    private final List<SearchDTO> searchDTOList;
    private final OnItemClickListener onItemClickListener;

    public ListAdapter(final List<SearchDTO> searchDTOList, OnItemClickListener onItemClickListener) {
        this.searchDTOList = searchDTOList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = null;
        if (i == VIEW_TYPE_CONTENT) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
            return new SearchResultViewHolder(view);
        }
        return new EmptyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof SearchResultViewHolder) {
            SearchResultViewHolder resultHolder = (SearchResultViewHolder) holder;
            resultHolder.bindData(searchDTOList.get(i));
            resultHolder.setOnItemClickListener(onItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return searchDTOList.size() > 0 ? searchDTOList.size() : 1;
    }

    @Override
    public int getItemViewType(final int position) {
        if (searchDTOList.size() > 0) {
            return VIEW_TYPE_CONTENT;
        } else {
            return 0;
        }
    }
}
