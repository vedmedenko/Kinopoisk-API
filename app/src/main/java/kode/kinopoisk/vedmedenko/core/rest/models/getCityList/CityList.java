package kode.kinopoisk.vedmedenko.core.rest.models.getCityList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CityList {

    public String countryID;
    public String countryName;

    @SerializedName("cityData")
    ArrayList<City> cities = new ArrayList<>();

    public ArrayList<City> getCities() {
        return cities;
    }

}
