package creek.fm.doublea.kzfr.api;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import creek.fm.doublea.kzfr.models.Week;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;

/**
 * Created by Aaron on 6/21/2015.
 */
public class ApiClient {

    private static KZFRApiInterface sKZFRApiInterface;


    public static KZFRApiInterface getKZFRApiClient(Context context) {
        Executor executor = Executors.newCachedThreadPool();

        //setup http cache for get requests
        File cacheDir = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB

        Cache cache = new Cache(cacheDir, cacheSize);
        OkHttpClient mClient = new OkHttpClient();
        mClient.setCache(cache);

        //build the rest adapter
        if(sKZFRApiInterface == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setExecutors(executor, executor)
                    .setEndpoint("http://kzfr.org/api")
                    .setClient(new OkClient(mClient))
                    .build();
            sKZFRApiInterface = restAdapter.create(KZFRApiInterface.class);
        }
        return sKZFRApiInterface;
    }



    public interface KZFRApiInterface {
        @GET("/schedule")
        void getSchedule(KZFRRetrofitCallback<Week> callback);
    }
}
