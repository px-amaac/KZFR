package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.adapters.SimpleRecyclerAdapter;
import creek.fm.doublea.kzfr.models.Program;
import creek.fm.doublea.kzfr.models.VersionModel;

/**
 * Created by Aaron on 7/2/2015.
 */
public class ProgramActivity extends MainActivity {
    private static final String TAG = ProgramActivity.class.getSimpleName();
    public static final String PROGRAM_DATA_KEY = TAG + ".program_data_key";

    Program mProgram;
    SimpleRecyclerAdapter simpleRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View content = inflater.inflate(R.layout.activity_program, mContentView, true);
        RecyclerView programRecyclerView = (RecyclerView) content.findViewById(R.id.program_recycler_view);
        programRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        programRecyclerView.setLayoutManager(linearLayoutManager);
        List<String> listData = new ArrayList<String>();
        int ct = 0;
        for (int i = 0; i < VersionModel.data.length * 2; i++) {
            listData.add(VersionModel.data[ct]);
            ct++;
            if (ct == VersionModel.data.length) {
                ct = 0;
            }
        }

        if (simpleRecyclerAdapter == null) {
            simpleRecyclerAdapter = new SimpleRecyclerAdapter(listData);
            programRecyclerView.setAdapter(simpleRecyclerAdapter);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromIntent(getIntent());
    }

    private void getDataFromIntent(Intent intent) {
        mProgram = intent.getParcelableExtra(PROGRAM_DATA_KEY);
    }
}
