package creek.fm.doublea.kzfr.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Aaron on 6/30/2015.
 */
public class NowPlaying {

    @Expose
    private Show now;
    @Expose
    private Show next;
    @Expose
    private Boolean Track;

    /**
     * @return The now
     */
    public Show getNow() {
        return now;
    }

    /**
     * @param now The now
     */
    public void setNow(Show now) {
        this.now = now;
    }

    /**
     * @return The next
     */
    public Show getNext() {
        return next;
    }

    /**
     * @param next The next
     */
    public void setNext(Show next) {
        this.next = next;
    }

    /**
     * @return The Track
     */
    public Boolean getTrack() {
        return Track;
    }

    /**
     * @param Track The Track
     */
    public void setTrack(Boolean Track) {
        this.Track = Track;
    }
}
