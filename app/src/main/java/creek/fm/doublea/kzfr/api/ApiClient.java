package creek.fm.doublea.kzfr.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import creek.fm.doublea.kzfr.models.Day;
import creek.fm.doublea.kzfr.models.Host;
import creek.fm.doublea.kzfr.models.Image;
import creek.fm.doublea.kzfr.models.JsonDeserializer;
import creek.fm.doublea.kzfr.models.NowPlaying;
import creek.fm.doublea.kzfr.models.Program;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;

/**
 * Created by Aaron on 6/21/2015.
 */
public class ApiClient {

    private static KZFRApiInterface sKZFRApiInterface;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Image.class, new JsonDeserializer<Image>())
            .create();

    public static KZFRApiInterface getKZFRApiClient(Context context) {
        Executor executor = Executors.newCachedThreadPool();

        //setup http cache for get requests
        File cacheDir = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB

        Cache cache = new Cache(cacheDir, cacheSize);
        OkHttpClient mClient = new OkHttpClient();
        mClient.setCache(cache);


        //build the rest adapter
        if (sKZFRApiInterface == null) {
            final RestAdapter restAdapter = new RestAdapter.Builder()
                    .setConverter(new GsonConverter(gson))
                    .setExecutors(executor, executor)
                    .setEndpoint("http://kzfr.org/api")
                    .setClient(new OkClient(mClient))
                    .build();
            sKZFRApiInterface = restAdapter.create(KZFRApiInterface.class);
        }
        return sKZFRApiInterface;
    }


    public interface KZFRApiInterface {

        /*http://kzfr.org/api/schedule*/
        @GET("/schedule")
        void getSchedule(KZFRRetrofitCallback<HashMap<String, Day>> callback);

        /*http://kzfr.org/api/broadcasting*/
        @GET("/broadcasting")
        void getCurrentBroadcast(KZFRRetrofitCallback<NowPlaying> callback);

        /*http://kzfr.org/api/host/{HOST_ID}*/
        @GET("/host/{id}")
        void getHost(KZFRRetrofitCallback<Host> callback);

        /*http://kzfr.org/api/show/{PROGRAM_ID}*/
        @GET("/show/{id}")
        void getProgram(KZFRRetrofitCallback<Program> callback);

    }


}
