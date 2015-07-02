package creek.fm.doublea.kzfr.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.models.Broadcast;
import creek.fm.doublea.kzfr.services.NowPlayingService;

/**
 * Created by Aaron on 6/10/2015.
 */
public class NowPlayingFragment extends Fragment implements View.OnClickListener {
    @InjectView(R.id.play_pause_button)
    ToggleButton mPlayPauseButton;

    GetMainService sGetMainService;

    public interface GetMainService {
        public NowPlayingService getMainService();
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
        ButterKnife.inject(this, v);
        mPlayPauseButton.setOnClickListener(this);

        return v;
    }

    public void addData(Broadcast broadcast) {

    }

    public void updateToggle() {
        NowPlayingService mainService = sGetMainService.getMainService();
        if (mainService != null) {
            mPlayPauseButton.setChecked(sGetMainService.getMainService().isPlaying());
        } else
            mPlayPauseButton.setChecked(false);
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
}
