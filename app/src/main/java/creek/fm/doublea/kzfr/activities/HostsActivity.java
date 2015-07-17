package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.adapters.HostListAdapter;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.models.Host;
import retrofit.client.Response;

/**
 * Created by Aaron on 7/8/2015.
 */
public class HostsActivity extends MainActivity {

    private RecyclerView mRecyclerView;
    private HostListAdapter mHostListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.simple_recycler_layout, mContentView, true);

        mRecyclerView = (RecyclerView) content.findViewById(R.id.simple_recycler_view);
        setupHostRecyclerView();
    }

    private void setupHostRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHostListAdapter = getOrSetupAdapter();
        mRecyclerView.setAdapter(mHostListAdapter);
    }

    private HostListAdapter getOrSetupAdapter() {
        if (mHostListAdapter == null) {
            return new HostListAdapter(this);
        } else
            return mHostListAdapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(true);
        executeShowsApiCall();
    }

    private void executeShowsApiCall() {
        ApiClient.getKZFRApiClient(this).getHosts(new KZFRRetrofitCallback<ArrayList<Host>>() {
            @Override
            public void success(ArrayList<Host> hosts, Response response) {
                super.success(hosts, response);
                addDataToAdapter(hosts);
            }
        });
    }

    private void addDataToAdapter(ArrayList<Host> hosts) {
        if (hosts != null && !hosts.isEmpty()) {
            mHostListAdapter.setShowsData(hosts);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mHostListAdapter.notifyDataSetChanged();
                    showProgressBar(false);
                }
            });
        }
    }


}
