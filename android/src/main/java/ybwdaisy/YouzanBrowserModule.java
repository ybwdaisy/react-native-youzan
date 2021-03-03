package ybwdaisy;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.YzLoginCallback;

import java.util.HashMap;

public class YouzanBrowserModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext mContext;

    public YouzanBrowserModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return "YouzanBrowser";
    }

    @ReactMethod
    public void login(ReadableMap info, final Promise promise) {
        YouzanSDK.yzlogin(info.getString("openUserId"),  info.getString("avatar"),  info.getString("extra"), info.getString("nickName"), info.getString("gender"), new YzLoginCallback() {
            @Override
            public void onSuccess(YouzanToken youzanToken) {
                // TODO: YouzanBrowser.sync(YouzanToken token)
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
