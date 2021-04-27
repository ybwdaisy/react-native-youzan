package com.ybwdaisy;

import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.youzan.androidsdkx5.YouzanBrowser;

public class YouzanWebChromeClient extends WebChromeClient implements LifecycleEventListener {
	protected static final FrameLayout.LayoutParams FULLSCREEN_LAYOUT_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	protected static final int FULLSCREEN_SYSTEM_UI_VISIBILITY = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
			View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
			View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
			View.SYSTEM_UI_FLAG_FULLSCREEN |
			View.SYSTEM_UI_FLAG_IMMERSIVE |
			View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

	protected ReactContext mReactContext;
	protected View mWebView;

	protected View mVideoView;
	protected IX5WebChromeClient.CustomViewCallback mCustomViewCallback;

	public YouzanWebChromeClient(ReactContext reactContext, YouzanBrowser webView) {
		this.mReactContext = reactContext;
		this.mWebView = webView;
	}

	@Override
	public void onHostResume() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mVideoView != null && mVideoView.getSystemUiVisibility() != FULLSCREEN_SYSTEM_UI_VISIBILITY) {
			mVideoView.setSystemUiVisibility(FULLSCREEN_SYSTEM_UI_VISIBILITY);
		}
	}

	@Override
	public void onHostPause() { }

	@Override
	public void onHostDestroy() { }

	protected ViewGroup getRootView() {
		return (ViewGroup) mReactContext.getCurrentActivity().findViewById(android.R.id.content);
	}
}
