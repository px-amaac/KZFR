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

import butterknife.Bind;
import butterknife.ButterKnife;
import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.adapters.ShowListAdapter;
import creek.fm.doublea.kzfr.models.Show;

/**
 * The fragment that contains the list view of shows for each day in the schedule view pager.
 */
public class ScheduleDayFragment extends Fragment {
    @Bind(R.id.simple_recycler_view)
    RecyclerView mRecyclerView;

    private static final String POSITION_TAG = "creek.fm.doublea.kzfr.fragments.position_tag";

    private int mPagerPosition;

    private ShowListAdapter mListAdapter = null;

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
        List<Show> getShowListData(int position);
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
        View rootView = inflater.inflate(R.layout.simple_recycler_layout, container, false);
        ButterKnife.bind(this, rootView);
        mPagerPosition = getArguments().getInt(POSITION_TAG);
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

    private ShowListAdapter getOrSetupAdapter() {
        Log.d("ScheduleDayFragment", "getOrSetupAdapter");
        if (mListAdapter == null) {
            return new ShowListAdapter(getActivity());
        } else
            return mListAdapter;
    }

    private void checkForShowData() {
        Log.d("ScheduleDayFragment", "checkForShowData");
        if (mListAdapter.isEmpty()) {
            List<Show> showList = getDataInterface.getShowListData(mPagerPosition);
            if (showList != null) {
                mListAdapter.setShowsData(getDataInterface.getShowListData(mPagerPosition));
                mListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ScheduleDayFragment", "OnResume");
        checkForShowData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
