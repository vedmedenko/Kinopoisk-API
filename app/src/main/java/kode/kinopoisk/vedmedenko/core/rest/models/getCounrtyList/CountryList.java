package kode.kinopoisk.vedmedenko.core.rest.models.getCounrtyList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryList {

    @SerializedName("countryData")
    ArrayList<Country> countries = new ArrayList<>();

    public ArrayList<Country> getCountries() {
        return countries;
    }
}
