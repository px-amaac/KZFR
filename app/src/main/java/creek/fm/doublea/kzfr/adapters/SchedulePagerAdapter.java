package creek.fm.doublea.kzfr.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import creek.fm.doublea.kzfr.fragments.ScheduleDayFragment;
import creek.fm.doublea.kzfr.models.Day;
import creek.fm.doublea.kzfr.models.Show;

/**
 * Created by Aaron on 6/25/2015.
 */
public class SchedulePagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = SchedulePagerAdapter.class.getSimpleName();
    private static final String[] CONTENT = new String[]{"Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private HashMap<String, Day> mScheduleData = new HashMap<String, Day>();

    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem at pasition " + position);
        return ScheduleDayFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mScheduleData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length];
    }

    public boolean isEmpty() {
        if (mScheduleData != null && !mScheduleData.isEmpty()) {
            return false;
        }
        return true;
    }

    public void setData(Map<String, Day> data) {

        Log.d(TAG, "setData");
        mScheduleData.clear();
        mScheduleData.putAll(data);
    }

    public List<Show> getData(int position) {
        Log.d(TAG, "getData at position " + position);
        return mScheduleData.get(String.valueOf(position + 1)).getShows();
    }

    public HashMap<String, Day> getAllData() {
        return mScheduleData;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
