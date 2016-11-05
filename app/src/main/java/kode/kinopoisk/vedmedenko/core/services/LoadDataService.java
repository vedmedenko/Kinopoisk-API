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
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class LoadDataService extends Service {

    @Inject
    protected DataManager dataManager;

    private WeakReference<Context> contextWeakReference;
    private CompositeSubscription mCompositeSubscription;

    public static void startService(@NonNull @ActivityContext Context context) {
        context.startService(new Intent(context, LoadDataService.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        contextWeakReference = new WeakReference<>(getBaseContext());
        KinopoiskApplication.get(contextWeakReference).getComponent().inject(this);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent broadcast = new Intent(ConstantsManager.BROADCAST_DATA);

        mCompositeSubscription.add(dataManager.loadDatesForSoonFilms()
                .subscribeOn(Schedulers.io())
                .doAfterTerminate(() -> {
                    stopSelf(startId);})
                .subscribe(datesForSoonFilms -> {
                    broadcast.putStringArrayListExtra(ConstantsManager.EXTRA_LIST_DATES, datesForSoonFilms.getDates());
                    sendBroadcast(broadcast);
                }, throwable -> {
                    Timber.e(throwable, "Error while loading data occurred!");
                }));

        mCompositeSubscription.add(dataManager.loadCountryList()
                .subscribeOn(Schedulers.io())
                .doAfterTerminate(() -> {
                    stopSelf(startId);})
                .subscribe(countryList -> {
                    broadcast.putParcelableArrayListExtra(ConstantsManager.EXTRA_LIST_COUNTRIES, countryList.getCountries());
                    sendBroadcast(broadcast);
                }, throwable -> {
                    Timber.e(throwable, "Error while loading data occurred!");
                }));

        mCompositeSubscription.add(dataManager.loadGenres()
                .subscribeOn(Schedulers.io())
                .doAfterTerminate(() -> {
                    stopSelf(startId);})
                .subscribe(genres -> {
                    broadcast.putParcelableArrayListExtra(ConstantsManager.EXTRA_LIST_GENRES, genres.getGenres());
                    sendBroadcast(broadcast);
                }, throwable -> {
                    Timber.e(throwable, "Error while loading data occurred!");
                }));

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contextWeakReference.clear();
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) mCompositeSubscription.unsubscribe();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
