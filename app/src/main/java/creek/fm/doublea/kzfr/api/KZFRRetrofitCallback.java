package creek.fm.doublea.kzfr.api;

import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Aaron on 6/21/2015.
 */
public class KZFRRetrofitCallback<S> implements Callback<S> {
    private static final String TAG = KZFRRetrofitCallback.class.getSimpleName();


    @Override
    public void success(S s, Response response) {

    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(TAG, "Failed to make http request for: " + error.getUrl());
        Response errorResponse = error.getResponse();
        if (errorResponse != null) {
            Log.e(TAG, errorResponse.getReason());
            if (errorResponse.getStatus() == 500) {
                Log.e(TAG, "Server Side errors");
            }
        }
    }
}
