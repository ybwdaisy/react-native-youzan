package ybwdaisy;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.YzLoginCallback;

public class YouzanBrowserModule extends ReactContextBaseJavaModule {
	public final ReactApplicationContext mContext;

	public  YouzanBrowserModule(ReactApplicationContext reactContext) {
		super(reactContext);
		mContext = reactContext;
	}

	@NonNull
	@Override
	public String getName() {
		return "YouzanBrowserManager";
	}

	@ReactMethod
	public void login(ReadableMap info, final Promise promise) {
		String openUserId = info.getString("openUserId");
		if (openUserId == null) {
			promise.reject("-1", "登陆失败, openUserId 为空");
			return;
		}
		String avatar = info.getString("avatar");
		if (avatar == null) avatar = "";
		String extra = info.getString("extra");
		if (extra == null) extra = "";
		String nickName = info.getString("nickName");
		if (nickName == null) nickName = "";
		String gender = info.getString("gender");
		if (gender == null) gender = "";
		YouzanSDK.yzlogin(openUserId, avatar, extra, nickName, gender, new YzLoginCallback() {
			@Override
			public void onSuccess(YouzanToken youzanToken) {
				YouzanSDK.sync(mContext, youzanToken);
				WritableMap res = new WritableNativeMap();
				res.putInt("code", 0);
				res.putString("yzOpenId", youzanToken.getYzOpenId());
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
		WritableMap res = new WritableNativeMap();
		res.putInt("code", 0);
		promise.resolve(res);
	}
}
