package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.models.Image;
import creek.fm.doublea.kzfr.models.Program;
import creek.fm.doublea.kzfr.utils.PaletteTransformation;
import retrofit.client.Response;

/**
 * Created by Aaron on 7/6/2015.
 */
public class ProgramActivity extends MainActivity implements View.OnClickListener{
    private static final String TAG = ProgramActivity.class.getSimpleName();
    public static final String PROGRAM_ID_KEY = TAG + ".program_id_key";
    public static final String PROGRAM_DATA_KEY = TAG + ".program_data_key";

    private int mProgramId;
    private RecyclerView mHostRecyclerView;
    private RecyclerView.Adapter mProgramRecyclerAdapter;
    private ImageView mProgramImageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View content = inflater.inflate(R.layout.activity_program, mContentView, true);
        mHostRecyclerView = (RecyclerView) content.findViewById(R.id.host_recycler_view);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) content.findViewById(R.id.collapsing_toolbar);
        mProgramImageView = (ImageView) content.findViewById(R.id.program_collapsing_image_view);
        setupRecyclerView();

        mProgramId = getIntent().getIntExtra(PROGRAM_ID_KEY, -1);
        if(savedInstanceState != null) {
            Program savedProgram = (Program) savedInstanceState.getParcelable(PROGRAM_DATA_KEY);
            addDataToAdapter(savedProgram);
        } else {
            if(mProgramId != -1) {
                showProgressBar(true);
                executeProgramApiCall(mProgramId);
            }
        }
    }

    private void executeProgramApiCall(int programId) {
        ApiClient.getKZFRApiClient(this).getProgram(programId, new KZFRRetrofitCallback<Program>() {

            @Override
            public void success(Program program, Response response) {
                super.success(program, response);
                addDataToAdapter(program);
                showProgressBar(false);
            }
        });
    }

    private void addDataToAdapter(Program program) {

        setupProgramImage(program.getImage());
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
