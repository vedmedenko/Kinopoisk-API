package kode.kinopoisk.vedmedenko.ui.activities.views;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.List;

import kode.kinopoisk.vedmedenko.core.rest.models.getSoonFilms.SoonFilm;
import kode.kinopoisk.vedmedenko.ui.activities.base.MvpView;

public interface MainMvpView extends MvpView {
    void setAdapters(@NonNull List<String> months,
                     @NonNull List<String> years,
                     @NonNull List<String> countries,
                     @NonNull List<String> genres);

    void setCityAdapter(@NonNull List<String> cities);
    void showProgress(boolean flag);
    void setSoonFilms(@NonNull List<SoonFilm> movies);
    void addSoonFilms(@NonNull List<SoonFilm> movies);
    void showHint(@StringRes int resid, boolean btnReloadVisibility);
    String getGenreSort();
    boolean getRatingSort();
}
