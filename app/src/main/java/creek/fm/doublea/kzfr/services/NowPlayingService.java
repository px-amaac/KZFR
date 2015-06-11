package creek.fm.doublea.kzfr.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Aaron on 6/10/2015.
 */
public class NowPlayingService extends Service {

    private final IBinder playerBind = new MediaPlayerBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return playerBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MediaPlayerBinder extends Binder {
        NowPlayingService getService() {
            return NowPlayingService.this;
        }

    }
}
