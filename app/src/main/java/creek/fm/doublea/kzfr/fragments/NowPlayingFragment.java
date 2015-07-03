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
import creek.fm.doublea.kzfr.Utils;
import creek.fm.doublea.kzfr.activities.ProgramActivity;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.models.NowPlaying;
import creek.fm.doublea.kzfr.services.NowPlayingService;
import retrofit.client.Response;

/**
 * Created by Aaron on 6/10/2015.
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


    GetMainService sGetMainService;
    NowPlaying mNowPlaying;

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

    // Show the buffering
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

    @OnClick(R.id.media_player_data)
    public void onMediaPlayerDataClicked() {
        if (mNowPlaying != null) {
            Intent programIntent = new Intent(getActivity(), ProgramActivity.class);
            programIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            programIntent.putExtra(ProgramActivity.PROGRAM_DATA_KEY, mNowPlaying.getNow());
            getActivity().startActivity(programIntent);
        }
    }


}
