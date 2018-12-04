package com.movietest.moviesearchapp.ui.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.movietest.moviesearchapp.R;
import com.movietest.moviesearchapp.db.dto.SearchDTO;
import com.movietest.moviesearchapp.ui.OnItemClickListener;
import com.movietest.moviesearchapp.utils.GlideHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_release)
    TextView tvRelease;

    private OnItemClickListener onItemClickListener;
    private SearchDTO search;

    public SearchResultViewHolder(@NonNull final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(search);
                }
            }
        });
    }

    public void bindData(final SearchDTO searchDTO) {
        if (searchDTO != null) {
            this.search = searchDTO;
            tvTitle.setText(searchDTO.getTitle());
            tvType.setText(searchDTO.getType());
            tvRelease.setText(searchDTO.getYear());

            final Context context = ivPoster.getContext();

            Glide.with(context)
                    .applyDefaultRequestOptions(GlideHelper.getDefaultOptions(context))
                    .load(searchDTO.getPoster())
                    .into(ivPoster);


        }
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
