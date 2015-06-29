package creek.fm.doublea.kzfr.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 6/18/2015.
 */
public class Day implements Parcelable {

    @Expose
    private String weekday;

    @Expose
    private List<Program> programs = new ArrayList<Program>();

    /**
     *
     * @return
     * The id
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     *
     * @param weekday
     * The id
     */
    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    /**
     *
     * @return
     * The programs
     */
    public List<Program> getPrograms() {
        return programs;
    }

    /**
     *
     * @param programs
     * The programs
     */
    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weekday);
        dest.writeList(this.programs);
    }

    public Day() {
    }

    protected Day(Parcel in) {
        this.weekday = in.readString();
        this.programs = new ArrayList<Program>();
        in.readList(this.programs, List.class.getClassLoader());
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
