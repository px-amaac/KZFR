package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.adapters.ShowListAdapter;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.models.Show;
import retrofit.client.Response;

/**
 * Created by Aaron on 7/8/2015.
 */
public class ShowsActivity extends MainActivity {

    private RecyclerView mRecyclerView;
    private ShowListAdapter mShowListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.simple_recycler_layout, mContentView, true);

        mRecyclerView = (RecyclerView) content.findViewById(R.id.simple_recycler_view);
        setupShowRecyclerView();
    }

    private void setupShowRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShowListAdapter = getOrSetupAdapter();
        mRecyclerView.setAdapter(mShowListAdapter);
    }

    private ShowListAdapter getOrSetupAdapter() {
        if (mShowListAdapter == null) {
            return new ShowListAdapter(this);
        } else
            return mShowListAdapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(true);
        executeShowsApiCall();
    }

    private void executeShowsApiCall() {
        ApiClient.getKZFRApiClient(this).getShows(new KZFRRetrofitCallback<ArrayList<Show>>() {
            @Override
            public void success(ArrayList<Show> shows, Response response) {
                super.success(shows, response);
                addDataToAdapter(shows);
            }
        });
    }

    private void addDataToAdapter(ArrayList<Show> shows) {
        if (shows != null && !shows.isEmpty()) {
            mShowListAdapter.setShowsData(shows);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mShowListAdapter.notifyDataSetChanged();
                    showProgressBar(false);
                }
            });
        }
    }


}
