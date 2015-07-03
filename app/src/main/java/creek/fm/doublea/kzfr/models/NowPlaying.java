package creek.fm.doublea.kzfr.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Aaron on 6/30/2015.
 */
public class NowPlaying {

    @Expose
    private Program now;
    @Expose
    private Program next;
    @Expose
    private Boolean Track;

    /**
     *
     * @return
     * The now
     */
    public Program getNow() {
        return now;
    }

    /**
     *
     * @param now
     * The now
     */
    public void setNow(Program now) {
        this.now = now;
    }

    /**
     *
     * @return
     * The next
     */
    public Program getNext() {
        return next;
    }

    /**
     *
     * @param next
     * The next
     */
    public void setNext(Program next) {
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
