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
import creek.fm.doublea.kzfr.activities.ShowActivity;
import creek.fm.doublea.kzfr.models.Airtime;
import creek.fm.doublea.kzfr.models.Image;
import creek.fm.doublea.kzfr.models.Show;
import creek.fm.doublea.kzfr.utils.Utils;

/**
 * Created by Aaron on 6/26/2015.
 */
public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.ViewHolder> {
    private static final String TAG = ShowListAdapter.class.getSimpleName();

    private final LayoutInflater mInflater;
    private ArrayList<Show> mShows = new ArrayList<>();
    private Context mContext;
    private int mLastPosition = -1;

    public ShowListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ShowListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(mInflater.inflate(R.layout.show_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ShowListAdapter.ViewHolder holder, int position) {
        holder.bind(mShows.get(position));
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

    public void setShowsData(List<Show> showData) {
        mShows.clear();
        mShows.addAll(showData);
    }

    public boolean isEmpty() {
        if (mShows != null && !mShows.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return mShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.show_card_view)
        CardView mCardView;
        @Bind(R.id.show_time)
        TextView mTime;
        @Bind(R.id.show_title)
        TextView mTitle;
        @Bind(R.id.show_description)
        TextView mDescription;
        @Bind(R.id.show_image)
        ImageView mImageView;

        private Show mShowItem;

        public ViewHolder(View v) {
            super(v);
            Log.d(TAG, "ViewHolder Constructor");
            ButterKnife.bind(this, v);
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent showIntent = new Intent(mContext, ShowActivity.class);
                    showIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    showIntent.putExtra(ShowActivity.SHOW_ID_KEY, Integer.valueOf(mShowItem.getId()));
                    mContext.startActivity(showIntent);
                }
            });
        }

        public void bind(Show show) {
            Log.d(TAG, "bind day");
            mShowItem = show;
            setAirtimeText();
            mTitle.setText(mShowItem.getTitle());
            mDescription.setText(mShowItem.getShortDescription());
            String imageUrl = null;
            Image imageUrls = mShowItem.getImage();
            if (imageUrls != null)
                imageUrl = imageUrls.getUrlSm();

            Picasso.with(mContext)
                    .load(imageUrl)
                    .resize(Utils.convertDpToPx(mContext, 112), Utils.convertDpToPx(mContext, 112))
                    .onlyScaleDown()
                    .centerInside()
                    .into(mImageView);
        }

        private void setAirtimeText() {
            List<Airtime> airtimes = mShowItem.getAirtimes();
            if (!airtimes.isEmpty()) {
                mTime.setText(Utils.getFriendlyAirtimes(airtimes));
            } else {
                mTime.setText(
                        Utils.getFriendlyAirTime(mShowItem.getAirtime()));
            }
        }
    }
}
