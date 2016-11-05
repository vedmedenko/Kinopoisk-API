package kode.kinopoisk.vedmedenko.core.rest.models.getGenres;

import android.os.Parcel;
import android.os.Parcelable;

public class Genre implements Parcelable {

    public String genreID;
    public String genreName;

    public Genre(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        genreID = data[0];
        genreName = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {genreID, genreName});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
