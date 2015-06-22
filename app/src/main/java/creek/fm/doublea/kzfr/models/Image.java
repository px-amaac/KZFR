package creek.fm.doublea.kzfr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aaron on 6/18/2015.
 */
public class Image {
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

}