package kode.kinopoisk.vedmedenko.core.rest.models;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoURL implements Parcelable {

    public String hd;
    public String sd;
    public String low;

    public VideoURL(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        hd = data[0];
        sd = data[0];
        low = data[0];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {hd, sd, low});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public VideoURL createFromParcel(Parcel in) {
            return new VideoURL(in);
        }

        public VideoURL[] newArray(int size) {
            return new VideoURL[size];
        }
    };
}
