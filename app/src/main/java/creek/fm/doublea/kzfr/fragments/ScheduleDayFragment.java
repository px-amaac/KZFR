package creek.fm.doublea.kzfr.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.adapters.DayListAdapter;
import creek.fm.doublea.kzfr.models.Program;

/**
 * Created by Aaron on 6/25/2015.
 */
public class ScheduleDayFragment extends Fragment {
    @InjectView(R.id.day_recycler_view)
    RecyclerView mRecyclerView;

    private static final String POSITION_TAG = "creek.fm.doublea.kzfr.fragments.position_tag";

    private int mPosition;

    private DayListAdapter mListAdapter = null;

    private GetDataInterface getDataInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            getDataInterface = (GetDataInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement GetData Interface");
        }
    }

    public interface GetDataInterface {
        List<Program> getProgramListData(int position);
    }

    public static ScheduleDayFragment newInstance(int position) {

        Log.d("ScheduleDayFragment", "newInstance");
        ScheduleDayFragment newScheduleDayFragment = new ScheduleDayFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_TAG, position);
        newScheduleDayFragment.setArguments(args);
        return newScheduleDayFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ScheduleDayFragment", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_schedule_day, container, false);
        ButterKnife.inject(this, rootView);
        mPosition = getArguments().getInt(POSITION_TAG);
        setupRecyclerView();

        return rootView;
    }

    private void setupRecyclerView() {
        Log.d("ScheduleDayFragment", "setupRecyclerView");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListAdapter = getOrSetupAdapter();
        mRecyclerView.setAdapter(mListAdapter);

    }

    private DayListAdapter getOrSetupAdapter() {
        Log.d("ScheduleDayFragment", "getOrSetupAdapter");
        if (mListAdapter == null) {
            return new DayListAdapter(getActivity(), new DayListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Program program) {

                }
            });
        } else
            return mListAdapter;
    }

    private void checkForProgramData() {
        Log.d("ScheduleDayFragment", "checkForProgramData");
        if(mListAdapter.isEmpty()) {
            List<Program> programList = getDataInterface.getProgramListData(mPosition);
            if(programList != null) {
                mListAdapter.setProgramsData(getDataInterface.getProgramListData(mPosition));
                mListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ScheduleDayFragment", "OnResume");
        checkForProgramData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
