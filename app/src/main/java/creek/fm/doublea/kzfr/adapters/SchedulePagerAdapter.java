package creek.fm.doublea.kzfr.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Map;

import creek.fm.doublea.kzfr.fragments.ScheduleDayFragment;
import creek.fm.doublea.kzfr.models.Day;

/**
 * Created by Aaron on 6/25/2015.
 */
public class SchedulePagerAdapter extends FragmentPagerAdapter {
    private static final String[] CONTENT = new String[]{"Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private Map<String, Day> mSchedleData;

    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ScheduleDayFragment.newInstance();
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position % CONTENT.length];
    }

    public boolean isEmpty() {
        if (mSchedleData != null && !mSchedleData.isEmpty()) {
            return false;
        }
        return true;
    }
}
