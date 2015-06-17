package creek.fm.doublea.kzfr.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.services.NowPlayingService;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActionBarDrawerToggle actionBarDrawerToggle;
    private NowPlayingService mNowPlayingService;
    private boolean mBound;
    private ToggleButton mPlayPauseButton;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NowPlayingService.MediaPlayerBinder binder = (NowPlayingService.MediaPlayerBinder) service;
            mNowPlayingService = binder.getService();
            updateToggle();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar mainActionToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        mPlayPauseButton = (ToggleButton) findViewById(R.id.play_pause_button);
        mPlayPauseButton.setOnClickListener(this);
        setSupportActionBar(mainActionToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            setupActionBar(actionBar);
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.open_drawer_content_description,
                R.string.close_drawer_content_description) {
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                return super.onOptionsItemSelected(item);
                //TODO add actions here for the drawer menu
            }
        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent mediaIntent = new Intent(this, NowPlayingService.class);
        startService(mediaIntent);
        if (!mBound)
            bindService(mediaIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        else
            updateToggle();
    }

    private void updateToggle() {
        mPlayPauseButton.setChecked(mNowPlayingService.isPlaying());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public void setupActionBar(ActionBar actionBar) {
        actionBar.setIcon(R.mipmap.kzfr_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO add share button to action bar in v2.0
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        actionBarDrawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sendStopIntent();
        unbindService(mServiceConnection);
        mBound = false;
    }

    /* Stop intent is sent to the service so the service can determine if it should stop. If the
       media player is paused when any activity triggers onPause then the service should stop itself.
      */
    private void sendStopIntent() {
        Intent stopIntent = new Intent(this, NowPlayingService.class);
        stopIntent.setAction(NowPlayingService.ACTION_CLOSE);
        startService(stopIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play_pause_button) {
            Intent mediaPlayerIntent = new Intent(this, NowPlayingService.class);
            if (mPlayPauseButton.isChecked()) {//media player is playing or the play intent has been passed to the media player
                //if its playing then user clicked the pause button so sent pause intent.
                mediaPlayerIntent.setAction(NowPlayingService.ACTION_PLAY);
            } else {
                mediaPlayerIntent.setAction(NowPlayingService.ACTION_PAUSE);
            }
            startService(mediaPlayerIntent);
        }
    }
}
