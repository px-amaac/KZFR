package creek.fm.doublea.kzfr.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aaron on 6/18/2015.
 */
public class Host implements Parcelable {
    @Expose
    private String id;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @Expose
    private String username;
    @Expose
    private String image;

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
     * The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName
     * The display_name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.displayName);
        dest.writeString(this.username);
        dest.writeValue(this.image);
    }

    public Host() {
    }

    protected Host(Parcel in) {
        this.id = in.readString();
        this.displayName = in.readString();
        this.username =
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Host> CREATOR = new Parcelable.Creator<Host>() {
        public Host createFromParcel(Parcel source) {
            return new Host(source);
        }

        public Host[] newArray(int size) {
            return new Host[size];
        }
    };
}