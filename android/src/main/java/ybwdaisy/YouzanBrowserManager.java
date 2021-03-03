package ybwdaisy;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.YzLoginCallback;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class YouzanBrowserManager extends SimpleViewManager<YouzanBrowserView> {

	public static final String REACT_CLASS = "YouzanBrowser";
	private YouzanBrowserView youzanBrowserView;
	ReactApplicationContext mContext;

	public YouzanBrowserManager(ReactApplicationContext reactContext) {
		mContext = reactContext;
	}

	@NonNull
	@Override
	public String getName() {
		return REACT_CLASS;
	}

	@NonNull
	@Override
	protected YouzanBrowserView createViewInstance(ThemedReactContext themedReactContext) {
		youzanBrowserView = new YouzanBrowserView(themedReactContext);
		return youzanBrowserView;
	}

	@Override
	@Nullable
	public Map getExportedCustomDirectEventTypeConstants() {
		MapBuilder.Builder builder = MapBuilder.builder();
		for (YouzanBrowserView.Events event : YouzanBrowserView.Events.values()) {
			builder.put(event.toString(), MapBuilder.of("registrationName", event.toString()));
		}
		return builder.build();
	}

	@ReactProp(name = "source")
	public void setSource(final YouzanBrowserView youzanBrowserView, ReadableMap source) {
		youzanBrowserView.setSource(source);
	}

	@ReactProp(name = "containerSize")
	public void setContainerSize(final YouzanBrowserView youzanBrowserView, ReadableMap containerSize) {
		youzanBrowserView.setContainerSize(containerSize);
	}

	@ReactMethod
	public void login(ReadableMap info, final Promise promise) {
		YouzanSDK.yzlogin(info.getString("openUserId"),  info.getString("avatar"),  info.getString("extra"), info.getString("nickName"), info.getString("gender"), new YzLoginCallback() {
			@Override
			public void onSuccess(YouzanToken youzanToken) {
				youzanBrowserView.sync(youzanToken);
				HashMap res = new HashMap();
				res.put("status", 0);
				res.put("yzOpenId", youzanToken.getYzOpenId());
				promise.resolve(res);
			}
			@Override
			public void onFail(String s) {
				promise.reject("-1", "登录失败");
			}
		});
	}

	@ReactMethod
	public void logout(final Promise promise) {
		YouzanSDK.userLogout(mContext);
		HashMap res = new HashMap();
		res.put("status", 0);
		promise.resolve(res);
	}

}
