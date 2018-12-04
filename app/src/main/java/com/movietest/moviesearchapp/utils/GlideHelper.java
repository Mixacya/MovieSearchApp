package com.movietest.moviesearchapp.utils;

import android.content.Context;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.movietest.moviesearchapp.R;

public class GlideHelper {

    private static RequestOptions requestOptions;

    public static RequestOptions getDefaultOptions(final Context context) {
        if (requestOptions == null) {
            requestOptions = new RequestOptions()
                    .fitCenter()
                    .placeholder(R.drawable.ic_launcher_background)
                    .priority(Priority.HIGH);
        }
        return requestOptions;
    }
}
