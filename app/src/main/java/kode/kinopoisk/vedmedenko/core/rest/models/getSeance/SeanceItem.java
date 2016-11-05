package kode.kinopoisk.vedmedenko.core.rest.models.getSeance;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SeanceItem implements Parcelable {

    public String address;
    public String lon;
    public String lat;
    public String cinemaName;

    public Integer cinemaID;

    @SerializedName("seance")
    public ArrayList<String> seances;

    public SeanceItem(Parcel in){
        cinemaID = in.readInt();

        String[] data = new String[4];

        in.readStringArray(data);
        address = data[0];
        lon = data[1];
        lat = data[2];
        cinemaName = data[3];

        seances = new ArrayList<>();
        in.readList(seances, String.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cinemaID);
        dest.writeStringArray(new String[] {address, lon, lat, cinemaName});
        dest.writeList(seances);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SeanceItem createFromParcel(Parcel in) {
            return new SeanceItem(in);
        }

        public SeanceItem[] newArray(int size) {
            return new SeanceItem[size];
        }
    };
}
