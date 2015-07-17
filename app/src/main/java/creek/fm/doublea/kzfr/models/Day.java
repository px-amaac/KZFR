package creek.fm.doublea.kzfr.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 6/18/2015.
 */
public class Day implements Parcelable {

    @Expose
    private String weekday;
    @SerializedName("programs")
    @Expose
    private List<Show> shows = new ArrayList<Show>();

    /**
     * @return The id
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     * @param weekday The id
     */
    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    /**
     * @return The shows
     */
    public List<Show> getShows() {
        return shows;
    }

    /**
     * @param shows The shows
     */
    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weekday);
        dest.writeList(this.shows);
    }

    public Day() {
    }

    protected Day(Parcel in) {
        this.weekday = in.readString();
        this.shows = new ArrayList<Show>();
        in.readList(this.shows, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Day> CREATOR = new Parcelable.Creator<Day>() {
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}
