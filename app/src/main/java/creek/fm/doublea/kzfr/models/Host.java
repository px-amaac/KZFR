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
public class Host implements Parcelable {
    @Expose
    private String id;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @Expose
    private String username;
    @Expose
    private creek.fm.doublea.kzfr.models.Image Image;
    @SerializedName("image")
    @Expose
    private String imageName;
    @Expose
    private List<Program> programs = new ArrayList<Program>();

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName The display_name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The Image
     */
    public creek.fm.doublea.kzfr.models.Image getImage() {
        return Image;
    }

    /**
     * @param Image The Image
     */
    public void setImage(creek.fm.doublea.kzfr.models.Image Image) {
        this.Image = Image;
    }

    /**
     * @return The image
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.imageName = image;
    }

    /**
     * @return The programs
     */
    public List<Program> getPrograms() {
        return programs;
    }

    /**
     * @param programs The programs
     */
    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    /**
     * @return The hosts
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.displayName);
        dest.writeString(this.username);
        dest.writeString(this.imageName);
        dest.writeParcelable(this.Image, 0);
        dest.writeTypedList(this.programs);
    }

    public Host() {
    }

    protected Host(Parcel in) {
        this.id = in.readString();
        this.displayName = in.readString();
        this.username = in.readString();
        this.imageName = in.readString();
        this.Image = in.readParcelable(creek.fm.doublea.kzfr.models.Image.class.getClassLoader());
        this.programs = in.createTypedArrayList(Program.CREATOR);
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