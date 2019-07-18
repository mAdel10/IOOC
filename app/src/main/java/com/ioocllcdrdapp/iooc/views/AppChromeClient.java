package com.ioocllcdrdapp.iooc.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;

import com.ioocllcdrdapp.iooc.R;

public class AppChromeClient extends WebChromeClient {

    private View mCustomView;
    private CustomViewCallback mCustomViewCallback;
    protected FrameLayout mFullscreenContainer;
    private int mOriginalOrientation;
    private int mOriginalSystemUiVisibility;

    private Activity activity;
    private Context context;

    private boolean isFullScreen;

    public AppChromeClient(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        isFullScreen = false;
    }

    public AppChromeClient(Context context, Activity activity, boolean isFullScreen) {
        this.context = context;
        this.activity = activity;
        this.isFullScreen = isFullScreen;
    }

    public Bitmap getDefaultVideoPoster() {
        if (mCustomView == null) {
            return null;
        }
        return BitmapFactory.decodeResource(context.getResources(), 2130837573);
    }

    public void onHideCustomView() {
        ((FrameLayout) activity.getWindow().getDecorView()).removeView(this.mCustomView);
        this.mCustomView = null;
        activity.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
        activity.setRequestedOrientation(this.mOriginalOrientation);
        this.mCustomViewCallback.onCustomViewHidden();
        this.mCustomViewCallback = null;

        isFullScreen = false;
    }

    public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback) {
        if (this.mCustomView != null) {
            onHideCustomView();
            return;
        }
        this.mCustomView = paramView;
        this.mOriginalSystemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        this.mOriginalOrientation = activity.getRequestedOrientation();
        this.mCustomViewCallback = paramCustomViewCallback;
        ((FrameLayout) activity.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
        activity.getWindow().getDecorView().setSystemUiVisibility(3846);
        mCustomView.setBackgroundColor(context.getResources().getColor(R.color.colorAccentDark));

        isFullScreen = true;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }
}