package kode.kinopoisk.vedmedenko.core.rest.models.getCounrtyList;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable {

    public String countryID;
    public String countryName;

    public Country(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        countryID = data[0];
        countryName = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {countryID, countryName});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
