package kode.kinopoisk.vedmedenko.ui.activities.views;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import kode.kinopoisk.vedmedenko.core.rest.models.getFilm.Film;
import kode.kinopoisk.vedmedenko.ui.activities.base.MvpView;

public interface DetailMvpView extends MvpView {
    void setupViewPager(@NonNull final Film movie);
    void showHint(@StringRes int resID);
    void showPoster(@NonNull String posterURL);
    void showProgress(boolean flag);
}
