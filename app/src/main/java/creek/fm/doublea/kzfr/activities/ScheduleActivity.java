package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.adapters.SchedulePagerAdapter;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.fragments.ScheduleDayFragment;
import creek.fm.doublea.kzfr.models.Day;
import creek.fm.doublea.kzfr.models.Program;
import retrofit.client.Response;

/**
 * Created by Aaron on 6/22/2015.
 */
public class ScheduleActivity extends MainActivity implements ScheduleDayFragment.GetDataInterface{

    private SchedulePagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SchudeleActivity", "onCreate");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.activity_schedule, mContentView, true);
        mViewPager = (ViewPager) content.findViewById(R.id.schedule_pager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SchudeleActivity", "onResume");
        setupPagerAdapter();
        if (mPagerAdapter.isEmpty()) {
            showProgressBar(true);
            executeApiCall();
        }
    }

    private void setupPagerAdapter() {
        Log.d("SchudeleActivity", "setupPagerAdapter");
        if (mViewPager != null) {
            mViewPager.setAdapter(getSchedulePagerAdapter());
            mViewPager.setCurrentItem(getDayOfWeek());
        }
    }

    private SchedulePagerAdapter getSchedulePagerAdapter() {

        Log.d("SchudeleActivity", "getSchedulePagerAdapter");
        if (mPagerAdapter == null) {
            mPagerAdapter = new SchedulePagerAdapter(getSupportFragmentManager());
        }
        return mPagerAdapter;
    }

    private void executeApiCall() {
        ApiClient.getKZFRApiClient(this).getSchedule(new KZFRRetrofitCallback<Map<String, Day>>() {

            @Override
            public void success(Map<String, Day> days, Response response) {
                mPagerAdapter.setData(days);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("SchudeleActivity", "APICALLSUCCESS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        mPagerAdapter.notifyDataSetChanged();
                        showProgressBar(false);
                    }
                });
            }
        });
    }

    private int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.DAY_OF_WEEK) - 2);
    }

    @Override
    public List<Program> getProgramListData(int position) {
        Log.d("SchudeleActivity", "getProgramList");
        if(!mPagerAdapter.isEmpty()) {

            Log.d("SchudeleActivity", "getProgramList_DataReturned");
            return mPagerAdapter.getData(position);
        }
        return null;
    }
}
