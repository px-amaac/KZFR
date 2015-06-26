package creek.fm.doublea.kzfr.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import creek.fm.doublea.kzfr.R;

/**
 * Created by Aaron on 6/25/2015.
 */
public class ScheduleDayFragment extends Fragment{
    @InjectView(R.id.day_recycler_view) RecyclerView mRecyclerView;

    public static ScheduleDayFragment newInstance() {
        return new ScheduleDayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule_day, container, false);
        ButterKnife.inject(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //reset all views to null
        ButterKnife.reset(this);
    }
}
