package creek.fm.doublea.kzfr.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aaron on 6/18/2015.
 */
public class Image implements Parcelable {
    @Expose
    private String url;
    @SerializedName("url_sm")
    @Expose
    private String urlSm;
    @SerializedName("url_md")
    @Expose
    private String urlMd;
    @SerializedName("url_lg")
    @Expose
    private String urlLg;

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The urlSm
     */
    public String getUrlSm() {
        return urlSm;
    }

    /**
     *
     * @param urlSm
     * The url_sm
     */
    public void setUrlSm(String urlSm) {
        this.urlSm = urlSm;
    }

    /**
     *
     * @return
     * The urlMd
     */
    public String getUrlMd() {
        return urlMd;
    }

    /**
     *
     * @param urlMd
     * The url_md
     */
    public void setUrlMd(String urlMd) {
        this.urlMd = urlMd;
    }

    /**
     *
     * @return
     * The urlLg
     */
    public String getUrlLg() {
        return urlLg;
    }

    /**
     *
     * @param urlLg
     * The url_lg
     */
    public void setUrlLg(String urlLg) {
        this.urlLg = urlLg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.urlSm);
        dest.writeString(this.urlMd);
        dest.writeString(this.urlLg);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.url = in.readString();
        this.urlSm = in.readString();
        this.urlMd = in.readString();
        this.urlLg = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}