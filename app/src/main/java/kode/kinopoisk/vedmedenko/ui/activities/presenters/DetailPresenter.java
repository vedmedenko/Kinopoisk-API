package kode.kinopoisk.vedmedenko.ui.activities.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import kode.kinopoisk.vedmedenko.R;
import kode.kinopoisk.vedmedenko.core.rest.models.getFilm.Film;
import kode.kinopoisk.vedmedenko.core.services.LoadFilmService;
import kode.kinopoisk.vedmedenko.injection.ActivityContext;
import kode.kinopoisk.vedmedenko.ui.activities.base.BasePresenter;
import kode.kinopoisk.vedmedenko.ui.activities.views.DetailMvpView;
import kode.kinopoisk.vedmedenko.utils.ConstantsManager;
import timber.log.Timber;

public class DetailPresenter extends BasePresenter<DetailMvpView> {

    private final Context context;

    private BroadcastReceiver receiver;

    @Inject
    public DetailPresenter(@NonNull @ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public void attach(DetailMvpView mvpView) {
        super.attach(mvpView);
        registerReceiver();
    }

    @Override
    public void detach() {
        super.detach();
        deregisterReciever();
    }

    public void loadMovie(@NonNull String filmID) {
        LoadFilmService.startService(context, filmID);
    }

    private void registerReceiver() {
        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {

                Film film = intent.getParcelableExtra(ConstantsManager.EXTRA_FILM);

                getMvpView().showPoster(context.getString(R.string.posterURL, film.filmID));
                getMvpView().setupViewPager(film);
                getMvpView().showProgress(false);
            }
        };

        IntentFilter intentFilter = new IntentFilter(ConstantsManager.BROADCAST_FILM);
        context.registerReceiver(receiver, intentFilter);
    }

    private void deregisterReciever() {
        try {
            context.unregisterReceiver(receiver);
        } catch (IllegalArgumentException | NullPointerException e) {
            Timber.d("Error while deregisteringRecieverData");
        }
    }
}