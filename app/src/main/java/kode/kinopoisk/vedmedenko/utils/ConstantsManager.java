package kode.kinopoisk.vedmedenko.utils;

public final class ConstantsManager {

    // Service constants.

    public static final String DATE = "kode.kinopoisk.vedmedenko.DATE";
    public static final String CITY_ID = "kode.kinopoisk.vedmedenko.CITY_ID";
    public static final String COUNTRY_ID = "kode.kinopoisk.vedmedenko.COUNTRY_ID";
    public static final String FILM_ID = "kode.kinopoisk.vedmedenko.FILM_ID";

    public static final String BROADCAST_DATA = "kode.kinopoisk.vedmedenko.BROADCAST_DATA";
    public static final String EXTRA_LIST_DATES = "kode.kinopoisk.vedmedenko.EXTRA_LIST_DATES";
    public static final String EXTRA_LIST_COUNTRIES = "kode.kinopoisk.vedmedenko.EXTRA_LIST_COUNTRIES";
    public static final String EXTRA_LIST_CITIES = "kode.kinopoisk.vedmedenko.EXTRA_LIST_CITIES";
    public static final String EXTRA_LIST_GENRES = "kode.kinopoisk.vedmedenko.EXTRA_LIST_GENRES";

    public static final String BROADCAST_SOONFILMS = "kode.kinopoisk.vedmedenko.BROADCAST_SOONFILMS";
    public static final String EXTRA_BOOLEAN_ADD = "kode.kinopoisk.vedmedenko.EXTRA_BOOLEAN_ADD";
    public static final String EXTRA_LIST_SOONFILMS = "kode.kinopoisk.vedmedenko.EXTRA_LIST_SOONFILMS";

    public static final String BROADCAST_FILM = "kode.kinopoisk.vedmedenko.BROADCAST_FILM";
    public static final String EXTRA_FILM = "kode.kinopoisk.vedmedenko.EXTRA_FILM";

    public static final String BROADCAST_SEANCE = "kode.kinopoisk.vedmedenko.BROADCAST_SEANCE";
    public static final String EXTRA_SEANCE = "kode.kinopoisk.vedmedenko.EXTRA_SEANCE";

    // Maps Activity

    public static final String EXTRA_LAT = "kode.kinopoisk.vedmedenko.EXTRA_LAT";
    public static final String EXTRA_CINEMA_NAME = "kode.kinopoisk.vedmedenko.EXTRA_CINEMA_NAME";
    public static final String EXTRA_LON = "kode.kinopoisk.vedmedenko.EXTRA_LON";

    // Detail Fragment constants.

    public static final String DF_ARG_MAP = "kode.kinopoisk.vedmedenko.ARG_MAP";

    // Retrofit constants.

    public static final String BASE_URL = "http://api.kinopoisk.cf";

    // RestModule constants.

    public static final int CONNECTION_TIME_OUT = 50;
    public static final int READ_TIME_OUT = 50;

    private ConstantsManager() {
        throw new AssertionError("No instances");
    }
}
