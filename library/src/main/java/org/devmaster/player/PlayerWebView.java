package org.devmaster.player;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

class PlayerWebView extends WebView {

    private final CustomWebChromeClient mWebChromeClient;

    public PlayerWebView(Context context) {
        this(context, null);
    }

    public PlayerWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mWebChromeClient = new CustomWebChromeClient();

        setupSettings(mWebChromeClient);
    }

    void setFullscreenView(FrameLayout fullscreenView) {
        mWebChromeClient.setFullscreenView(fullscreenView);
    }

    void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        mWebChromeClient.setOnProgressChangeListener(onProgressChangeListener);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupSettings(WebChromeClient webChromeClient) {
        setWebChromeClient(webChromeClient);
        setWebViewClient(new CustomWebViewClient());
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setSupportZoom(false);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAppCacheEnabled(true);
        getSettings().setDomStorageEnabled(true);
    }

    private class CustomWebChromeClient extends WebChromeClient {

        private FrameLayout mFullscreenView;
        private OnProgressChangeListener mOnProgressChangeListener;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (mFullscreenView == null) {
                super.onShowCustomView(view, callback);
            } else {
                mFullscreenView.removeAllViews();
                mFullscreenView.addView(view);
                mFullscreenView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onHideCustomView() {
            if (mFullscreenView == null) {
                super.onHideCustomView();
            } else {
                mFullscreenView.removeAllViews();
                mFullscreenView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (mOnProgressChangeListener != null) {
                mOnProgressChangeListener.onProgressChanged(newProgress);
            }
        }

        void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
            mOnProgressChangeListener = onProgressChangeListener;
        }

        void setFullscreenView(FrameLayout fullscreenView) {
            mFullscreenView = fullscreenView;
        }
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    interface OnProgressChangeListener {
        void onProgressChanged(int progress);
    }

}
