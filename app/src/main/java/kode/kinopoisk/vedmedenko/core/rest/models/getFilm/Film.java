package kode.kinopoisk.vedmedenko.core.rest.models.getFilm;

import android.os.Parcel;
import android.os.Parcelable;

import kode.kinopoisk.vedmedenko.core.rest.models.RatingData;
import kode.kinopoisk.vedmedenko.core.rest.models.VideoURL;

public class Film implements Parcelable {

    public String filmID; //
    public String nameRU; //
    public String nameEN; //
    public String posterURL; //
    public String year; //
    public String filmLength;
    public String country;
    public String genre; //
    public String slogan; //
    public String description; //
    public String ratingMPAA; //
    public String ratingAgeLimits; //
    public String imdbID;

    public Integer reviewsCount;
    public Integer isIMAX;
    public Integer is3D;

    public RatingData ratingData;
    public VideoURL videoURL;

    public Film(Parcel in){
        String[] stringData = new String[13];
        in.readStringArray(stringData);

        filmID = stringData[0];
        nameRU = stringData[1];
        nameEN = stringData[2];
        posterURL = stringData[3];
        year = stringData[4];
        filmLength = stringData[5];
        country = stringData[6];
        genre = stringData[7];
        slogan = stringData[8];
        description = stringData[9];
        ratingMPAA = stringData[10];
        ratingAgeLimits = stringData[11];
        imdbID = stringData[12];

        int[] integerData = new int[3];
        in.readIntArray(integerData);

        reviewsCount = integerData[0];
        isIMAX = integerData[1];
        is3D = integerData[2];

        ratingData = in.readParcelable(RatingData.class.getClassLoader());
        videoURL = in.readParcelable(VideoURL.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {filmID, nameRU, nameEN, posterURL, year,
                filmLength, country, genre, slogan, description, ratingMPAA, ratingAgeLimits, imdbID});

        Integer[] arrIntegers = new Integer[] {reviewsCount, isIMAX, is3D};
        int[] ints = new int[arrIntegers.length];

        for(int i = 0; i < arrIntegers.length; i++) {
            if (arrIntegers[i] == null) {
                ints[i] = -1;
            } else {
                ints[i] = arrIntegers[i];
            }
        }

        dest.writeIntArray(ints);
        dest.writeParcelable(ratingData, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeParcelable(videoURL, PARCELABLE_WRITE_RETURN_VALUE);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
