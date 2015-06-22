package creek.fm.doublea.kzfr.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 6/18/2015.
 */
public class Week {
    @Expose
    private List<Day> days = new ArrayList<Day>();

    /**
     *
     * @return
     * The Days
     */
    public List<Day> getPrograms() {
        return days;
    }

    /**
     *
     * @param days
     * The Days
     */
    public void setDay(List<Day> days) {
        this.days = days;
    }
}
