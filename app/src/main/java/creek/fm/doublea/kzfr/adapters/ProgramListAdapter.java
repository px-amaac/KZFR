package creek.fm.doublea.kzfr.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.activities.ProgramActivity;
import creek.fm.doublea.kzfr.models.Image;
import creek.fm.doublea.kzfr.models.Program;
import creek.fm.doublea.kzfr.utils.Utils;

/**
 * Created by Aaron on 6/26/2015.
 */
public class ProgramListAdapter extends RecyclerView.Adapter<ProgramListAdapter.ViewHolder> {
    private static final String TAG = ProgramListAdapter.class.getSimpleName();

    private final LayoutInflater mInflater;
    private ArrayList<Program> mPrograms = new ArrayList<>();
    private Context mContext;
    private int mLastPosition = -1;

    public ProgramListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ProgramListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(mInflater.inflate(R.layout.program_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ProgramListAdapter.ViewHolder holder, int position) {
        holder.bind(mPrograms.get(position));
        setAnimation(holder.mCardView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    public void setProgramsData(List<Program> programData) {
        mPrograms.clear();
        mPrograms.addAll(programData);
    }

    public boolean isEmpty() {
        if (mPrograms != null && !mPrograms.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return mPrograms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.day_card_view)
        CardView mCardView;
        @Bind(R.id.program_time)
        TextView mTime;
        @Bind(R.id.program_title)
        TextView mTitle;
        @Bind(R.id.program_description)
        TextView mDescription;
        @Bind(R.id.program_image)
        ImageView mImageView;

        private Program mProgramItem;

        public ViewHolder(View v) {
            super(v);
            Log.d(TAG, "ViewHolder Constructor");
            ButterKnife.bind(this, v);
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent programIntent = new Intent(mContext, ProgramActivity.class);
                    programIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    programIntent.putExtra(ProgramActivity.PROGRAM_ID_KEY, Integer.valueOf(mProgramItem.getId()));
                    mContext.startActivity(programIntent);
                }
            });
        }

        public void bind(Program program) {
            Log.d(TAG, "bind day");
            mProgramItem = program;
            mTime.setText(Utils.getFriendlyAirTime(mProgramItem.getAirtime()));
            mTitle.setText(mProgramItem.getTitle());
            mDescription.setText(mProgramItem.getShortDescription());
            String imageUrl = null;
            Image imageUrls = mProgramItem.getImage();
            if (imageUrls != null)
                imageUrl = imageUrls.getUrlSm();

            Picasso.with(mContext)
                    .load(imageUrl)
                    .resize(Utils.convertDpToPx(mContext, 112), Utils.convertDpToPx(mContext, 112))
                    .onlyScaleDown()
                    .centerInside()
                    .into(mImageView);
        }
    }
}
