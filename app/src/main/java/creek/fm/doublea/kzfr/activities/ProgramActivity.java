package creek.fm.doublea.kzfr.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import creek.fm.doublea.kzfr.R;
import creek.fm.doublea.kzfr.models.Program;
import creek.fm.doublea.kzfr.utils.PaletteTransformation;

/**
 * Created by Aaron on 7/2/2015.
 */
public class ProgramActivity extends MainActivity implements View.OnClickListener {
    private static final String TAG = ProgramActivity.class.getSimpleName();
    public static final String PROGRAM_DATA_KEY = TAG + ".program_data_key";

    Program mProgram;
    private TextView mProgramTitle;
    private TextView mProgramAirtimes;
    private TextView mProgramDescription;
    private ImageView mProgramImageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View content = inflater.inflate(R.layout.activity_program, mContentView, true);
        mProgramTitle = (TextView) content.findViewById(R.id.program_activity_title);
        mProgramDescription = (TextView) content.findViewById(R.id.program_activity_description);
        mProgramImageView = (ImageView) content.findViewById(R.id.program_collapsing_image_view);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) content.findViewById(R.id.collapsing_toolbar);

        mProgramImageView.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromIntent(getIntent());
    }

    private void getDataFromIntent(Intent intent) {
        mProgram = intent.getParcelableExtra(PROGRAM_DATA_KEY);
        if(mProgram != null) {
            mCollapsingToolbarLayout.setTitle(mProgram.getTitle());
            mProgramDescription.setText(mProgram.getShortDescription());
            if(mProgram.getImage() != null) {
                setupProgramImage();
            }
        }
    }

    private void setupProgramImage() {
        Picasso.with(this)
                .load(mProgram.getImage().getUrlLg())
                .transform(PaletteTransformation.instance())
                .into(mProgramImageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) mProgramImageView.getDrawable()).getBitmap();
                        Palette palette = PaletteTransformation.getPalette(bitmap);
                        int darkVibrantColor = palette.getMutedColor(R.color.dark);
                        mCollapsingToolbarLayout.setContentScrimColor(darkVibrantColor);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.program_collapsing_image_view) {
            if(mProgramImageView.getScaleType() == ImageView.ScaleType.CENTER_CROP){
                mProgramImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } else {
                mProgramImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }
}
