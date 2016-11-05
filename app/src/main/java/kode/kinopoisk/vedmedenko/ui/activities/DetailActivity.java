package kode.kinopoisk.vedmedenko.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kode.kinopoisk.vedmedenko.R;
import kode.kinopoisk.vedmedenko.core.rest.models.getFilm.Film;
import kode.kinopoisk.vedmedenko.ui.activities.base.BaseActivity;
import kode.kinopoisk.vedmedenko.ui.activities.presenters.DetailPresenter;
import kode.kinopoisk.vedmedenko.ui.activities.views.DetailMvpView;
import kode.kinopoisk.vedmedenko.ui.fragments.DetailFragment;
import kode.kinopoisk.vedmedenko.utils.ConstantsManager;
import kode.kinopoisk.vedmedenko.utils.NetworkUtil;

public class DetailActivity extends BaseActivity implements DetailMvpView {

    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.image_movie)
    ImageView imageMovie;

    @BindView(R.id.pager_movie_detail)
    ViewPager mMovieDetailPager;

    @BindView(R.id.progress_indicator)
    ProgressBar mProgressBar;

    @BindView(R.id.text_view_hint)
    TextView hint;

    @Inject
    DetailPresenter mDetailPresenter;

    public static Intent getStartIntent(@NonNull Context context, @NonNull String filmID) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ConstantsManager.FILM_ID, filmID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent().inject(this);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setupToolbar();

        mDetailPresenter.attach(this);

        String filmID = getIntent().getStringExtra(ConstantsManager.FILM_ID);

        if (NetworkUtil.isNetworkConnected(this)) {
            showProgress(true);
            mDetailPresenter.loadMovie(filmID);
        } else {
            showHint(R.string.text_no_data);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.label_detail));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDetailPresenter.detach();
    }

    @Override
    public void setupViewPager(@NonNull final Film movie) {
        mMovieDetailPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            String[] titles = getResources().getStringArray(R.array.detail_fragment_titles);

            @Override
            public Fragment getItem(int position) {
                ArrayList<String> details = new ArrayList<>();
                final Context context = getBaseContext();

                switch (position) {
                    case 0:
                        details.add(context.getString(R.string.detail_fragment_name, movie.nameRU));
                        details.add(context.getString(R.string.detail_fragment_genre, movie.genre));
                        details.add(context.getString(R.string.detail_fragment_year, movie.year));
                        details.add(context.getString(R.string.detail_fragment_slogan, movie.slogan));
                        return DetailFragment.newInstance(details);
                    case 1:
                        details.add(context.getString(R.string.detail_fragment_ratingMPAA, movie.ratingMPAA));
                        details.add(context.getString(R.string.detail_fragment_ratingAge, movie.ratingAgeLimits));
                        details.add(context.getString(R.string.detail_fragment_plot, movie.description));
                        details.add(context.getString(R.string.detail_fragment_filmLength, movie.filmLength));
                        details.add(context.getString(R.string.detail_fragment_country, movie.country));
                        return DetailFragment.newInstance(details);

                    /* TODO: создать модель Film.creators, сделать вкладку информации о людях, принимавших участие в создание фильма */

                    /*case 2:
                        details.add("Director: ", movie.director());
                        details.add("Writer: ", movie.writer());
                        details.add("Actors: ", movie.actors());
                        details.add("Country: ", movie.country());
                        return DetailFragment.newInstance(details, "");*/

                    case 2:
                        details.add(context.getString(R.string.detail_fragment_isIMAX, movie.isIMAX == 1));
                        details.add(context.getString(R.string.detail_fragment_is3D, movie.is3D == 1));
                        details.add(context.getString(R.string.detail_fragment_imdbVotes, movie.ratingData.ratingIMDbVoteCount));
                        details.add(context.getString(R.string.detail_fragment_imdbRating, movie.ratingData.ratingIMDb));
                        details.add(context.getString(R.string.detail_fragment_criticsVotes, movie.ratingData.ratingFilmCriticsVoteCount));
                        details.add(context.getString(R.string.detail_fragment_criticsRating, movie.ratingData.ratingFilmCritics));
                        return DetailFragment.newInstance(details);
                    default:
                        return DetailFragment.newInstance(details);
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });

        mTabLayout.setupWithViewPager(mMovieDetailPager);
    }

    @Override
    public void showPoster(@NonNull String posterURL) {
        imageMovie.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(posterURL)
                .into(imageMovie);
    }

    @Override
    public void showHint(@StringRes int resID) {
        mProgressBar.setVisibility(View.GONE);
        hint.setText(getResources().getString(resID));
        mMovieDetailPager.setVisibility(View.GONE);
        imageMovie.setVisibility(View.GONE);
        hint.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(boolean flag) {
        if (flag) {
            mMovieDetailPager.setVisibility(View.GONE);
            hint.setVisibility(View.GONE);
            imageMovie.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mMovieDetailPager.setVisibility(View.VISIBLE);
            imageMovie.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
