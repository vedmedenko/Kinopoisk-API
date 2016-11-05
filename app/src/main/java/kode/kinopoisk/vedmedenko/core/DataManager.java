package kode.kinopoisk.vedmedenko.core;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import kode.kinopoisk.vedmedenko.core.rest.KinopoiskApi;
import kode.kinopoisk.vedmedenko.core.rest.models.getCityList.CityList;
import kode.kinopoisk.vedmedenko.core.rest.models.getCounrtyList.CountryList;
import kode.kinopoisk.vedmedenko.core.rest.models.getDatesForSoonFilms.DatesForSoonFilms;
import kode.kinopoisk.vedmedenko.core.rest.models.getFilm.Film;
import kode.kinopoisk.vedmedenko.core.rest.models.getGenres.Genres;
import kode.kinopoisk.vedmedenko.core.rest.models.getSeance.Seance;
import kode.kinopoisk.vedmedenko.core.rest.models.getSoonFilms.SoonFilms;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public class DataManager {

    private KinopoiskApi kinopoiskApi;

    @Inject
    public DataManager(@NonNull KinopoiskApi kinopoiskApi) {
        this.kinopoiskApi = kinopoiskApi;
    }

    @NonNull
    public Observable<SoonFilms> loadSoonFilms(@NonNull String date, int cityID) {
        return kinopoiskApi.getSoonFilms(date, cityID);
    }

    @NonNull
    public Observable<Film> loadFilm(@NonNull String filmID) {
        return kinopoiskApi.getFilm(filmID);
    }

    @NonNull
    public Observable<Seance> loadSeance(@NonNull String filmID, int cityId, @NonNull String date) {
        return kinopoiskApi.getSeance(filmID, cityId, date);
    }

    @NonNull
    public Observable<DatesForSoonFilms> loadDatesForSoonFilms() {
        return kinopoiskApi.getDatesForSoonFilms();
    }

    @NonNull
    public Observable<CityList> loadCityList(@NonNull int countryID) {
        return kinopoiskApi.getCityList(countryID);
    }

    @NonNull
    public Observable<CountryList> loadCountryList() {
        return kinopoiskApi.getCountryList();
    }

    @NonNull
    public Observable<Genres> loadGenres() {
        return kinopoiskApi.getGenres();
    }
}
