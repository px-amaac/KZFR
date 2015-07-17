package creek.fm.doublea.kzfr.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.activities.MainActivity;
import creek.fm.doublea.kzfr.activities.ScheduleActivity;


/**
 * Service that controls the media player and the notification that represents it.
 */
public class NowPlayingService extends Service implements AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener {
    private static final String TAG = NowPlayingService.class.getSimpleName();

    private final IBinder mMediaPlayerBinder = new MediaPlayerBinder();
    public static final String ACTION_PLAY = "creek.fm.doublea.kzfr.services.PLAY";
    public static final String ACTION_PAUSE = "creek.fm.doublea.kzfr.services.PAUSE";
    private static final String ACTION_CLOSE = "creek.fm.doublea.kzfr.services.APP_CLOSE";
    public static final String ACTION_CLOSE_IF_PAUSED = "creek.fm.doublea.kzfr.services.APP_CLOSE_IF_PAUSED";
    private static final int NOTIFICATION_ID = 4223;
    private MediaPlayer mMediaPlayer = null;
    private AudioManager mAudioManager = null;

    //The URL that feeds the KZFR stream.
    private static final String mStreamUrl = "http://stream-tx1.radioparadise.com:8090/;stream/1";

    //Wifi Lock to ensure the wifo does not ge to sleep while we are stearming music.
    private WifiManager.WifiLock mWifiLock;

    enum State {
        Retrieving, // the MediaRetriever is retrieving music
        Stopped,  //Media player is stopped and not prepared to play
        Preparing, // Media player is preparing to play
        Playeng,  // MediaPlayer playback is active.
        // There is a chance that the MP is actually paused here if we do not have audio focus.
        // We stay in this state so we know to resume when we gain audio focus again.
        Paused // Audio Playback is paused
    }

    private State mState = State.Stopped;

    enum AudioFocus {
        NoFocusNoDuck, // service does not have audio focus and cannot duck
        NoFocusCanDuck, // we don't have focus but we can play at low volume ("ducking")
        Focused  // media player has full audio focus
    }

    private AudioFocus mAudioFocus = AudioFocus.NoFocusNoDuck;

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                mAudioFocus = AudioFocus.Focused;
                // resume playback
                if (mState == State.Playeng) {
                    startMediaPlayer();
                    mMediaPlayer.setVolume(1.0f, 1.0f);
                }
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                mAudioFocus = AudioFocus.NoFocusNoDuck;
                // Lost focus for an unbounded amount of time: stop playback and release media player
                stopMediaPlayer();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                mAudioFocus = AudioFocus.NoFocusNoDuck;
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                processPauseRequest();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                mAudioFocus = AudioFocus.NoFocusCanDuck;
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mMediaPlayer.isPlaying()) mMediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        startMediaPlayer();
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }


    private void setupAudioManager() {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        }
    }

    private void setupWifiLock() {
        if (mWifiLock == null) {
            mWifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE))
                    .createWifiLock(WifiManager.WIFI_MODE_FULL, "mediaplayerlock");
        }
    }

    private void setupMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnInfoListener(this);
            mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mMediaPlayer.setDataSource(this, Uri.parse(mStreamUrl));
            } catch (IOException e) {
                e.printStackTrace();
                stopSelf();
            }
        }
    }

    /**
     * The radio streaming service runs in forground mode to keep the Android OS from killing it.
     * The OnStartCommand is called every time there is a call to start service and the service is
     * already started. By Passing an intent to the onStartCommand we can play and pause the music.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = null;
        if (intent != null) {
            action = intent.getAction();
        }
        if (action != null) {
            switch (action) {
                case ACTION_PLAY:
                    processPlayRequest();
                    break;
                case ACTION_PAUSE:
                    processPauseRequest();
                    break;
                case ACTION_CLOSE_IF_PAUSED:
                    closeIfPaused();
                    break;
                case ACTION_CLOSE:
                    close();
                    break;
            }
        }
        return START_STICKY; //do not restart service if it is killed.
    }

    //if the media player is paused or stopped and this method has been triggered then stop the service.
    private void closeIfPaused() {
        if (mState == State.Paused || mState == State.Stopped) {
            close();
        }
    }

    private void close() {
        removeNotification();
        stopSelf();
    }

    private void initMediaPlayer() {
        setupMediaPlayer();
        requestResources();
    }

    /**
     * Check if the media player was initialized and we have audio focus.
     * Without audio focus we do not start the media player.
     * change state and start to prepare async
     */
    private void configAndPrepareMediaPlayer() {
        initMediaPlayer();
        mState = State.Preparing;
        buildNotification(true);
        mMediaPlayer.prepareAsync();

    }

    /**
     * The media player is prepared check to make sure we are not in the stopped or paused states
     * before starting the media player
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mState != State.Paused && mState != State.Stopped) {
            startMediaPlayer();
        }
    }

    /*
        Check if the media player is available and start it.
     */
    private void startMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            sendUpdatePlayerIntent();
            mState = State.Playeng;
            buildNotification(false);
        }
    }

    private void sendUpdatePlayerIntent() {
        Log.d(TAG, "updatePlayerIntent");
        Intent updatePlayerIntent = new Intent(MainActivity.UPDATE_PLAYER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(updatePlayerIntent);
    }

    /*
        Request audio focus and aquire a wifi lock. Returns true if audio focus was granted.
     */
    private void requestResources() {
        setupAudioManager();
        setupWifiLock();
        mWifiLock.acquire();

        tryToGetAudioFocus();
    }

    private void tryToGetAudioFocus() {
        if (mAudioFocus != AudioFocus.Focused && AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN))
            mAudioFocus = AudioFocus.Focused;

    }

    /**
     * if the Media player is playing then stop it. Change the state and relax the wifi lock and
     * audio focus.
     */
    private void stopMediaPlayer() {
        // Lost focus for an unbounded amount of time: stop playback and release media player
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mState = State.Stopped;
        //relax the resources because we no longer need them.
        relaxResources();
        giveUpAudioFocus();
    }

    private void processPlayRequest() {
        if (mState == State.Stopped) {
            sendBufferingIntent();
            configAndPrepareMediaPlayer();
        } else if (mState == State.Paused) {
            requestResources();
            startMediaPlayer();
        }
    }

    //send an intent telling any activity listening to this intent that the media player is buffering.
    private void sendBufferingIntent() {
        Intent bufferingPlayerIntent = new Intent(MainActivity.BUFFERING);
        LocalBroadcastManager.getInstance(this).sendBroadcast(bufferingPlayerIntent);
    }

    private void processPauseRequest() {

        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            sendUpdatePlayerIntent();
            mState = State.Paused;
            relaxResources();
            buildNotification(false);
        }
    }

    /**
     * There is no media style notification for operating systems below api 21. So This method builds
     * a simple compat notification that has a play or pause button depending on if the player is
     * paused or played. if foreGroundOrUpdate then the service should go to the foreground. else
     * just update the notification.
     */
    private void buildNotification(boolean startForeground) {
        Intent intent = new Intent(getApplicationContext(), NowPlayingService.class);
        intent.setAction(ACTION_CLOSE);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("KZFR Radio").setContentText("Streaming Live")
                .setSmallIcon(R.mipmap.kzfr_logo_small).setOngoing(true)
                .setContentIntent(getMainContentIntent())
                .setDeleteIntent(pendingIntent);
        if (mState == State.Paused || mState == State.Stopped) {
            builder.addAction(generateAction(android.R.drawable.ic_media_play, "Play", ACTION_PLAY));
        } else {
            builder.addAction(generateAction(android.R.drawable.ic_media_pause, "Pause", ACTION_PAUSE));
        }
        builder.addAction(generateAction(android.R.drawable.ic_menu_close_clear_cancel, "Close", ACTION_CLOSE));

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        if (startForeground)
            startForeground(NOTIFICATION_ID, builder.build());
        else
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private PendingIntent getMainContentIntent() {
        Intent resultIntent = new Intent(this, ScheduleActivity.class);
        return PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private NotificationCompat.Action generateAction(int icon, String title, String intentAction) {
        Intent intent = new Intent(getApplicationContext(), NowPlayingService.class);
        intent.setAction(intentAction);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        return new NotificationCompat.Action.Builder(icon, title, pendingIntent).build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMediaPlayerBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onDestroy() {
        stopMediaPlayer();
    }

    //give up wifi lock if it is held and stop the service from being a foreground service.
    private void relaxResources() {

        //Release the WifiLock resource
        if (mWifiLock != null && mWifiLock.isHeld()) {
            mWifiLock.release();
        }


        // stop service from being a foreground service. Passing true removes the notification as well.
        stopForeground(true);

    }

    private void removeNotification() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        notificationManagerCompat.cancel(NOTIFICATION_ID);
    }

    private void giveUpAudioFocus() {
        if ((mAudioFocus == AudioFocus.Focused || mAudioFocus == AudioFocus.NoFocusCanDuck) &&
                AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAudioManager.abandonAudioFocus(this)) {
            mAudioFocus = AudioFocus.NoFocusNoDuck;
        }
    }

    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    public class MediaPlayerBinder extends Binder {

        public NowPlayingService getService() {
            return NowPlayingService.this;
        }
    }
}
