//
//  YouzanBrowserManager.m
//  react-native-youzan
//
//  Created by ybw-macbook-pro on 2021/3/1.
//

#import "YouzanBrowserManager.h"
#import <YZBaseSDK/YZBaseSDK.h>
#import <YZBaseSDK/YZWebView.h>
#import "YouzanBrowser.h"

@implementation YouzanBrowserManager

RCT_EXPORT_MODULE();

RCT_EXPORT_VIEW_PROPERTY(source, NSDictionary);
RCT_EXPORT_VIEW_PROPERTY(containerSize, NSDictionary);
RCT_EXPORT_VIEW_PROPERTY(onLoad, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onLoadStart, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onLoadEnd, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onLoadError, RCTDirectEventBlock);

RCT_EXPORT_VIEW_PROPERTY(onLogin, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onShare, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onReady, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onAddToCart, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onBuyNow, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onAddUp, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onPaymentFinished, RCTDirectEventBlock);

- (UIView *)view {
    return [[YouzanBrowser alloc]init];
}

#pragma mark 导出静态方法
// 登录
RCT_EXPORT_METHOD(login:(nonnull NSDictionary *)loginInfo
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *openUserId = [loginInfo objectForKey:@"openUserId"];
    // openUserId 是必传参数
    if ([openUserId isEqual:[NSNull null]]) {
        reject(@"-1", @"登陆失败, openUserId 为空", nil);
        return;
    }
    NSString *gender = [loginInfo objectForKey:@"gender"];
    [YZSDK.shared loginWithOpenUserId:openUserId
                               avatar:[loginInfo objectForKey:@"avatar"]
                                extra:[loginInfo objectForKey:@"extra"]
                             nickName:[loginInfo objectForKey:@"nickName"]
                               gender:gender.intValue
                        andCompletion:^(BOOL isSuccess, NSString * _Nullable yzOpenId) {
        if (isSuccess) {
            NSLog(@"登陆成功, yzOpenId: %@", yzOpenId);
            resolve(@{
                @"status": @0,
                @"yzOpenId": yzOpenId
            });
        }
    }];
}

// 退出登录
RCT_EXPORT_METHOD(logout:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    [YZSDK.shared logoutWithCompletion:^{
        NSLog(@"退出登录成功");
        resolve(@{
            @"status": @0
        });
    }];
}

@end
