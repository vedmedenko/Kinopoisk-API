package kode.kinopoisk.vedmedenko.core.rest;

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

public interface KinopoiskApi {

    @GET("/getSoonFilms")
    Observable<SoonFilms> getSoonFilms(@Query("date") String date, @Query("cityID") int cityID);

    @GET("/getFilm")
    Observable<Film> getFilm(@Query("filmID") String filmID);

    @GET("/getSeance")
    Observable<Seance> getSeance(@Query("filmID") String filmID, @Query("cityID") int cityID, @Query("date") String date);

    @GET("/getDatesForSoonFilms")
    Observable<DatesForSoonFilms> getDatesForSoonFilms();

    @GET("/getCityList")
    Observable<CityList> getCityList(@Query("countryID") int countryID);

    @GET("/getCountryList")
    Observable<CountryList> getCountryList();

    @GET("/getGenres")
    Observable<Genres> getGenres();
}