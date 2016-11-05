package kode.kinopoisk.vedmedenko.core.rest.models.getSoonFilms;

import android.os.Parcel;
import android.os.Parcelable;

import kode.kinopoisk.vedmedenko.core.rest.models.VideoURL;
import kode.kinopoisk.vedmedenko.core.rest.models.getCityList.City;

public class SoonFilm implements Parcelable {

    public String type;
    public String id;
    public String nameRU;
    public String nameEN;
    public String year;
    public String rating;
    public String posterURL;
    public String filmLength;
    public String country;
    public String genre;
    public VideoURL videoURL;
    public String premiereRU;

    public SoonFilm(Parcel in){
        String[] data = new String[11];

        in.readStringArray(data);
        type = data[0];
        id = data[1];
        nameRU = data[2];
        nameEN = data[3];
        year = data[4];
        rating = data[5];
        posterURL = data[6];
        filmLength = data[7];
        country = data[8];
        genre = data[9];
        premiereRU = data[10];

        videoURL = in.readParcelable(VideoURL.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {type, id, nameRU, nameEN, year, rating, posterURL, filmLength, country,
                                            genre, premiereRU});
        dest.writeParcelable(videoURL, PARCELABLE_WRITE_RETURN_VALUE);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SoonFilm createFromParcel(Parcel in) {
            return new SoonFilm(in);
        }

        public SoonFilm[] newArray(int size) {
            return new SoonFilm[size];
        }
    };
}
