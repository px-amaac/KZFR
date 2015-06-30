package creek.fm.doublea.kzfr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 6/30/2015.
 */
public class Broadcast {

    @Expose
    private String id;
    @Expose
    private String title;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("full_description")
    @Expose
    private String fullDescription;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("has_airtimes")
    @Expose
    private Boolean hasAirtimes;
    @Expose
    private Airtime Airtime;
    @Expose
    private String image;
    @Expose
    private Image Image;
    @Expose
    private String url;
    @Expose
    private List<Category> categories = new ArrayList<Category>();
    @Expose
    private List<Object> hosts = new ArrayList<Object>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     *
     * @param shortDescription
     * The short_description
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     *
     * @return
     * The fullDescription
     */
    public String getFullDescription() {
        return fullDescription;
    }

    /**
     *
     * @param fullDescription
     * The full_description
     */
    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    /**
     *
     * @return
     * The shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     *
     * @param shortName
     * The short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     *
     * @return
     * The hasAirtimes
     */
    public Boolean getHasAirtimes() {
        return hasAirtimes;
    }

    /**
     *
     * @param hasAirtimes
     * The has_airtimes
     */
    public void setHasAirtimes(Boolean hasAirtimes) {
        this.hasAirtimes = hasAirtimes;
    }

    /**
     *
     * @return
     * The Airtime
     */
    public Airtime getAirtime() {
        return Airtime;
    }

    /**
     *
     * @param Airtime
     * The Airtime
     */
    public void setAirtime(Airtime Airtime) {
        this.Airtime = Airtime;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImageName() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImageName(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The Image
     */
    public Image getImage() {
        return Image;
    }

    /**
     *
     * @param Image
     * The Image
     */
    public void setImage(Image Image) {
        this.Image = Image;
    }

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
     * The categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     *
     * @return
     * The hosts
     */
    public List<Object> getHosts() {
        return hosts;
    }

    /**
     *
     * @param hosts
     * The hosts
     */
    public void setHosts(List<Object> hosts) {
        this.hosts = hosts;
    }
}
