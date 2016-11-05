package kode.kinopoisk.vedmedenko.core.rest.models.getSoonFilms;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SoonFilms {

    @SerializedName("previewFilms")
    ArrayList<ArrayList<SoonFilm>> movies = new ArrayList<>();

    String date;

    public int getArraySize() {
        return movies.size();
    }

    public ArrayList<SoonFilm> getFilmList(int i) {
        return movies.get(i);
    }
}
