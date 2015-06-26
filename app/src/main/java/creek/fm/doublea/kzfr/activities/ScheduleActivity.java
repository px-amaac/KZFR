package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.adapters.SchedulePagerAdapter;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.models.Day;
import creek.fm.doublea.kzfr.models.Week;
import retrofit.client.Response;

/**
 * Created by Aaron on 6/22/2015.
 */
public class ScheduleActivity extends MainActivity  {

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.activity_schedule, mContentView, true);
        ViewPager mViewPager = (ViewPager) content.findViewById(R.id.schedule_pager);


        mPagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(getDayOfWeek());
    }

    private void executeApiCall() {
        ApiClient.getKZFRApiClient(this).getSchedule(new KZFRRetrofitCallback<Map<String, Day>>() {

            @Override
            public void success(Map<String, Day> days, Response response) {
                Log.e("CALLBACK!!!!!!!!!!!!!", "Success");
            }
        });
    }

    private int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.DAY_OF_WEEK) - 2);
    }
}
