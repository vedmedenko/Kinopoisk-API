package kode.kinopoisk.vedmedenko.core.rest.models.getDatesForSoonFilms;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DatesForSoonFilms {

    @SerializedName("dates")
    ArrayList<String> dates = new ArrayList<>();

    public ArrayList<String> getDates() {
        return dates;
    }
}
