package kode.kinopoisk.vedmedenko.core.rest.models.getCityList;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {

    public String cityID;
    public String cityName;

    public City(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        cityID = data[0];
        cityName = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {cityID, cityName});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
