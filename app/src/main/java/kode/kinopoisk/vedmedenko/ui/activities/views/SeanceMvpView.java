package kode.kinopoisk.vedmedenko.ui.activities.views;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import kode.kinopoisk.vedmedenko.core.rest.models.getSeance.Seance;
import kode.kinopoisk.vedmedenko.ui.activities.base.MvpView;

public interface SeanceMvpView extends MvpView {
    void setupContent(@NonNull Seance seance);
    void showProgress(boolean flag);
    void showHint(@StringRes int resid);
}
