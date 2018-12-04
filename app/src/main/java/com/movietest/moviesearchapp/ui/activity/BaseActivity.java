package com.movietest.moviesearchapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        onViewCreated(savedInstanceState);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void onViewCreated(final Bundle savedInstanceState);

}
