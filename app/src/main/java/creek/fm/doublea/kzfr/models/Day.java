package creek.fm.doublea.kzfr.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 6/18/2015.
 */
public class Day {

    @Expose
    private String weekday;

    @Expose
    private List<Program> programs = new ArrayList<Program>();

    /**
     *
     * @return
     * The id
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     *
     * @param weekday
     * The id
     */
    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    /**
     *
     * @return
     * The programs
     */
    public List<Program> getPrograms() {
        return programs;
    }

    /**
     *
     * @param programs
     * The programs
     */
    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }
}
