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
public class Program implements Parcelable {
    @Expose
    private creek.fm.doublea.kzfr.models.Airtime Airtime;
    @Expose
    private String id;
    @Expose
    private String title;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("full_description")
    @Expose
    private String fullDescription;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @Expose
    private creek.fm.doublea.kzfr.models.Image Image;
    @SerializedName("image")
    @Expose
    private String imageName;
    @Expose
    private List<Object> hosts = new ArrayList<Object>();
    @Expose
    private List<Category> categories = new ArrayList<Category>();

    /**
     *
     * @return
     * The Airtime
     */
    public creek.fm.doublea.kzfr.models.Airtime getAirtime() {
        return Airtime;
    }

    /**
     *
     * @param Airtime
     * The Airtime
     */
    public void setAirtime(creek.fm.doublea.kzfr.models.Airtime Airtime) {
        this.Airtime = Airtime;
    }

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
     * The Image
     */
    public creek.fm.doublea.kzfr.models.Image getImage() {
        return Image;
    }

    /**
     *
     * @param Image
     * The Image
     */
    public void setImage(creek.fm.doublea.kzfr.models.Image Image) {
        this.Image = Image;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImageName() {
        return imageName;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.imageName = image;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.Airtime, 0);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.shortName);
        dest.writeString(this.fullDescription);
        dest.writeString(this.shortDescription);
        dest.writeParcelable(this.Image, 0);
        dest.writeString(this.imageName);
        dest.writeList(this.hosts);
        dest.writeTypedList(categories);
    }

    public Program() {
    }

    protected Program(Parcel in) {
        this.Airtime = in.readParcelable(creek.fm.doublea.kzfr.models.Airtime.class.getClassLoader());
        this.id = in.readString();
        this.title = in.readString();
        this.shortName = in.readString();
        this.fullDescription = in.readString();
        this.shortDescription = in.readString();
        this.Image = in.readParcelable(creek.fm.doublea.kzfr.models.Image.class.getClassLoader());
        this.imageName = in.readString();
        this.hosts = new ArrayList<Object>();
        in.readList(this.hosts, List.class.getClassLoader());
        this.categories = in.createTypedArrayList(Category.CREATOR);
    }

    public static final Parcelable.Creator<Program> CREATOR = new Parcelable.Creator<Program>() {
        public Program createFromParcel(Parcel source) {
            return new Program(source);
        }

        public Program[] newArray(int size) {
            return new Program[size];
        }
    };
}