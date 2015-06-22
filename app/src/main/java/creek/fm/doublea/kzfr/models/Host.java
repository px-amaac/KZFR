package creek.fm.doublea.kzfr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aaron on 6/18/2015.
 */
public class Host {
    @Expose
    private String id;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @Expose
    private String username;
    @Expose
    private Boolean image;

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
    public Boolean getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(Boolean image) {
        this.image = image;
    }

}