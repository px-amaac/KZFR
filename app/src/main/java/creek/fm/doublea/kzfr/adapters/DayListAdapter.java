package creek.fm.doublea.kzfr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.models.Program;

/**
 * Created by Aaron on 6/26/2015.
 */
public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder> {
    private static final String TAG = DayListAdapter.class.getSimpleName();

    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater mInflater;
    private ArrayList<Program> mPrograms = new ArrayList<>();

    public static interface OnItemClickListener {
        public void onItemClick(Program program);
    }

    public DayListAdapter(Context context, OnItemClickListener onItemClickListener) {
        mInflater = LayoutInflater.from(context);
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
        public TextView mTime;
        public TextView mTitle;
        public TextView mDescription;
        private Program mProgramItem;

        public ViewHolder(View v) {
            super(v);
            Log.d(TAG, "ViewHolder Constructor");
            mTime = (TextView) v.findViewById(R.id.program_time);
            mTitle = (TextView) v.findViewById(R.id.program_title);
            mDescription = (TextView) v.findViewById(R.id.program_description);
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
        }
    }
}
