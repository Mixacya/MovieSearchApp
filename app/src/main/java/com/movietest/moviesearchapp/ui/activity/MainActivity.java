package com.movietest.moviesearchapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.movietest.moviesearchapp.R;
import com.movietest.moviesearchapp.mvp.view.ListFragmentView;
import com.movietest.moviesearchapp.ui.fragment.BaseFragment;
import com.movietest.moviesearchapp.ui.fragment.ListFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_FRAGMENT_SAVED_MOVIES = "FRAGMENT_SAVED_MOVIES";
    private static final String TAG_FRAGMENT_SEARCH_TITLE = "FRAGMENT_SEARCH_TITLE";
    private static final String EXTRA_LAST_PAGE = "EXTRA_LAST_PAGE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.fl_container)
    FrameLayout container;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private String selectedPageTag = null;

    @LayoutRes
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated(final Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_navigation, R.string.close_navigation);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState != null) {
            selectedPageTag = savedInstanceState.getString(EXTRA_LAST_PAGE, TAG_FRAGMENT_SAVED_MOVIES);
        }
        if (selectedPageTag == null) {
            selectedPageTag = TAG_FRAGMENT_SAVED_MOVIES;
        }
        switch (selectedPageTag) {
            case TAG_FRAGMENT_SAVED_MOVIES:
                navigationView.getMenu().performIdentifierAction(R.id.nav_saved_movies, 0);
                break;
            case TAG_FRAGMENT_SEARCH_TITLE:
                navigationView.getMenu().performIdentifierAction(R.id.nav_search_movies, 0);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_saved_movies:
                changeFragment(TAG_FRAGMENT_SAVED_MOVIES);
                setTitle(R.string.nav_saved_movies);
                break;
            case R.id.nav_search_movies:
                changeFragment(TAG_FRAGMENT_SEARCH_TITLE);
                setTitle(R.string.nav_search_movies);
                break;
        }
        menuItem.setChecked(true);
        drawer.closeDrawers();
        return true;
    }

    @Override
    public void onBackPressed() {
        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (fragment != null && fragment instanceof BaseFragment) {
            if (((BaseFragment) fragment).onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putString(EXTRA_LAST_PAGE, selectedPageTag);
        }
    }

    private void changeFragment(final String tag) {
        selectedPageTag = tag;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = createFragmentByTag(tag);
        }
        if (fragment != null && !fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment, tag)
                    .commit();
        }
    }

    private Fragment createFragmentByTag(final String tag) {
        switch (tag) {
            case TAG_FRAGMENT_SAVED_MOVIES:
                return ListFragment.newInstance(ListFragmentView.TYPE_SAVED_MOVIES);
//            case TAG_FRAGMENT_SEARCH_DIRECTOR:
//                return ListFragment.newInstance(ListFragmentView.TYPE_SEARCH_MOVIES);
            case TAG_FRAGMENT_SEARCH_TITLE:
                return ListFragment.newInstance(ListFragmentView.TYPE_SEARCH_MOVIES);
        }
        return null;
    }

}
