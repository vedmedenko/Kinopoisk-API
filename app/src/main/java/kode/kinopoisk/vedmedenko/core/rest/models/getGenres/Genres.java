package kode.kinopoisk.vedmedenko.core.rest.models.getGenres;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Genres {

    @SerializedName("genreData")
    ArrayList<Genre> genres = new ArrayList<>();

    public ArrayList<Genre> getGenres() {
        return genres;
    }

}
