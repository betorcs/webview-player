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

        if (!isInEditMode()) {
            setupSettings(mWebChromeClient);
        }
    }

    void setFullscreenView(FrameLayout fullscreenView, OnFullscreenModeListener onFullscreenModeListener) {
        mWebChromeClient.setFullscreenView(fullscreenView, onFullscreenModeListener);
    }

    void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        mWebChromeClient.setOnProgressChangeListener(onProgressChangeListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        clear();
        loadUrl("about:blank");
        super.onDetachedFromWindow();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupSettings(WebChromeClient webChromeClient) {
        setWebChromeClient(webChromeClient);
        setWebViewClient(new CustomWebViewClient());
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setSupportZoom(false);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAppCacheEnabled(false);
        getSettings().setDomStorageEnabled(false);
    }

    private class CustomWebChromeClient extends WebChromeClient {

        private FrameLayout mFullscreenView;
        private OnProgressChangeListener mOnProgressChangeListener;
        private OnFullscreenModeListener mOnFullscreenModeListener;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (mFullscreenView != null) {
                mFullscreenView.removeAllViews();
                mFullscreenView.addView(view);
                mFullscreenView.setVisibility(View.VISIBLE);
                if (mOnFullscreenModeListener != null) {
                    mOnFullscreenModeListener.onFullscreenEnter();
                }
            }
        }

        @Override
        public void onHideCustomView() {
            if (mFullscreenView != null) {
                mFullscreenView.removeAllViews();
                mFullscreenView.setVisibility(View.GONE);
            }
            if (mOnFullscreenModeListener != null) {
                mOnFullscreenModeListener.onFullscreenExit();
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

        void setFullscreenView(FrameLayout fullscreenView, OnFullscreenModeListener onFullscreenModeListener) {
            mFullscreenView = fullscreenView;
            mOnFullscreenModeListener = onFullscreenModeListener;
        }
    }

    @Override
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        clear();
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    private void clear() {
        clearHistory();
        clearCache(true);
    }

    @Override
    public void loadUrl(String url) {
        clear();
        super.loadUrl(url);
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
