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
    private String id;
    @Expose
    private String start;
    @Expose
    private String end;
    @Expose
    private String weekday;
    @Expose
    private String rule;
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
    @SerializedName("start_date_gmt")
    @Expose
    private String startDateGmt;
    @SerializedName("start_date_time_gmt")
    @Expose
    private String startDateTimeGmt;
    @SerializedName("end_date_gmt")
    @Expose
    private String endDateGmt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getStartDateGmt() {
        return startDateGmt;
    }

    public void setStartDateGmt(String startDateGmt) {
        this.startDateGmt = startDateGmt;
    }

    public String getStartDateTimeGmt() {
        return startDateTimeGmt;
    }

    public void setStartDateTimeGmt(String startDateTimeGmt) {
        this.startDateTimeGmt = startDateTimeGmt;
    }

    public String getEndDateGmt() {
        return endDateGmt;
    }

    public void setEndDateGmt(String endDateGmt) {
        this.endDateGmt = endDateGmt;
    }

    public String getEndDateTimeGmt() {
        return endDateTimeGmt;
    }

    public void setEndDateTimeGmt(String endDateTimeGmt) {
        this.endDateTimeGmt = endDateTimeGmt;
    }

    @SerializedName("end_date_time_gmt")
    @Expose
    private String endDateTimeGmt;
    @SerializedName("start_x")
    @Expose
    private String startX;
    @SerializedName("end_x")
    @Expose
    private String endX;

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
        dest.writeString(this.id);
        dest.writeString(this.start);
        dest.writeString(this.end);
        dest.writeString(this.weekday);
        dest.writeString(this.rule);
        dest.writeString(this.startF);
        dest.writeString(this.endF);
        dest.writeString(this.startGmt);
        dest.writeString(this.endGmt);
        dest.writeString(this.startDateGmt);
        dest.writeString(this.startDateTimeGmt);
        dest.writeString(this.endDateGmt);
        dest.writeString(this.endDateTimeGmt);
        dest.writeString(this.startX);
        dest.writeString(this.endX);
    }

    public Airtime() {
    }

    protected Airtime(Parcel in) {
        this.id = in.readString();
        this.start = in.readString();
        this.end = in.readString();
        this.weekday = in.readString();
        this.rule = in.readString();
        this.startF = in.readString();
        this.endF = in.readString();
        this.startGmt = in.readString();
        this.endGmt = in.readString();
        this.startDateGmt = in.readString();
        this.startDateTimeGmt = in.readString();
        this.endDateGmt = in.readString();
        this.endDateTimeGmt = in.readString();
        this.startX = in.readString();
        this.endX = in.readString();
    }

    public static final Creator<Airtime> CREATOR = new Creator<Airtime>() {
        public Airtime createFromParcel(Parcel source) {
            return new Airtime(source);
        }

        public Airtime[] newArray(int size) {
            return new Airtime[size];
        }
    };
}