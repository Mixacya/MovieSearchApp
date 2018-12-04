package com.movietest.moviesearchapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.movietest.moviesearchapp.R;
import com.movietest.moviesearchapp.db.dto.SearchDTO;
import com.movietest.moviesearchapp.ui.fragment.MovieDetailsFragment;

import butterknife.BindView;

public class MovieDetailsActivity extends BaseActivity {

    public static final String SEARCH_KEY = "searchKey";
    public static final String TAG_FRAGMENT_MOVIE_DETAILS= "FRAGMENT_MOVIE_DETAILS";

    @BindView(R.id.vp_container)
    FrameLayout vpContainer;

    private SearchDTO searchDTO;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    protected void onViewCreated(final Bundle savedInstanceState) {
        searchDTO = getIntent().getParcelableExtra(SEARCH_KEY);
        changeFragment(TAG_FRAGMENT_MOVIE_DETAILS);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(final String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = createFragmentByTag(tag);
        }
        if (fragment != null && !fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.vp_container, fragment, tag)
                    .commit();
        }
    }

    private Fragment createFragmentByTag(final String tag) {
        switch (tag) {
            case TAG_FRAGMENT_MOVIE_DETAILS:
                return MovieDetailsFragment.newInstance(searchDTO);
        }
        return null;
    }
}
