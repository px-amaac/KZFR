package creek.fm.doublea.kzfr.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aaron on 6/18/2015.
 */
public class Airtime implements Parcelable {
    @Expose
    private String start;
    @Expose
    private String end;
    @SerializedName("start_x")
    @Expose
    private String startX;
    @SerializedName("end_x")
    @Expose
    private String endX;
    @SerializedName("start_f")
    @Expose
    private String startF;
    @SerializedName("end_f")
    @Expose
    private String endF;
    @SerializedName("start_gmt")
    @Expose
    private String startGmt;
    @SerializedName("end_gmt")
    @Expose
    private String endGmt;
    @Expose
    private String weekday;

    /**
     *
     * @return
     * The start
     */
    public String getStart() {
        return start;
    }

    /**
     *
     * @param start
     * The start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     *
     * @return
     * The end
     */
    public String getEnd() {
        return end;
    }

    /**
     *
     * @param end
     * The end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     *
     * @return
     * The startX
     */
    public String getStartX() {
        return startX;
    }

    /**
     *
     * @param startX
     * The start_x
     */
    public void setStartX(String startX) {
        this.startX = startX;
    }

    /**
     *
     * @return
     * The endX
     */
    public String getEndX() {
        return endX;
    }

    /**
     *
     * @param endX
     * The end_x
     */
    public void setEndX(String endX) {
        this.endX = endX;
    }

    /**
     *
     * @return
     * The startF
     */
    public String getStartF() {
        return startF;
    }

    /**
     *
     * @param startF
     * The start_f
     */
    public void setStartF(String startF) {
        this.startF = startF;
    }

    /**
     *
     * @return
     * The endF
     */
    public String getEndF() {
        return endF;
    }

    /**
     *
     * @param endF
     * The end_f
     */
    public void setEndF(String endF) {
        this.endF = endF;
    }

    /**
     *
     * @return
     * The startGmt
     */
    public String getStartGmt() {
        return startGmt;
    }

    /**
     *
     * @param startGmt
     * The start_gmt
     */
    public void setStartGmt(String startGmt) {
        this.startGmt = startGmt;
    }

    /**
     *
     * @return
     * The endGmt
     */
    public String getEndGmt() {
        return endGmt;
    }

    /**
     *
     * @param endGmt
     * The end_gmt
     */
    public void setEndGmt(String endGmt) {
        this.endGmt = endGmt;
    }

    /**
     *
     * @return
     * The weekday
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     *
     * @param weekday
     * The weekday
     */
    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.start);
        dest.writeString(this.end);
        dest.writeString(this.startX);
        dest.writeString(this.endX);
        dest.writeString(this.startF);
        dest.writeString(this.endF);
        dest.writeString(this.startGmt);
        dest.writeString(this.endGmt);
        dest.writeString(this.weekday);
    }

    public Airtime() {
    }

    protected Airtime(Parcel in) {
        this.start = in.readString();
        this.end = in.readString();
        this.startX = in.readString();
        this.endX = in.readString();
        this.startF = in.readString();
        this.endF = in.readString();
        this.startGmt = in.readString();
        this.endGmt = in.readString();
        this.weekday = in.readString();
    }

    public static final Parcelable.Creator<Airtime> CREATOR = new Parcelable.Creator<Airtime>() {
        public Airtime createFromParcel(Parcel source) {
            return new Airtime(source);
        }

        public Airtime[] newArray(int size) {
            return new Airtime[size];
        }
    };
}