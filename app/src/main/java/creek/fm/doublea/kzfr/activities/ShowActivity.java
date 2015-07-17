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
import creek.fm.doublea.kzfr.adapters.ShowRecyclerAdapter;
import creek.fm.doublea.kzfr.api.ApiClient;
import creek.fm.doublea.kzfr.api.KZFRRetrofitCallback;
import creek.fm.doublea.kzfr.models.Image;
import creek.fm.doublea.kzfr.models.Show;
import creek.fm.doublea.kzfr.utils.PaletteTransformation;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Aaron on 7/6/2015.
 */
public class ShowActivity extends MainActivity implements View.OnClickListener {
    private static final String TAG = ShowActivity.class.getSimpleName();
    public static final String SHOW_ID_KEY = TAG + ".show_id_key";
    public static final String SHOW_DATA_KEY = TAG + ".show_data_key";
    public static final String NEXT_SHOW_ID_KEY = TAG + ".next_show_data_key";

    private int mShowId, mNextShowId;
    private RecyclerView mHostRecyclerView;
    private ShowRecyclerAdapter mShowRecyclerAdapter;
    private ImageView mShowImageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View content = inflater.inflate(R.layout.activity_show, mContentView, true);
        mHostRecyclerView = (RecyclerView) content.findViewById(R.id.host_recycler_view);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) content.findViewById(R.id.collapsing_toolbar);
        mShowImageView = (ImageView) content.findViewById(R.id.show_collapsing_image_view);
        mToolbar = (Toolbar) content.findViewById(R.id.toolbar);
        setupRecyclerView();

        if (savedInstanceState != null) {
            Show savedShow = (Show) savedInstanceState.getParcelable(SHOW_DATA_KEY);
            addDataToAdapter(savedShow);
            mNextShowId = savedInstanceState.getInt(NEXT_SHOW_ID_KEY);
            setNextShowId(mNextShowId);
        }
    }

    private void setNextShowId(int nextShowId) {
        if (mShowRecyclerAdapter != null) {
            mShowRecyclerAdapter.setNextShowId(mNextShowId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromIntent();
        if (mShowId != -1) {
            showProgressBar(true);
            executeShowApiCall(mShowId);
        }
    }

    private void getDataFromIntent() {
        Intent currentIntent = getIntent();
        mShowId = currentIntent.getIntExtra(SHOW_ID_KEY, -1);
        mNextShowId = currentIntent.getIntExtra(NEXT_SHOW_ID_KEY, -1);
        setNextShowId(mNextShowId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mShowRecyclerAdapter != null && !mShowRecyclerAdapter.isEmpty()) {
            outState.putParcelable(SHOW_DATA_KEY, mShowRecyclerAdapter.getShowData());
            outState.putInt(NEXT_SHOW_ID_KEY, mShowRecyclerAdapter.getNextShowId());
        }
    }

    private void executeShowApiCall(int showId) {
        ApiClient.getKZFRApiClient(this).getShow(showId, new KZFRRetrofitCallback<Show>() {

            @Override
            public void success(Show show, Response response) {
                super.success(show, response);
                addDataToAdapter(show);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgressBar(false);
                    }
                });
            }
        });
    }

    private void addDataToAdapter(final Show show) {
        mShowRecyclerAdapter.setShowData(show);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShowRecyclerAdapter.notifyDataSetChanged();
                if (show != null) {
                    setCollapsingToolbarLayoutTitle(show.getTitle());
                    setupShowImage(show.getImage());
                }
                showProgressBar(false);
            }
        });

    }

    /**
     * This method is a solution to a bug in the collapsing toolbar layout that causes the title to
     * not update unless the text size has changed. The method simply changes the text size and
     * changes it back forcing the title to update.
     * http://stackoverflow.com/questions/30682548/collapsingtoolbarlayout-settitle-does-not-update-unless-collapsed/31309381#31309381
     *
     * @param title the new title
     */
    private void setCollapsingToolbarLayoutTitle(String title) {
        mCollapsingToolbarLayout.setTitle(title);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }

    private void setupRecyclerView() {
        mHostRecyclerView.setHasFixedSize(true);
        mHostRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShowRecyclerAdapter = new ShowRecyclerAdapter(this);
        mHostRecyclerView.setAdapter(mShowRecyclerAdapter);
    }

    private void setupShowImage(Image image) {
        if (image != null) {
            loadShowImage(image.getUrlLg());
            mShowImageView.setOnClickListener(this);
        } else {
            loadDefaultImage();
        }
    }

    Callback.EmptyCallback picassoSuccessCallback = new Callback.EmptyCallback() {
        @Override
        public void onSuccess() {
            setCollapsingToolbarScrimColor();
        }
    };

    private void loadShowImage(String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .transform(PaletteTransformation.instance())
                .placeholder(R.drawable.kzfr_logo)
                .into(mShowImageView, picassoSuccessCallback);
    }

    /**
     * This method will load the default KZFR image into the collapsing toolbar layout taking
     * advantage of the paletteTransformation to change the toolbar color when collapsed.
     */
    private void loadDefaultImage() {
        Picasso.with(this)
                .load(R.drawable.kzfr_logo)
                .transform(PaletteTransformation.instance())
                .into(mShowImageView, picassoSuccessCallback);
        mShowImageView.setOnClickListener(this);
    }

    private void setCollapsingToolbarScrimColor() {
        Bitmap bitmap = ((BitmapDrawable) mShowImageView.getDrawable()).getBitmap();
        Palette palette = PaletteTransformation.getPalette(bitmap);
        int darkVibrantColor = palette.getDarkVibrantColor(R.color.dark);
        mCollapsingToolbarLayout.setContentScrimColor(darkVibrantColor);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.show_collapsing_image_view) {
            if (mShowImageView.getScaleType() == ImageView.ScaleType.CENTER_CROP) {
                mShowImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } else {
                mShowImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }
}
