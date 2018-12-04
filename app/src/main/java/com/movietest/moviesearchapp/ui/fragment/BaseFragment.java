package com.movietest.moviesearchapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatFragment;

import butterknife.ButterKnife;

import static android.content.Context.INPUT_METHOD_SERVICE;

public abstract class BaseFragment extends MvpAppCompatFragment {

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public boolean onBackPressed() {
        return false;
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
