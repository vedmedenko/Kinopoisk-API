package kode.kinopoisk.vedmedenko.core.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import kode.kinopoisk.vedmedenko.KinopoiskApplication;
import kode.kinopoisk.vedmedenko.core.DataManager;
import kode.kinopoisk.vedmedenko.injection.ActivityContext;
import kode.kinopoisk.vedmedenko.utils.ConstantsManager;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class LoadFilmService extends Service {

    @Inject
    protected DataManager dataManager;

    private WeakReference<Context> contextWeakReference;
    private Subscription subscription;

    public static void startService(@NonNull @ActivityContext Context context, @NonNull String filmID) {
        Intent intent = new Intent(context, LoadFilmService.class);
        intent.putExtra(ConstantsManager.FILM_ID, filmID);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        contextWeakReference = new WeakReference<>(getBaseContext());
        KinopoiskApplication.get(contextWeakReference).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();

        String filmID = extras.getString(ConstantsManager.FILM_ID);

        Intent broadcast = new Intent(ConstantsManager.BROADCAST_FILM);

        subscription = dataManager.loadFilm(filmID)
                .subscribeOn(Schedulers.io())
                .doAfterTerminate(() -> {
                    stopSelf(startId);
                })
                .subscribe(film -> {
                    broadcast.putExtra(ConstantsManager.EXTRA_FILM, film);
                    sendBroadcast(broadcast);
                }, throwable -> {
                    Timber.e(throwable, "Error while loading data occurred!");
                });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contextWeakReference.clear();
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
