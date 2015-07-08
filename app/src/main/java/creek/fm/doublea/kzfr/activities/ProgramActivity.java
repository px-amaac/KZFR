package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.adapters.ProgramRecyclerAdapter;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.models.Image;
import creek.fm.doublea.kzfr.models.Program;
import creek.fm.doublea.kzfr.utils.PaletteTransformation;
import retrofit.client.Response;

/**
 * Created by Aaron on 7/6/2015.
 */
public class ProgramActivity extends MainActivity implements View.OnClickListener {
    private static final String TAG = ProgramActivity.class.getSimpleName();
    public static final String PROGRAM_ID_KEY = TAG + ".program_id_key";
    public static final String PROGRAM_DATA_KEY = TAG + ".program_data_key";
    public static final String NEXT_PROGRAM_ID_KEY = TAG + ".next_program_data_key";

    private int mProgramId;
    private RecyclerView mHostRecyclerView;
    private ProgramRecyclerAdapter mProgramRecyclerAdapter;
    private ImageView mProgramImageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View content = inflater.inflate(R.layout.activity_program, mContentView, true);
        mHostRecyclerView = (RecyclerView) content.findViewById(R.id.host_recycler_view);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) content.findViewById(R.id.collapsing_toolbar);
        mProgramImageView = (ImageView) content.findViewById(R.id.program_collapsing_image_view);
        mToolbar = (Toolbar) content.findViewById(R.id.toolbar);
        setupRecyclerView();

        if (savedInstanceState != null) {
            Program savedProgram = (Program) savedInstanceState.getParcelable(PROGRAM_DATA_KEY);
            int nextProgramId = savedInstanceState.getInt(NEXT_PROGRAM_ID_KEY);
            if (nextProgramId != -1)
                mProgramRecyclerAdapter.setNextProgramId(nextProgramId);
            addDataToAdapter(savedProgram);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mProgramId != -1) {
            showProgressBar(true);
            executeProgramApiCall(mProgramId);
            mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
            mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mProgramId = intent.getIntExtra(PROGRAM_ID_KEY, -1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mProgramRecyclerAdapter != null && !mProgramRecyclerAdapter.isEmpty()) {
            outState.putParcelable(PROGRAM_DATA_KEY, mProgramRecyclerAdapter.getProgramData());
            outState.putInt(NEXT_PROGRAM_ID_KEY, mProgramRecyclerAdapter.getNextProgramId());
        }
    }

    private void executeProgramApiCall(int programId) {
        ApiClient.getKZFRApiClient(this).getProgram(programId, new KZFRRetrofitCallback<Program>() {

            @Override
            public void success(Program program, Response response) {
                super.success(program, response);
                addDataToAdapter(program);
            }
        });
    }

    private void addDataToAdapter(final Program program) {
        mProgramRecyclerAdapter.setProgramData(program);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgramRecyclerAdapter.notifyDataSetChanged();
                if(program != null) {
                    mCollapsingToolbarLayout.setTitle(program.getTitle());
                    mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
                    mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
                    setupProgramImage(program.getImage());
                }
                showProgressBar(false);
            }
        });

    }

    private void setupRecyclerView() {
        mHostRecyclerView.setHasFixedSize(true);
        mHostRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgramRecyclerAdapter = new ProgramRecyclerAdapter(this);
        mHostRecyclerView.setAdapter(mProgramRecyclerAdapter);
    }

    private void setupProgramImage(Image image) {
        Picasso.with(this)
                .load(image.getUrlLg())
                .transform(PaletteTransformation.instance())
                .placeholder(R.drawable.kzfr_logo)
                .into(mProgramImageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) mProgramImageView.getDrawable()).getBitmap();
                        Palette palette = PaletteTransformation.getPalette(bitmap);
                        int darkVibrantColor = palette.getDarkVibrantColor(R.color.dark);
                        mCollapsingToolbarLayout.setContentScrimColor(darkVibrantColor);
                    }
                });
        mProgramImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.program_collapsing_image_view) {
            if (mProgramImageView.getScaleType() == ImageView.ScaleType.CENTER_CROP) {
                mProgramImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } else {
                mProgramImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }
}
