package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.HashMap;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.adapters.ProgramListAdapter;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.models.Day;
import creek.fm.doublea.kzfr.models.Program;
import retrofit.client.Response;

/**
 * Created by Aaron on 7/8/2015.
 */
public class ProgramsActivity extends MainActivity{

    private RecyclerView mRecyclerView;
    private ProgramListAdapter mProgramListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.program_recycler_layout, mContentView, true);

        mRecyclerView = (RecyclerView) content.findViewById(R.id.programs_recycler_view);
        setupProgramRecyclerView();
    }

    private void setupProgramRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgramListAdapter = getOrSetupAdapter();
        mRecyclerView.setAdapter(mProgramListAdapter);
    }

    private ProgramListAdapter getOrSetupAdapter() {
        if (mProgramListAdapter == null) {
            return new ProgramListAdapter(this);
        } else
            return mProgramListAdapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(true);
        executeProgramsApiCall();
    }

    private void executeProgramsApiCall() {
        ApiClient.getKZFRApiClient(this).getPrograms(new KZFRRetrofitCallback<ArrayList<Program>>() {
            @Override
            public void success(ArrayList<Program> programs, Response response) {
                super.success(programs, response);
                addDataToAdapter(programs);
            }
        });
    }

    private void addDataToAdapter(ArrayList<Program> programs) {
        if (programs != null && !programs.isEmpty()) {
            mProgramListAdapter.setProgramsData(programs);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgramListAdapter.notifyDataSetChanged();
                    showProgressBar(false);
                }
            });
        }
    }


}
