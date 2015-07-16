package creek.fm.doublea.kzfr.adapters;

import android.content.Context;
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
import creek.fm.doublea.kzfr.models.Host;
import creek.fm.doublea.kzfr.models.Image;
import creek.fm.doublea.kzfr.models.Program;
import creek.fm.doublea.kzfr.utils.Utils;

/**
 * Created by Aaron on 6/26/2015.
 */
public class HostListAdapter extends RecyclerView.Adapter<HostListAdapter.ViewHolder> {
    private static final String TAG = HostListAdapter.class.getSimpleName();

    private final LayoutInflater mInflater;
    private ArrayList<Host> mHosts = new ArrayList<>();
    private Context mContext;
    private int mLastPosition = -1;

    public HostListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public HostListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(mInflater.inflate(R.layout.hosts_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(HostListAdapter.ViewHolder holder, int position) {
        holder.bind(mHosts.get(position));
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

    public void setProgramsData(List<Host> hostData) {
        mHosts.clear();
        mHosts.addAll(hostData);
    }

    public boolean isEmpty() {
        if (mHosts != null && !mHosts.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return mHosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.host_card_view)
        CardView mCardView;
        @Bind(R.id.card_view_hosts_programs)
        TextView mHostPrograms;
        @Bind(R.id.card_view_host_name)
        TextView mHostName;
        @Bind(R.id.host_image)
        ImageView mImageView;

        private Host mHost;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void bind(Host host) {
            mHost = host;
            setupHostName(mHost.getDisplayName());
            List<Program> programs = mHost.getPrograms();
            listHostPrograms(programs);
            Image imageUrls = mHost.getImage();
            if (imageUrls != null) {
                loadHostImage(imageUrls.getUrlSm());
            } else {
                loadDefaultHostImage();
            }
        }

        private void listHostPrograms(List<Program> programs) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Programs:\n");
            for(Program program:programs) {
                stringBuilder
                        .append(program.getTitle())
                        .append("\n");
            }
            mHostPrograms.setText(stringBuilder.toString());
        }

        private void loadHostImage(String imageUrl) {
            mImageView.setVisibility(View.VISIBLE);
            Picasso.with(mContext)
                    .load(imageUrl)
                    .resize(Utils.convertDpToPx(mContext, 108), Utils.convertDpToPx(mContext, 108))
                    .onlyScaleDown()
                    .centerInside()
                    .into(mImageView);
        }

        private void loadDefaultHostImage() {
            Picasso.with(mContext)
                    .load(R.mipmap.host_default_image)
                    .resize(Utils.convertDpToPx(mContext, 108), Utils.convertDpToPx(mContext, 108))
                    .onlyScaleDown()
                    .centerInside()
                    .into(mImageView);
        }

        private void setupHostName(String hostName) {
            if (!hostName.isEmpty()) {
                mHostName.setText(hostName);
            } else {
                mHostName.setText(mContext.getString(R.string.unknown_host_name));
            }
        }
    }
}
