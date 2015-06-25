package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.Map;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.models.Day;
import creek.fm.doublea.kzfr.models.Week;
import retrofit.client.Response;

/**
 * Created by Aaron on 6/22/2015.
 */
public class ScheduleActivity extends MainActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.activity_schedule, mContentView, true);
        Button apiCall = (Button) findViewById(R.id.apibutton);
        apiCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int viewId = v.getId();
        ApiClient.getKZFRApiClient(this).getSchedule(new KZFRRetrofitCallback<Map<String, Day>>() {

            @Override
            public void success(Map<String, Day> days, Response response) {
                Log.e("CALLBACK!!!!!!!!!!!!!", "Success");
            }
        });

    }
}
