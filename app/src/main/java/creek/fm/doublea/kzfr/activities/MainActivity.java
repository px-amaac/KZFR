package creek.fm.doublea.kzfr.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.fragments.NowPlayingFragment;
import creek.fm.doublea.kzfr.services.NowPlayingService;


public class MainActivity extends AppCompatActivity implements NowPlayingFragment.GetMainService {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String UPDATE_PLAYER = TAG + ".update_player";
    public static final String BUFFERING = TAG + ".buffering_player";


    // Butterknife view injections
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.main_toolbar)
    Toolbar mMainActionToolbar;
    @Bind(R.id.navigation)
    NavigationView mNavigationView;
    @Bind(R.id.content)
    FrameLayout mContentView;
    @Bind(R.id.progress_spinner)
    protected ProgressBar mProgressBar;

    ActionBarDrawerToggle actionBarDrawerToggle;
    private NowPlayingService mNowPlayingService;
    private boolean mBound;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NowPlayingService.MediaPlayerBinder binder = (NowPlayingService.MediaPlayerBinder) service;
            mNowPlayingService = binder.getService();
            updateMediaPlayerToggle();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    /**
     * this broadcast receiver is receiving intents from the media player service and calling the
     * appropriate methods to update the media player bar.
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(UPDATE_PLAYER)) {
                updateMediaPlayerToggle();
            } else if (intent.getAction().equals(BUFFERING)) {
                showMediaPlayerBuffering();
            }
        }
    };

    private void showMediaPlayerBuffering() {
        NowPlayingFragment nowPlayingFragment = (NowPlayingFragment) getSupportFragmentManager().findFragmentById(R.id.now_playing_fragment);
        if (nowPlayingFragment != null) {
            nowPlayingFragment.showBuffering(true);
        }
    }

    private void updateMediaPlayerToggle() {
        NowPlayingFragment nowPlayingFragment = (NowPlayingFragment) getSupportFragmentManager().findFragmentById(R.id.now_playing_fragment);
        if (nowPlayingFragment != null) {
            nowPlayingFragment.updateToggle();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mMainActionToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            setupActionBar(actionBar);
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.open_drawer_content_description,
                R.string.close_drawer_content_description);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        mNavigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);


    }

    /**
     * Register the broadcast receiver te receive intents from the media player service.
     */
    private void registerBroadcastReceiver() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter updateIntentFilter = new IntentFilter();
        updateIntentFilter.addAction(UPDATE_PLAYER);
        updateIntentFilter.addAction(BUFFERING);
        broadcastManager.registerReceiver(broadcastReceiver, updateIntentFilter);
    }

    private void unRegisterBroadcastReceiver() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            switch (itemId) {
                case R.id.schedule:
                    Intent scheduleIntent = new Intent(MainActivity.this, ScheduleActivity.class);
                    startActivity(scheduleIntent);
                    break;
                case R.id.programs:
                    Intent programIntent = new Intent(MainActivity.this, ProgramsActivity.class);
                    startActivity(programIntent);
                    break;
                case R.id.hosts:
                    Intent hostIntent = new Intent(MainActivity.this, HostsActivity.class);
                    startActivity(hostIntent);
                    break;
                case R.id.website:
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://kzfr.org"));
                    startActivity(browserIntent);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadcastReceiver();
        Intent mediaIntent = new Intent(this, NowPlayingService.class);
        startService(mediaIntent);
        if (mNowPlayingService == null)
            bindService(mediaIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //sync state needed to make the drawer menu item appear
        actionBarDrawerToggle.syncState();
    }

    public void setupActionBar(ActionBar actionBar) {
        actionBar.setIcon(R.mipmap.kzfr_logo);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        centerNavDrawerIconVertically();
    }

    /**
     * The navigation drawer icon by default is not centered vertically. This method centers the
     * navigation drawer icon in the app bar layout
     * Solution found http://stackoverflow.com/questions/29003584/set-actionbardrawertoggle-in-center-of-toolbar-or-actionbar-android
     */
    public void centerNavDrawerIconVertically() {
        for (int i = 0; i < mMainActionToolbar.getChildCount(); i++) {
            // make toggle drawable center-vertical, you can make each view alignment whatever you want
            if (mMainActionToolbar.getChildAt(i) instanceof ImageButton) {
                Toolbar.LayoutParams lp = (Toolbar.LayoutParams) mMainActionToolbar.getChildAt(i).getLayoutParams();
                lp.gravity = Gravity.CENTER_VERTICAL;
            }
        }
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
        mDrawerLayout.closeDrawers();
        sendStopIntent();
        if (mNowPlayingService != null) {
            unbindService(mServiceConnection);
            mNowPlayingService = null;
        }
        unRegisterBroadcastReceiver();
        mBound = false;
    }

    /**
     * Stop intent is sent to the service so the service can determine if it should stop. If the
     * media player is paused when any activity triggers onPause then the service should stop itself.
     */
    private void sendStopIntent() {
        Intent stopIntent = new Intent(this, NowPlayingService.class);
        stopIntent.setAction(NowPlayingService.ACTION_CLOSE);
        startService(stopIntent);
    }

    /**
     * This method will hide or show the progrss bar depending on the boolean passed in.
     * This method should be called on the ui thread.
     *
     * @param shouldShow
     */
    protected void showProgressBar(boolean shouldShow) {
        if (shouldShow) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public NowPlayingService getMainService() {
        return mNowPlayingService;
    }
}
