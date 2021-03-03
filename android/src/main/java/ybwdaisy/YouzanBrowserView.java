package ybwdaisy;

import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.event.AbsAddToCartEvent;
import com.youzan.androidsdk.event.AbsAddUpEvent;
import com.youzan.androidsdk.event.AbsAuthEvent;
import com.youzan.androidsdk.event.AbsBuyNowEvent;
import com.youzan.androidsdk.event.AbsPaymentFinishedEvent;
import com.youzan.androidsdk.event.AbsShareEvent;
import com.youzan.androidsdk.event.AbsStateEvent;
import com.youzan.androidsdk.model.goods.GoodsOfCartModel;
import com.youzan.androidsdk.model.goods.GoodsOfSettleModel;
import com.youzan.androidsdk.model.goods.GoodsShareModel;
import com.youzan.androidsdk.model.trade.TradePayFinishedModel;
import com.youzan.androidsdkx5.YouzanBrowser;
import com.youzan.x5web.YZBaseWebView;

import java.util.HashMap;
import java.util.List;

public class YouzanBrowserView extends YZBaseWebView {
	private YouzanBrowser youzanBrowser;
	private ReadableMap mSource = null;
	private ReadableMap mContainerSize = null;
	private RCTEventEmitter mEventEmitter;

	public enum Events {
		EVENT_LOAD("onLoad"),
		EVENT_LOAD_START("onLoadStart"),
		EVENT_LOAD_END("onLoadEnd"),
		EVENT_LOAD_ERROR("onLoadError"),

		EVENT_LOGIN("onLogin"),
		EVENT_SHARE("onShare"),
		EVENT_READY("onReady"),
		EVENT_ADD_TO_CART("onAddToCart"),
		EVENT_BUY_NOW("onBuyNow"),
		EVENT_ADD_UP("onAddUp"),
		EVENT_PAYMENT_FINISHED("onPaymentFinished");

		private final String mName;

		Events(final String name) {
			mName = name;
		}

		@Override
		public String toString() {
			return mName;
		}
	}

	public YouzanBrowserView(ThemedReactContext themedReactContext) {
		super(themedReactContext);
		mEventEmitter = themedReactContext.getJSModule(RCTEventEmitter.class);
		youzanBrowser = new YouzanBrowser(themedReactContext);
		// WebView 加载回调
		youzanBrowser.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				return super.shouldOverrideUrlLoading(view, request);
			}

			@Override
			public void onLoadResource(WebView view, String s) {
				mEventEmitter.receiveEvent(getId(), Events.EVENT_LOAD.toString(), baseEvent());
			}

			@Override
			public void onPageStarted(WebView view, String s, Bitmap b) {
				mEventEmitter.receiveEvent(getId(), Events.EVENT_LOAD_START.toString(), baseEvent());
			}

			@Override
			public void onPageFinished(WebView view, String s) {
				mEventEmitter.receiveEvent(getId(), Events.EVENT_LOAD_END.toString(), baseEvent());
			}

			@Override
			public void onReceivedError(WebView view, int i, String s1, String s2) {
				mEventEmitter.receiveEvent(getId(), Events.EVENT_LOAD_ERROR.toString(), baseEvent());
			}
		});
		subscribeYouzanEvents();
	}

	public void sync(YouzanToken youzanToken) {
		youzanBrowser.sync(youzanToken);
	}

	public void setSource(ReadableMap source) {
		if (mSource == null) {
			mSource = source;
			if (youzanBrowser != null) {
				youzanBrowser.loadUrl(mSource.getString("uri"));
			}
		}
	}

	public void setContainerSize(ReadableMap containerSize) {
		if (mContainerSize == null) {
			mContainerSize = containerSize;
		}
	}

	public void reload() {
		youzanBrowser.reload();
	}

	public void stopLoading() {
		youzanBrowser.stopLoading();
	}

	public void goForward() {
		if (youzanBrowser.canGoForward()) {
			youzanBrowser.goForward();
		}
	}

	public void goBack() {
		if (youzanBrowser.canGoBack()) {
			youzanBrowser.goBack();
		}
	}

	public void goBackWithStep(Integer step) {
		if (youzanBrowser.canGoBack()) {
			youzanBrowser.goBackOrForward(step);
		}
	}

	private WritableMap baseEvent() {
		HashMap event = new HashMap();
		event.put("url", youzanBrowser.getUrl());
		event.put("canGoBack", youzanBrowser.canGoBack());
		event.put("canGoForward", youzanBrowser.canGoForward());
		return (WritableMap) event;
	}

	private void subscribeYouzanEvents() {
		// 登录事件
		youzanBrowser.subscribe(new AbsAuthEvent() {
			@Override
			public void call(Context context, boolean needLogin) {
				if (needLogin) {
					mEventEmitter.receiveEvent(getId(), Events.EVENT_LOGIN.toString(), baseEvent());
				}
			}
		});
		// 分享事件
		youzanBrowser.subscribe(new AbsShareEvent() {
			@Override
			public void call(Context context, GoodsShareModel goodsShareModel) {
				WritableMap baseEvent = baseEvent();
				HashMap data = new HashMap();
				data.put("title", goodsShareModel.getTitle());
				data.put("desc", goodsShareModel.getDesc());
				data.put("link", goodsShareModel.getLink());
				baseEvent.putMap("data", (ReadableMap) data);
				mEventEmitter.receiveEvent(getId(), Events.EVENT_SHARE.toString(), baseEvent);
			}
		});
		// 页面 ready
		youzanBrowser.subscribe(new AbsStateEvent() {
			@Override
			public void call(Context context) {
				mEventEmitter.receiveEvent(getId(), Events.EVENT_READY.toString(), baseEvent());
			}
		});
		// 加入购物车
		youzanBrowser.subscribe(new AbsAddToCartEvent() {
			@Override
			public void call(Context context, GoodsOfCartModel goodsOfCartModel) {
				WritableMap baseEvent = baseEvent();
				HashMap data = new HashMap();
				data.put("itemId", goodsOfCartModel.getItemId());
				data.put("skuId", goodsOfCartModel.getSkuId());
				data.put("alias", goodsOfCartModel.getAlias());
				data.put("title", goodsOfCartModel.getTitle());
				data.put("num", goodsOfCartModel.getNum());
				data.put("payPrice", goodsOfCartModel.getPayPrice());
				baseEvent.putMap("data", (ReadableMap) data);
				mEventEmitter.receiveEvent(getId(), Events.EVENT_ADD_TO_CART.toString(), baseEvent);
			}
		});
		// 购物车结算
		youzanBrowser.subscribe(new AbsAddUpEvent() {
			@Override
			public void call(Context context, List<GoodsOfSettleModel> list) {
				WritableMap baseEvent = baseEvent();
				baseEvent.putMap("data", (ReadableMap) list);
				mEventEmitter.receiveEvent(getId(), Events.EVENT_ADD_UP.toString(), baseEvent);
			}
		});
		// 商品详情“立即购买”
		youzanBrowser.subscribe(new AbsBuyNowEvent() {
			@Override
			public void call(Context context, GoodsOfCartModel goodsOfCartModel) {
				WritableMap baseEvent = baseEvent();
				HashMap data = new HashMap();
				data.put("itemId", goodsOfCartModel.getItemId());
				data.put("skuId", goodsOfCartModel.getSkuId());
				data.put("alias", goodsOfCartModel.getAlias());
				data.put("title", goodsOfCartModel.getTitle());
				data.put("num", goodsOfCartModel.getNum());
				data.put("payPrice", goodsOfCartModel.getPayPrice());
				baseEvent.putMap("data", (ReadableMap) data);
				mEventEmitter.receiveEvent(getId(), Events.EVENT_BUY_NOW.toString(), baseEvent);
			}
		});
		// 支付成功回调结果页
		youzanBrowser.subscribe(new AbsPaymentFinishedEvent() {
			@Override
			public void call(Context context, TradePayFinishedModel tradePayFinishedModel) {
				WritableMap baseEvent = baseEvent();
				HashMap data = new HashMap();
				data.put("tid", tradePayFinishedModel.getTid());
				data.put("status", tradePayFinishedModel.getStatus());
				data.put("payType", tradePayFinishedModel.getPayType());
				baseEvent.putMap("data", (ReadableMap) data);
				mEventEmitter.receiveEvent(getId(), Events.EVENT_PAYMENT_FINISHED.toString(), baseEvent);
			}
		});

	}
}
