package creek.fm.doublea.kzfr.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.activities.ShowActivity;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.models.NowPlaying;
import creek.fm.doublea.kzfr.services.NowPlayingService;
import creek.fm.doublea.kzfr.utils.Utils;
import retrofit.client.Response;

/**
 * Fragment that displays the currently playing show title and communicates with the media player
 * service to play and pause the media player.
 */
public class NowPlayingFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = NowPlayingFragment.class.getSimpleName();

    @Bind(R.id.play_pause_button)
    ToggleButton mPlayPauseButton;
    @Bind(R.id.media_player_title)
    TextView mMediaPlayerTitle;
    @Bind(R.id.media_player_time)
    TextView mMediaPlayerTime;
    @Bind(R.id.buffering_title)
    TextView mBuffering;
    @Bind(R.id.media_player_data)
    RelativeLayout mPlayerData;


    private GetMainService sGetMainService;
    private NowPlaying mNowPlaying;

    public interface GetMainService {
        NowPlayingService getMainService();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            sGetMainService = (GetMainService) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement GetMainService Interface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_media_controls, container, false);
        ButterKnife.bind(this, v);
        mPlayPauseButton.setOnClickListener(this);
        executeApiCall();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToggle();
    }

    private void executeApiCall() {
        ApiClient.getKZFRApiClient(getActivity()).getCurrentBroadcast(new KZFRRetrofitCallback<NowPlaying>() {

            @Override
            public void success(NowPlaying nowPlaying, Response response) {
                super.success(nowPlaying, response);
                addDataToView(nowPlaying);

            }
        });
    }

    private void addDataToView(NowPlaying nowPlaying) {
        mNowPlaying = nowPlaying;
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMediaPlayerTitle.setText(mNowPlaying.getNow().getTitle());
                    mMediaPlayerTime.setText(Utils.getFriendlyAirTime(mNowPlaying.getNow().getAirtime()));
                }
            });
        }

    }

    /**
     * This method is called by the main activity when it receives a broadcast from the media player
     * service. This tells the media player bar to check if the media player is playing and set the
     * play pause button accordingly.
     */
    public void updateToggle() {
        NowPlayingService mainService = sGetMainService.getMainService();

        //The media player might have been buffering so set the buffering view to gone.
        showBuffering(false);

        if (mainService != null) {
            mPlayPauseButton.setChecked(sGetMainService.getMainService().isPlaying());
        } else {
            mPlayPauseButton.setChecked(false);
        }
    }

    /**
     * If the media player is buffering on setting itself up this method is called to make the ui
     * changes indicating that the media player is currently buffering.
     *
     * @param isBuffering
     */
    public void showBuffering(boolean isBuffering) {
        if (isBuffering) {
            mBuffering.setVisibility(View.VISIBLE);
            mMediaPlayerTime.setVisibility(View.GONE);
        } else {
            mBuffering.setVisibility(View.GONE);
            mMediaPlayerTime.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play_pause_button) {
            Intent mediaPlayerIntent = new Intent(getActivity(), NowPlayingService.class);
            if (mPlayPauseButton.isChecked()) {//media player is playing or the play intent has been passed to the media player
                //if its playing then user clicked the pause button so sent pause intent.
                mediaPlayerIntent.setAction(NowPlayingService.ACTION_PLAY);
            } else {
                mediaPlayerIntent.setAction(NowPlayingService.ACTION_PAUSE);
            }
            getActivity().startService(mediaPlayerIntent);
        }
    }

    /**
     * OnClick action for the data in the media player bar. This method launches the show intent
     * with an extra show id so it displays the up next button.
     */
    @OnClick(R.id.media_player_data)
    public void onMediaPlayerDataClicked() {
        if (mNowPlaying != null) {
            Intent showIntent = new Intent(getActivity(), ShowActivity.class);
            showIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            showIntent.putExtra(ShowActivity.SHOW_ID_KEY, Integer.valueOf(mNowPlaying.getNow().getId()));
            showIntent.putExtra(ShowActivity.NEXT_SHOW_ID_KEY, Integer.valueOf(mNowPlaying.getNext().getId()));
            getActivity().startActivity(showIntent);
        }
    }


}
