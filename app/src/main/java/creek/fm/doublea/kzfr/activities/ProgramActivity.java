package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.models.Program;

/**
 * Created by Aaron on 7/2/2015.
 */
public class ProgramActivity extends MainActivity {
    private static final String TAG = ProgramActivity.class.getSimpleName();
    public static final String PROGRAM_DATA_KEY = TAG + ".program_data_key";

    Program mProgram;
    private TextView mProgramTitle;
    private TextView mProgramAirtimes;
    private TextView mProgramDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View content = inflater.inflate(R.layout.activity_program, mContentView, true);
        mProgramTitle = (TextView) content.findViewById(R.id.program_activity_title);
        mProgramDescription = (TextView) content.findViewById(R.id.program_activity_description);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromIntent(getIntent());
    }

    private void getDataFromIntent(Intent intent) {
        mProgram = intent.getParcelableExtra(PROGRAM_DATA_KEY);
        if(mProgram != null) {
            mProgramTitle.setText(mProgram.getTitle());
            mProgramDescription.setText(mProgram.getFullDescription());
        }
    }
}
