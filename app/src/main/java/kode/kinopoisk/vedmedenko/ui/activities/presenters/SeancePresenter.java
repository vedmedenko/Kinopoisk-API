package kode.kinopoisk.vedmedenko.ui.activities.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import kode.kinopoisk.vedmedenko.core.rest.models.getSeance.Seance;
import kode.kinopoisk.vedmedenko.core.services.LoadSeancesService;
import kode.kinopoisk.vedmedenko.injection.ActivityContext;
import kode.kinopoisk.vedmedenko.ui.activities.base.BasePresenter;
import kode.kinopoisk.vedmedenko.ui.activities.views.SeanceMvpView;
import kode.kinopoisk.vedmedenko.utils.ConstantsManager;
import timber.log.Timber;

public class SeancePresenter extends BasePresenter<SeanceMvpView> {

    private final Context context;
    private BroadcastReceiver receiver;

    @Inject
    public SeancePresenter(@NonNull @ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public void attach(SeanceMvpView mvpView) {
        super.attach(mvpView);
        registerReceiver();
    }

    @Override
    public void detach() {
        super.detach();
        deregisterReciever();
    }

    public void loadSeances(@NonNull String filmID, int cityID, @NonNull String date) {
        getMvpView().showProgress(true);
        LoadSeancesService.startService(context, filmID, cityID, date);
    }

    private void registerReceiver() {
        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                Seance seance = intent.getParcelableExtra(ConstantsManager.EXTRA_SEANCE);

                getMvpView().setupContent(seance);
                getMvpView().showProgress(false);
            }
        };

        IntentFilter intentFilter = new IntentFilter(ConstantsManager.BROADCAST_SEANCE);
        context.registerReceiver(receiver, intentFilter);
    }

    private void deregisterReciever() {
        try {
            context.unregisterReceiver(receiver);
        } catch (IllegalArgumentException | NullPointerException e) {
            Timber.d("Error while deregisteringRecieverSeances");
        }
    }

}
