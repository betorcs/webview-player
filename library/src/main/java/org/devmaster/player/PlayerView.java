package org.devmaster.player;


import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import static android.view.Gravity.CENTER;

public class PlayerView extends FrameLayout implements Player.View, PlayerWebView.OnProgressChangeListener {

    private final ProgressBar mProgressBar;
    private final PlayerWebView mWebView;
    private final PlayerPresenter mPresenter = new PlayerPresenter(this);
    private boolean mIsAttachedToWindow = false;

    public PlayerView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mWebView = new PlayerWebView(context);
        mProgressBar = new ProgressBar(context);

        if (isInEditMode()) {
            return;
        }

        mWebView.setOnProgressChangeListener(this);
        requestLayout();
        addView(mWebView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mProgressBar.setVisibility(GONE);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = CENTER;
        addView(mProgressBar, params);
    }

    @Override
    public void setVideoHtml(@NonNull String html) {
        mWebView.loadDataWithBaseURL("https://www.youtube.com", html, null, null, null);
    }

    @Override
    public void setVideoUrl(@NonNull String url) {
        mWebView.loadUrl(url);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mIsAttachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        mPresenter.reset();
        mWebView.destroy();
        removeView(mWebView);
        super.onDetachedFromWindow();
        mIsAttachedToWindow = false;
    }

    public void loadVideo(@NonNull String videoId, @NonNull @VideoSource String source) throws IllegalStateException {

        if (!mIsAttachedToWindow)
            throw new IllegalStateException();

        mPresenter.loadVideo(videoId, source);
    }

    public void setFullscreenView(FrameLayout fullscreenView) {
        mWebView.setFullscreenView(fullscreenView);
    }

    @Override
    public void onProgressChanged(int progress) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(progress < 100 ? VISIBLE : GONE);
        }
    }
}
