package kode.kinopoisk.vedmedenko.ui.activities.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import javax.inject.Inject;

import kode.kinopoisk.vedmedenko.R;
import kode.kinopoisk.vedmedenko.core.rest.models.getCityList.City;
import kode.kinopoisk.vedmedenko.core.rest.models.getCounrtyList.Country;
import kode.kinopoisk.vedmedenko.core.rest.models.getGenres.Genre;
import kode.kinopoisk.vedmedenko.core.rest.models.getSoonFilms.SoonFilm;
import kode.kinopoisk.vedmedenko.core.services.LoadCitiesService;
import kode.kinopoisk.vedmedenko.core.services.LoadDataService;
import kode.kinopoisk.vedmedenko.core.services.LoadSoonFilmsService;
import kode.kinopoisk.vedmedenko.injection.ActivityContext;
import kode.kinopoisk.vedmedenko.ui.activities.base.BasePresenter;
import kode.kinopoisk.vedmedenko.ui.activities.views.MainMvpView;
import kode.kinopoisk.vedmedenko.utils.ConstantsManager;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainMvpView> {

    private BroadcastReceiver broadcastReceiverData, broadcastReceiverSoonFilms;
    private Context context;

    private ArrayList<String> dates;
    private ArrayList<Country> countries;
    private ArrayList<City> cities;
    private ArrayList<Genre> genres;

    @Inject
    public MainPresenter(@NonNull @ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public void attach(MainMvpView mvpView) {
        super.attach(mvpView);
        registerRecieverData();
        registerRecieverSoonFilms();
        loadData();
    }

    @Override
    public void detach() {
        super.detach();
        deregisterRecieverData();
        deregisterRecieverSoonFilms();
    }

    public void loadData() {
        LoadDataService.startService(context);
        getMvpView().showProgress(true);
    }

    public void loadCities(String country) {
        for (Country cnt: countries) {
            if (cnt.countryName.equals(country)) {
                LoadCitiesService.startService(context, Integer.valueOf(cnt.countryID));
            }
        }
    }

    public void loadSoonFilms(@NonNull String date, @NonNull String cityName) {

        if (!dates.contains(date)) {
            getMvpView().showHint(R.string.stringErrorWrongDate, false);
            getMvpView().showProgress(false);
            return;
        }

        for (City city : cities) {
            if (cityName.equals(city.cityName))
                LoadSoonFilmsService.startService(context, date, Integer.valueOf(city.cityID));
        }
    }

    public int getCityID(@NonNull String cityName) {
        for (City city : cities) {
            if (cityName.equals(city.cityName))
                return Integer.valueOf(city.cityID);
        }
        return -1;
    }

    private void registerRecieverData() {
        broadcastReceiverData = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {

                Bundle extras = intent.getExtras();

                if (dates == null)
                    dates = extras.getStringArrayList(ConstantsManager.EXTRA_LIST_DATES);

                if (countries == null)
                    countries = extras.getParcelableArrayList(ConstantsManager.EXTRA_LIST_COUNTRIES);

                cities = extras.getParcelableArrayList(ConstantsManager.EXTRA_LIST_CITIES);

                if (genres == null)
                    genres = extras.getParcelableArrayList(ConstantsManager.EXTRA_LIST_GENRES);

                if (dates != null && countries != null && genres != null) {
                    ArrayList<String> months = new ArrayList<>();

                    for (int i = 1; i <= 12; i++)
                        months.add("" + i);

                    HashSet<String> hashSet = new HashSet<>();

                    for (String date : dates) {
                        hashSet.add(date.split("\\.")[1]);
                    }

                    ArrayList<String> years = new ArrayList<>(hashSet);
                    Collections.sort(years, (o1, o2) -> Integer.valueOf(o1).compareTo(Integer.valueOf(o2)));
                    hashSet.clear();

                    ArrayList<String> countriesString = new ArrayList<>();
                    for (Country country : countries)
                        countriesString.add(country.countryName);
                    Collections.sort(countriesString, String.CASE_INSENSITIVE_ORDER);

                    ArrayList<String> genresString = new ArrayList<>();
                    for (Genre genre : genres)
                        genresString.add(genre.genreName);
                    Collections.sort(genresString, String.CASE_INSENSITIVE_ORDER);

                    getMvpView().setAdapters(months, years, countriesString, genresString);
                    getMvpView().showProgress(false);
                }

                if (cities != null) {
                    ArrayList<String> citiesString = new ArrayList<>();
                    for (City city : cities)
                        citiesString.add(city.cityName);
                    Collections.sort(citiesString, String.CASE_INSENSITIVE_ORDER);
                    getMvpView().setCityAdapter(citiesString);
                    getMvpView().showProgress(false);
                }
            }
        };

        context.registerReceiver(broadcastReceiverData, new IntentFilter(ConstantsManager.BROADCAST_DATA));
    }

    private void registerRecieverSoonFilms() {
        broadcastReceiverSoonFilms = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();

                boolean add = extras.getBoolean(ConstantsManager.EXTRA_BOOLEAN_ADD);
                ArrayList<SoonFilm> soonFilms = extras.getParcelableArrayList(ConstantsManager.EXTRA_LIST_SOONFILMS);

                if (!add) {
                    getMvpView().setSoonFilms(soonFilms);
                    getMvpView().showProgress(false);
                } else {
                    getMvpView().addSoonFilms(soonFilms);
                }

            }
        };

        context.registerReceiver(broadcastReceiverSoonFilms, new IntentFilter(ConstantsManager.BROADCAST_SOONFILMS));
    }

    private void deregisterRecieverData() {
        try {
            context.unregisterReceiver(broadcastReceiverData);
        } catch (IllegalArgumentException | NullPointerException e) {
            Timber.d("Error while deregisteringRecieverData");
        }
    }

    private void deregisterRecieverSoonFilms() {
        try {
            context.unregisterReceiver(broadcastReceiverSoonFilms);
        } catch (IllegalArgumentException | NullPointerException e) {
            Timber.d("Error while deregisteringRecieverSoonFilms");
        }
    }
}
