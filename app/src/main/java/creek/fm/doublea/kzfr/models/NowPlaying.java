package creek.fm.doublea.kzfr.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Aaron on 6/30/2015.
 */
public class NowPlaying {

    @Expose
    private Broadcast now;
    @Expose
    private Broadcast next;
    @Expose
    private Boolean Track;

    /**
     *
     * @return
     * The now
     */
    public Broadcast getNow() {
        return now;
    }

    /**
     *
     * @param now
     * The now
     */
    public void setNow(Broadcast now) {
        this.now = now;
    }

    /**
     *
     * @return
     * The next
     */
    public Broadcast getNext() {
        return next;
    }

    /**
     *
     * @param next
     * The next
     */
    public void setNext(Broadcast next) {
        this.next = next;
    }

    /**
     *
     * @return
     * The Track
     */
    public Boolean getTrack() {
        return Track;
    }

    /**
     *
     * @param Track
     * The Track
     */
    public void setTrack(Boolean Track) {
        this.Track = Track;
    }
}
