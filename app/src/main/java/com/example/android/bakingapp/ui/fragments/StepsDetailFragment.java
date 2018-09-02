package com.example.android.bakingapp.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsDetailFragment extends Fragment {

    private static final String STEP_KEY = "step_key";
    private static final String SIMPLE_EXO_PLAYER_PLAY_STATE = "player_play_state";
    private static final String SIMPLE_EXO_PLAYER_POSITION_KEY = "exo_player_position_key";
    private static final String IS_TABLET_KEY = "two_pane";
    @BindView(R.id.step_detail_long_description)
    TextView mStepDetailDescription;
    @BindView(R.id.step_detail_short_description)
    TextView mStepShortDescription;
    @BindView(R.id.step_media_player)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.no_video_message)
    TextView noVideoMessage;
    @BindView(R.id.thumbnail_url)
    ImageView mThumbnailUrl;
    private Step mStep;
    private SimpleExoPlayer mExoPlayer;
    private Context mContext;
    private long exoPlayerPosition;
    private boolean mTwoPane;
    // RESUBMIT: store the Player's play state
    private boolean mExoPlayerState;

    // Mandatory Empty Constructor
    public StepsDetailFragment() {
    }

    // This method checks if the Path from the url is image file
    // found an example here ---> https://stackoverflow.com/questions/17618118/check-if-a-file-is-an-image-or-a-video
    private static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    // Inflates the RecyclerView of all Steps
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);
        mContext = getContext();
        exoPlayerPosition = 0;
        mExoPlayerState = true;

        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(STEP_KEY);
            // RESUBMIT: store the Player's play state
            mExoPlayerState = savedInstanceState.getBoolean(SIMPLE_EXO_PLAYER_PLAY_STATE);
            exoPlayerPosition = savedInstanceState.getLong(SIMPLE_EXO_PLAYER_POSITION_KEY);
            mTwoPane = savedInstanceState.getBoolean(IS_TABLET_KEY);
        }

        // Check if is portrait or landscape
        setPortraitOrLandscape();

        if (!mStep.getVideoURL().equals("") && mStep.getVideoURL() != null) {
            if (isConnected()) {// if user has internet show the media player
                mThumbnailUrl.setVisibility(View.GONE);
                noVideoMessage.setVisibility(View.GONE);
                initializePlayer(Uri.parse(mStep.getVideoURL()));
            } else { //hide the media player & inform the user
                mPlayerView.setVisibility(View.GONE);
                noVideoMessage.setText(R.string.no_internet);
            }
        } else {//if the step has no video
            // Display the Thumbnail Image if it has one
            if (!mStep.getThumbnailURL().equals("") && isImageFile(mStep.getThumbnailURL())) {
                mThumbnailUrl.setVisibility(View.VISIBLE);
                // Load the Image
                Picasso.with(mContext)
                        .load(mStep.getThumbnailURL())
                        .placeholder(R.drawable.ic_ingredients_cc)
                        .into(mThumbnailUrl);
            }
            // Display no video Message and hide the media player
            noVideoMessage.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
        }

        mStepShortDescription.setText(mStep.getShortDescription());
        mStepDetailDescription.setText(mStep.getDescription());

        return rootView;
    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(mContext, "BakingApp");
            Activity getActivity = getActivity();
            MediaSource mediaSource = null;
            if (getActivity != null)
                mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getActivity, userAgent), new DefaultExtractorsFactory(), null, null);

            if (exoPlayerPosition != 0) {
                mExoPlayer.seekTo(exoPlayerPosition);
            }

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(mExoPlayerState);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (!mStep.getVideoURL().equals("") && mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    // Save the Exo Player Position
    // and release Player when paused
    // followed an example from here --> https://stackoverflow.com/questions/45481775/exoplayer-restore-state-when-resumed
    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            exoPlayerPosition = mExoPlayer.getCurrentPosition();
            // RESUBMIT: store the Player's play state
            mExoPlayerState = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    // Initialize Player when Resumed
    @Override
    public void onResume() {
        super.onResume();
        if (!mStep.getVideoURL().equals("") && mStep.getVideoURL() != null) {
            initializePlayer(Uri.parse(mStep.getVideoURL()));
        }
    }

    /**
     * Release the player when the Fragment is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mStep != null)
            releasePlayer();
    }

    // This method checks if it is Portrait or Landscape
    // and sets FullScreen or Not
    // followed an example here --> https://stackoverflow.com/questions/46713761/how-to-play-video-full-screen-in-landscape-using-exoplayer
    public void setPortraitOrLandscape() {
        // Portrait Mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width = FrameLayout.LayoutParams.MATCH_PARENT;
            params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
            mPlayerView.setLayoutParams(params);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !mStep.getVideoURL().equals("") && !mTwoPane) {// landscape
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width = FrameLayout.LayoutParams.MATCH_PARENT;
            params.height = FrameLayout.LayoutParams.MATCH_PARENT;
            mPlayerView.setLayoutParams(params);
            if (getActivity() != null)
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    // setter method for step
    public void setStep(Step step) {
        this.mStep = step;
    }

    // setter method for Boolean mTwoPane (is Tablet)
    public void setTwoPane(Boolean twoPane) {
        this.mTwoPane = twoPane;
    }

    // Save the current state of this fragment (Steps Detail)
    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable(STEP_KEY, mStep);
        // RESUBMIT: store the Player's play state
        currentState.putBoolean(SIMPLE_EXO_PLAYER_PLAY_STATE, mExoPlayerState);
        currentState.putLong(SIMPLE_EXO_PLAYER_POSITION_KEY, exoPlayerPosition);
        currentState.putBoolean(IS_TABLET_KEY, mTwoPane);
    }

    // This method checks if the user has Internet connection
    private boolean isConnected() {
        if (getActivity() == null) {
            return false;
        }
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        // boolean to check if there is a network connection
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}