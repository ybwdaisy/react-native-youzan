package com.ybwdaisy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youzan.androidsdk.event.AbsAddToCartEvent;
import com.youzan.androidsdk.event.AbsAddUpEvent;
import com.youzan.androidsdk.event.AbsAuthEvent;
import com.youzan.androidsdk.event.AbsAuthorizationErrorEvent;
import com.youzan.androidsdk.event.AbsAuthorizationSuccessEvent;
import com.youzan.androidsdk.event.AbsBuyNowEvent;
import com.youzan.androidsdk.event.AbsChooserEvent;
import com.youzan.androidsdk.event.AbsPaymentFinishedEvent;
import com.youzan.androidsdk.event.AbsShareEvent;
import com.youzan.androidsdk.event.AbsStateEvent;
import com.youzan.androidsdk.model.goods.GoodsOfCartModel;
import com.youzan.androidsdk.model.goods.GoodsOfSettleModel;
import com.youzan.androidsdk.model.goods.GoodsShareModel;
import com.youzan.androidsdk.model.trade.TradePayFinishedModel;
import com.youzan.androidsdkx5.YouzanBrowser;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class YouzanBrowserManager extends SimpleViewManager<YouzanBrowser> {

	public static final String REACT_CLASS = "YouzanBrowser";

	public static final int COMMAND_RELOAD = 1;
	public static final int COMMAND_STOP_LOADING = 2;
	public static final int COMMAND_GO_FORWARD = 3;
	public static final int COMMAND_GO_BACK = 4;
	public static final int COMMAND_GO_BACK_HOME = 5;
	public static final int COMMAND_GO_BACK_WITH_STEP = 6;
	public static final int COMMAND_CHOOSER = 7;
	public static final int COMMAND_LOAD_URL = 8;

	private YouzanBrowser youzanBrowser;
	private ReadableMap mSource = null;
	private RCTEventEmitter mEventEmitter;
	private ReactContext mContext;
	private YouzanWebChromeClient mWebChromeClient = null;

	@Override
	public void receiveCommand(@NonNull YouzanBrowser youzanBrowser, int commandId, @Nullable ReadableArray args) {
		switch (commandId) {
			case COMMAND_RELOAD:
				reload();
				break;
			case COMMAND_STOP_LOADING:
				stopLoading();
				break;
			case COMMAND_GO_FORWARD:
				goForward();
				break;
			case COMMAND_GO_BACK:
				goBack();
				break;
			case COMMAND_GO_BACK_HOME:
				goBackHome();
				break;
			case COMMAND_GO_BACK_WITH_STEP:
				goBackWithStep(args.getInt(0));
				break;
			case COMMAND_CHOOSER:
				receiveFile(args.getInt(0), args.getString(1));
				break;
			case COMMAND_LOAD_URL:
				loadUrl(args.getString(0));
			default:
				break;
		}

	}

	@Override
	public @Nullable Map<String, Integer> getCommandsMap() {
		return MapBuilder.<String, Integer>builder()
				.put("reload", COMMAND_RELOAD)
				.put("stopLoading", COMMAND_STOP_LOADING)
				.put("goForward", COMMAND_GO_FORWARD)
				.put("goBack", COMMAND_GO_BACK)
				.put("goBackHome", COMMAND_GO_BACK_HOME)
				.put("goBackWithStep", COMMAND_GO_BACK_WITH_STEP)
				.put("receiveFile", COMMAND_CHOOSER)
				.put("loadUrl", COMMAND_LOAD_URL)
				.build();
	}

	public YouzanBrowserManager(ReactContext reactContext) {
		mContext = reactContext;
	}

	public enum Events {
		EVENT_LOAD("onLoad"),
		EVENT_LOAD_START("onLoadStart"),
		EVENT_LOAD_END("onLoadEnd"),
		EVENT_LOAD_ERROR("onLoadError"),

		EVENT_LOGIN("onLogin"),
		EVENT_SHARE("onShare"),
		EVENT_READY("onReady"),
		EVENT_CHOOSER("onChooser"),
		EVENT_ADD_TO_CART("onAddToCart"),
		EVENT_BUY_NOW("onBuyNow"),
		EVENT_ADD_UP("onAddUp"),
		EVENT_PAYMENT_FINISHED("onPaymentFinished"),

		EVENT_AUTHORIZATION_SUCCEED("onAuthorizationSucceed"),
		EVENT_AUTHORIZATION_FAILED("onAuthorizationFailed");

		private final String mName;

		Events(final String name) {
			mName = name;
		}

		@Override
		public String toString() {
			return mName;
		}
	}

	@NonNull
	@Override
	public String getName() {
		return REACT_CLASS;
	}

	@NonNull
	@Override
	protected YouzanBrowser createViewInstance(ThemedReactContext themedReactContext) {
		mEventEmitter = themedReactContext.getJSModule(RCTEventEmitter.class);
		youzanBrowser = new YouzanBrowser(themedReactContext.getCurrentActivity());
		// Fix 视频播放白屏
		setupWebChromeClient(themedReactContext, youzanBrowser);
		// Fixes broken full-screen modals/galleries due to body height being 0.
		youzanBrowser.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		youzanBrowser.needLoading(false);
		// WebView 加载回调
		youzanBrowser.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				return super.shouldOverrideUrlLoading(view, request);
			}

			@Override
			public void onLoadResource(WebView view, String s) {
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_LOAD.toString(), baseEvent());
			}

			@Override
			public void onPageStarted(WebView view, String s, Bitmap b) {
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_LOAD_START.toString(), baseEvent());
			}

			@Override
			public void onPageFinished(WebView view, String s) {
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_LOAD_END.toString(), baseEvent());
			}

			@Override
			public void onReceivedError(WebView view, int i, String s1, String s2) {
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_LOAD_ERROR.toString(), baseEvent());
			}
		});
		// 监听事件
		subscribeYouzanEvents();
		return youzanBrowser;
	}

	@Override
	@Nullable
	public Map getExportedCustomDirectEventTypeConstants() {
		MapBuilder.Builder builder = MapBuilder.builder();
		for (Events event: Events.values()) {
			builder.put(event.toString(), MapBuilder.of("registrationName", event.toString()));
		}
		return builder.build();
	}

	private void reload() {
		youzanBrowser.reload();
	}

	private void stopLoading() {
		youzanBrowser.stopLoading();
	}

	private void goForward() {
		if (youzanBrowser.canGoForward()) {
			youzanBrowser.goForward();
		}
	}

	private void goBack() {
		if (youzanBrowser.canGoBack()) {
			youzanBrowser.goBack();
		}
	}

	private void goBackHome() {
		int count = -1;
		while(youzanBrowser.canGoBackOrForward(count)){
			count--;
		}
		youzanBrowser.goBackOrForward(count + 1);
	}

	private void goBackWithStep(int step) {
		if(youzanBrowser.canGoBackOrForward(step)){
			youzanBrowser.goBackOrForward(step);
		}
	}

	private void receiveFile(int requestCode, String uri) {
		Intent intent = new Intent();
		String imgUri = uri != null ? uri : "";
		intent.setData(Uri.parse(imgUri));
		youzanBrowser.receiveFile(requestCode, intent);
	}

	private void loadUrl(String url) {
		if (url != null) {
			youzanBrowser.loadUrl(url);
		}
	}

	private WritableMap baseEvent() {
		WritableMap event = new WritableNativeMap();
		event.putString("url", youzanBrowser.getUrl());
		event.putString("title", youzanBrowser.getTitle());
		event.putBoolean("canGoBack", youzanBrowser.canGoBack());
		event.putBoolean("canGoForward", youzanBrowser.canGoForward());
		return event;
	}

	private void subscribeYouzanEvents() {
		// 登录事件
		youzanBrowser.subscribe(new AbsAuthEvent() {
			@Override
			public void call(Context context, boolean needLogin) {
				if (needLogin) {
					mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_LOGIN.toString(), baseEvent());
				}
			}
		});
		// 分享事件
		youzanBrowser.subscribe(new AbsShareEvent() {
			@Override
			public void call(Context context, GoodsShareModel goodsShareModel) {
				WritableMap baseEvent = baseEvent();
				WritableMap data = new WritableNativeMap();
				data.putString("title", goodsShareModel.getTitle());
				data.putString("desc", goodsShareModel.getDesc());
				data.putString("link", goodsShareModel.getLink());
				data.putString("imgUrl", goodsShareModel.getImgUrl());
				baseEvent.putMap("data", data);
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_SHARE.toString(), baseEvent);
			}
		});
		// 页面 ready
		youzanBrowser.subscribe(new AbsStateEvent() {
			@Override
			public void call(Context context) {
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_READY.toString(), baseEvent());
			}
		});
		// 文件选择事件
		youzanBrowser.subscribe(new AbsChooserEvent() {
			@Override
			public void call(Context context, Intent intent, int requestCode) throws ActivityNotFoundException {
				WritableMap baseEvent = baseEvent();
				baseEvent.putInt("requestCode", requestCode);
				baseEvent.putString("acceptType", intent.getType());
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_CHOOSER.toString(), baseEvent);
			}
		});
		// 加入购物车
		youzanBrowser.subscribe(new AbsAddToCartEvent() {
			@Override
			public void call(Context context, GoodsOfCartModel goodsOfCartModel) {
				WritableMap baseEvent = baseEvent();
				WritableMap data = new WritableNativeMap();
				data.putString("itemId", String.valueOf(goodsOfCartModel.getItemId()));
				data.putString("skuId", String.valueOf(goodsOfCartModel.getSkuId()));
				data.putString("alias", goodsOfCartModel.getAlias());
				data.putString("title", goodsOfCartModel.getTitle());
				data.putInt("num", goodsOfCartModel.getNum());
				data.putInt("payPrice", goodsOfCartModel.getPayPrice());
				baseEvent.putMap("data", data);
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_ADD_TO_CART.toString(), baseEvent);
			}
		});
		// 商品详情“立即购买”
		youzanBrowser.subscribe(new AbsBuyNowEvent() {
			@Override
			public void call(Context context, GoodsOfCartModel goodsOfCartModel) {
				WritableMap baseEvent = baseEvent();
				WritableMap data = new WritableNativeMap();
				data.putString("itemId", String.valueOf(goodsOfCartModel.getItemId()));
				data.putString("skuId", String.valueOf(goodsOfCartModel.getSkuId()));
				data.putString("alias", goodsOfCartModel.getAlias());
				data.putString("title", goodsOfCartModel.getTitle());
				data.putInt("num", goodsOfCartModel.getNum());
				data.putInt("payPrice", goodsOfCartModel.getPayPrice());
				baseEvent.putMap("data", data);
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_BUY_NOW.toString(), baseEvent);
			}
		});
		// 购物车结算
		youzanBrowser.subscribe(new AbsAddUpEvent() {
			@Override
			public void call(Context context, List<GoodsOfSettleModel> list) {
				WritableMap baseEvent = baseEvent();
				WritableArray data = new WritableNativeArray();
				for (GoodsOfSettleModel goodsOfSettleModel : list) {
					WritableMap map = new WritableNativeMap();
					map.putString("itemId", String.valueOf(goodsOfSettleModel.getItemId()));
					map.putString("skuId", String.valueOf(goodsOfSettleModel.getSkuId()));
					map.putString("alias", goodsOfSettleModel.getAlias());
					map.putString("title", goodsOfSettleModel.getTitle());
					map.putInt("num", goodsOfSettleModel.getNum());
					map.putInt("payPrice", goodsOfSettleModel.getPayPrice());
					map.putBoolean("selected", goodsOfSettleModel.isSelected());
					data.pushMap(map);
				}
				baseEvent.putArray("data", data);
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_ADD_UP.toString(), baseEvent);
			}
		});
		// 支付成功回调结果页
		youzanBrowser.subscribe(new AbsPaymentFinishedEvent() {
			@Override
			public void call(Context context, TradePayFinishedModel tradePayFinishedModel) {
				WritableMap baseEvent = baseEvent();
				WritableMap data = new WritableNativeMap();
				data.putString("tid", tradePayFinishedModel.getTid());
				data.putInt("status", tradePayFinishedModel.getStatus());
				data.putInt("payType", tradePayFinishedModel.getPayType());
				baseEvent.putMap("data", data);
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_PAYMENT_FINISHED.toString(), baseEvent);
			}
		});
		// 手机号授权成功回调
		youzanBrowser.subscribe(new AbsAuthorizationSuccessEvent() {
			@Override
			public void call(Context context) {
				WritableMap baseEvent = baseEvent();
				WritableMap data = new WritableNativeMap();
				data.putInt("code", 0);
				data.putString("message", "success");
				baseEvent.putMap("data", data);
				mEventEmitter.receiveEvent(youzanBrowser.getId(), Events.EVENT_AUTHORIZATION_SUCCEED.toString(), baseEvent);
			}
		});
		// 手机号授权失败回调
		youzanBrowser.subscribe(new AbsAuthorizationErrorEvent() {
			@Override
			public void call(Context context, int code, String message) {
				WritableMap baseEvent = baseEvent();
				WritableMap data = new WritableNativeMap();
				data.putInt("code", code);
				data.putString("message", message);
				baseEvent.putMap("data", data);
				// code == 0 表示已授权成功
				Events eventName = code == 0 ? Events.EVENT_AUTHORIZATION_SUCCEED : Events.EVENT_AUTHORIZATION_FAILED;
				mEventEmitter.receiveEvent(youzanBrowser.getId(), eventName.toString(), baseEvent);
			}
		});

	}

	@ReactProp(name = "source")
	public void setSource(final YouzanBrowser youzanBrowser, ReadableMap source) {
		mSource = source;
		String uri = mSource.getString("uri");
		if (youzanBrowser != null && uri != null) {
			youzanBrowser.loadUrl(uri);
		}
	}


	protected void setupWebChromeClient(ReactContext reactContext, YouzanBrowser webView) {
		final int initialRequestedOrientation = reactContext.getCurrentActivity().getRequestedOrientation();
		mWebChromeClient = new YouzanWebChromeClient(reactContext, webView) {
			@Override
			public Bitmap getDefaultVideoPoster() {
				return Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
			}

			@Override
			public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
				super.onShowCustomView(view, callback);
				if (mVideoView != null) {
					callback.onCustomViewHidden();
					return;
				}

				mVideoView = view;
				mCustomViewCallback = callback;

				mReactContext.getCurrentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
					mVideoView.setSystemUiVisibility(FULLSCREEN_SYSTEM_UI_VISIBILITY);
					mReactContext.getCurrentActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
				}

				mVideoView.setBackgroundColor(Color.BLACK);
				getRootView().addView(mVideoView, FULLSCREEN_LAYOUT_PARAMS);
				mWebView.setVisibility(View.GONE);

				mReactContext.addLifecycleEventListener(this);

				mCustomViewCallback.onCustomViewHidden();
			}

			@Override
			public void onHideCustomView() {
				if (mVideoView == null) {
					return;
				}

				mVideoView.setVisibility(View.GONE);
				getRootView().removeView(mVideoView);
				mCustomViewCallback.onCustomViewHidden();

				mVideoView = null;
				mCustomViewCallback = null;

				mWebView.setVisibility(View.VISIBLE);

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
					mReactContext.getCurrentActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
				}
				mReactContext.getCurrentActivity().setRequestedOrientation(initialRequestedOrientation);

				mReactContext.removeLifecycleEventListener(this);
			}
		};
		webView.setWebChromeClient(mWebChromeClient);
	}


}
