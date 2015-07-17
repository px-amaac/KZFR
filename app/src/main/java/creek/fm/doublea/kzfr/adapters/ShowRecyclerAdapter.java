package creek.fm.doublea.kzfr.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.activities.ShowActivity;
import creek.fm.doublea.kzfr.models.Airtime;
import creek.fm.doublea.kzfr.models.Category;
import creek.fm.doublea.kzfr.models.Host;
import creek.fm.doublea.kzfr.models.Image;
import creek.fm.doublea.kzfr.models.Show;
import creek.fm.doublea.kzfr.utils.Utils;

/**
 * Created by Aaron on 7/6/2015.
 */
public class ShowRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    abstract class ShowBaseViewHolder extends RecyclerView.ViewHolder {

        public ShowBaseViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(int position, Show show);
    }

    class AirtimeViewHolder extends ShowBaseViewHolder {
        @Bind(R.id.show_airtimes)
        TextView mShowAirtimes;

        static final int viewType = 14;

        public AirtimeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(int position, Show show) {
            List<Airtime> airtimes = show.getAirtimes();
            if (!airtimes.isEmpty()) {
                mShowAirtimes.setText(Utils.getFriendlyAirtimes(airtimes));
            } else {
                mShowAirtimes.setText(
                        Utils.getFriendlyAirTime(show.getAirtime()));
            }
        }
    }

    class DescriptionViewHolder extends ShowBaseViewHolder {
        @Bind(R.id.description_card_layout)
        TextView mShowDescription;

        static final int viewType = 13;

        public DescriptionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(int position, Show show) {
            String showDescription = show.getFullDescription();
            if (showDescription.isEmpty()) {
                showDescription = show.getShortDescription();
            }
            if (!showDescription.isEmpty()) {
                mShowDescription.setText(Html.fromHtml(showDescription));
            }
        }
    }

    class HostsViewHolder extends ShowBaseViewHolder {
        @Bind(R.id.card_view_host_name)
        TextView mHostName;
        @Bind(R.id.host_image)
        ImageView mImageView;

        static final int viewType = 12;

        public HostsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(int position, Show show) {
            Host host = show.getHosts().get(position - (mDescriptionPosition + 1));
            String hostName = host.getDisplayName();
            if (!hostName.isEmpty()) {
                mHostName.setText(hostName);
            } else {
                mHostName.setText(mContext.getString(R.string.unknown_host_name));
            }
            Image imageUrls = host.getImage();
            if (imageUrls != null) {
                Picasso.with(mContext)
                        .load(imageUrls.getUrlSm())
                        .resize(Utils.convertDpToPx(mContext, 108), Utils.convertDpToPx(mContext, 108))
                        .onlyScaleDown()
                        .centerInside()
                        .into(mImageView);
            } else {
                Picasso.with(mContext)
                        .load(R.mipmap.host_default_image)
                        .resize(Utils.convertDpToPx(mContext, 108), Utils.convertDpToPx(mContext, 108))
                        .onlyScaleDown()
                        .centerInside()
                        .into(mImageView);
            }
        }
    }

    class CategoriesViewHolder extends ShowBaseViewHolder {
        @Bind(R.id.show_categories)
        TextView mShowCategories;
        static final int viewType = 11;

        public CategoriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(int position, Show show) {
            List<Category> categoryList = show.getCategories();
            StringBuilder stringBuilder = new StringBuilder();
            for (Category category : categoryList) {
                stringBuilder.append(category.getTitle())
                        .append(" ");
            }
            mShowCategories.setText(stringBuilder.toString());
        }
    }

    class UpNextViewHolder extends ShowBaseViewHolder {
        @Bind(R.id.up_next_image_view)
        ImageView mUpNextImageView;
        static final int viewType = 10;

        public UpNextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(int position, Show show) {

            mUpNextImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showIntent = new Intent(mContext, ShowActivity.class);
                    showIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    showIntent.putExtra(ShowActivity.SHOW_ID_KEY, Integer.valueOf(mNextShowId));
                    mContext.startActivity(showIntent);
                }
            });
        }
    }

    private Context mContext;
    private LayoutInflater mInflater;
    private Show mShowData;
    private int mNextShowId = -1;
    private boolean mHasAirTimes;
    private int mDescriptionPosition = -1;
    private int mLastHostPosition = -1;

    public ShowRecyclerAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * This method is very specific to the layout desired. The desired layout is as follows.
     * If there are any airtimes then the airtimes view is first. The description view will be
     * second unless there is no airtimes view then it will be first. After the description there
     * will be 0 or more hosts views followed by 0 or more categories in a single card view. If the
     * show view has a next show id then the last view is an up next button. this should only
     * happen if the show view is the current show running and was reached by clicking on the
     * media player bar.
     *
     * @param position the current position of the view about to be created.
     * @return the view type
     */
    @Override
    public int getItemViewType(int position) {

        int endPosition = -1;
        ensureDescriptionPosition();
        ensureLastHostPosition();

        if (position == 0 && mHasAirTimes) {
            endPosition = AirtimeViewHolder.viewType;
        } else if (position == mDescriptionPosition) {
            endPosition = DescriptionViewHolder.viewType;
        } else if (position > mDescriptionPosition && position <= mLastHostPosition) {
            endPosition = HostsViewHolder.viewType;
        } else if (position > mLastHostPosition && position <= mLastHostPosition + getCategoriesNum()) {
            endPosition = CategoriesViewHolder.viewType;
        } else if (position == getItemCount() - 1 && mNextShowId != -1) {
            endPosition = UpNextViewHolder.viewType;
        }
        return endPosition;
    }

    private void ensureDescriptionPosition() {
        if (mDescriptionPosition == -1) {
            if (mHasAirTimes) {
                mDescriptionPosition = 1;
            } else {
                mDescriptionPosition = 0;
            }
        }
    }

    private void ensureLastHostPosition() {
        if (mLastHostPosition == -1)
            mLastHostPosition = mDescriptionPosition + getHostsNum();
    }

    @Override
    public int getItemCount() {
        return 1 + getHasAirtimes() + getHostsNum() + getCategoriesNum() + hasNextShow();
    }

    private int getHasAirtimes() {
        mHasAirTimes = (mShowData != null && (!mShowData.getAirtimes().isEmpty() || mShowData.getAirtime() != null));
        return mHasAirTimes ? 1 : 0;
    }

    private int getHostsNum() {
        int hostNum = 0;
        if (mShowData != null) {
            hostNum = mShowData.getHosts().size();
        }
        return hostNum;
    }

    private int getCategoriesNum() {
        return (mShowData != null && mShowData.getCategories().size() > 0) ? 1 : 0;
    }

    private int hasNextShow() {
        return (mNextShowId != -1) ? 1 : 0;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder currentViewHolder = null;

        if (viewType == AirtimeViewHolder.viewType) {
            currentViewHolder = new AirtimeViewHolder(mInflater.inflate(R.layout.airtime_card_layout, parent, false));
        } else if (viewType == DescriptionViewHolder.viewType) {
            currentViewHolder = new DescriptionViewHolder(mInflater.inflate(R.layout.description_card_layout, parent, false));
        } else if (viewType == HostsViewHolder.viewType) {
            currentViewHolder = new HostsViewHolder(mInflater.inflate(R.layout.hosts_card_layout, parent, false));
        } else if (viewType == UpNextViewHolder.viewType) {
            currentViewHolder = new UpNextViewHolder(mInflater.inflate(R.layout.up_next_layout, parent, false));
        } else {
            currentViewHolder = new CategoriesViewHolder(mInflater.inflate(R.layout.categories_card_layout, parent, false));
        }
        return currentViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShowBaseViewHolder baseViewHolder = (ShowBaseViewHolder) holder;
        if (mShowData != null) {
            baseViewHolder.bind(position, mShowData);
        }
    }

    public void setShowData(Show show) {
        mShowData = show;
        mDescriptionPosition = -1;
        mLastHostPosition = -1;
    }

    public void setNextShowId(int nextShowId) {
        mNextShowId = nextShowId;
    }

    public Show getShowData() {
        return mShowData;
    }

    public int getNextShowId() {
        return mNextShowId;
    }

    public boolean isEmpty() {
        return mShowData == null;
    }

    private void resetViews() {
        mShowData = null;
        mNextShowId = -1;
    }
}