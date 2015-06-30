package creek.fm.doublea.kzfr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.Utils;
import creek.fm.doublea.kzfr.models.Image;
import creek.fm.doublea.kzfr.models.Program;

/**
 * Created by Aaron on 6/26/2015.
 */
public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder> {
    private static final String TAG = DayListAdapter.class.getSimpleName();

    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater mInflater;
    private ArrayList<Program> mPrograms = new ArrayList<>();
    private Context mContext;

    public static interface OnItemClickListener {
        public void onItemClick(Program program);
    }

    public DayListAdapter(Context context, OnItemClickListener onItemClickListener) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public DayListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(mInflater.inflate(R.layout.program_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(DayListAdapter.ViewHolder holder, int position) {
        holder.bind(mPrograms.get(position));
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
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
        @InjectView(R.id.program_time)
        TextView mTime;
        @InjectView(R.id.program_title)
        TextView mTitle;
        @InjectView(R.id.program_description)
        TextView mDescription;
        @InjectView(R.id.program_image)
        ImageView mImageView;

        private Program mProgramItem;

        public ViewHolder(View v) {
            super(v);
            Log.d(TAG, "ViewHolder Constructor");
            ButterKnife.inject(this, v);
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mProgramItem);
                    }
                }
            });
        }

        public void bind(Program program) {
            Log.d(TAG, "bind day");
            mProgramItem = program;
            mTime.setText(mProgramItem.getAirtime().getStartF());
            mTitle.setText(mProgramItem.getTitle());
            mDescription.setText(mProgramItem.getShortDescription());
            String imageUrl = null;
            Image imageUrls = mProgramItem.getImage();
            if (imageUrls != null)
                imageUrl = imageUrls.getUrlSm();

            Picasso.with(mContext)
                    .load(imageUrl)
                    .resize(Utils.convertDpToPx(mContext, 128), Utils.convertDpToPx(mContext, 128))
                    .onlyScaleDown()
                    .centerInside()
                    .into(mImageView);
        }
    }
}
