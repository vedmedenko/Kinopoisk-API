package kode.kinopoisk.vedmedenko.core.rest.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RatingData implements Parcelable {

    public String ratingGoodReview;
    public String rating;
    public String ratingVoteCount;
    public String ratingAwait;
    public String ratingAwaitCount;
    public String ratingIMDb;
    public String ratingIMDbVoteCount;
    public String ratingFilmCritics;
    public String ratingFilmCriticsVoteCount;
    public String ratingRFCritics;

    public Integer ratingRFCriticsVoteCount;
    public Integer ratingGoodReviewVoteCount;

    public RatingData(Parcel in){
        String[] stringData = new String[10];
        in.readStringArray(stringData);

        ratingGoodReview = stringData[0];
        rating = stringData[1];
        ratingVoteCount = stringData[2];
        ratingAwait = stringData[3];
        ratingAwaitCount = stringData[4];
        ratingIMDb = stringData[5];
        ratingIMDbVoteCount = stringData[6];
        ratingFilmCritics = stringData[7];
        ratingFilmCriticsVoteCount = stringData[8];
        ratingRFCritics  = stringData[9];

        int[] integerData = new int[2];
        in.readIntArray(integerData);

        ratingRFCriticsVoteCount = integerData[0];
        ratingGoodReviewVoteCount = integerData[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {ratingGoodReview, rating, ratingVoteCount, ratingAwait, ratingAwaitCount,
                                            ratingIMDb, ratingIMDbVoteCount, ratingFilmCritics, ratingFilmCriticsVoteCount,
                                            ratingRFCritics});

        Integer[] arrIntegers = new Integer[] {ratingRFCriticsVoteCount, ratingGoodReviewVoteCount};
        int[] ints = new int[arrIntegers.length];

        for(int i = 0; i < arrIntegers.length; i++) {
            if (arrIntegers[i] == null) {
                ints[i] = -1;
            } else {
                ints[i] = arrIntegers[i];
            }
        }

        dest.writeIntArray(ints);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public RatingData createFromParcel(Parcel in) {
            return new RatingData(in);
        }

        public RatingData[] newArray(int size) {
            return new RatingData[size];
        }
    };
}
