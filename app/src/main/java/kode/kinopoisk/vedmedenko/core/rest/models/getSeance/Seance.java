package kode.kinopoisk.vedmedenko.core.rest.models.getSeance;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import kode.kinopoisk.vedmedenko.core.rest.models.VideoURL;
import kode.kinopoisk.vedmedenko.core.rest.models.getCityList.City;

public class Seance implements Parcelable {

    public String cityID;
    public String cityName;
    public String seanceURL;
    public String filmID;
    public String nameRU;
    public String nameEN;
    public String year;
    public String rating;
    public String posterURL;
    public String filmLength;
    public String country;
    public String genre;
    public String date;
    public String imdbID;

    public VideoURL videoURL;
    public ArrayList<SeanceItem> items;

    public Seance(Parcel in){
        String[] data = new String[14];

        in.readStringArray(data);
        cityID = data[0];
        cityName = data[1];
        seanceURL = data[2];
        filmID = data[3];
        nameRU = data[4];
        nameEN = data[5];
        year = data[6];
        rating = data[7];
        posterURL = data[8];
        filmLength = data[9];
        country = data[10];
        genre = data[11];
        date = data[12];
        imdbID = data[13];

        videoURL = in.readParcelable(VideoURL.class.getClassLoader());
        items = new ArrayList<>();
        in.readTypedList(items, SeanceItem.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {cityID, cityName, seanceURL, filmID, nameRU, nameEN, year,
                                            rating, posterURL, filmLength, country, genre, date, imdbID});
        dest.writeParcelable(videoURL, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeTypedList(items);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Seance createFromParcel(Parcel in) {
            return new Seance(in);
        }

        public Seance[] newArray(int size) {
            return new Seance[size];
        }
    };
}
