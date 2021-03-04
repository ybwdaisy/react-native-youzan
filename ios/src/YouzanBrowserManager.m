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
#import <React/RCTUIManager.h>

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
    return [[YouzanBrowser alloc]initWithBridge:self.bridge];
}

- (NSArray<NSString *> *)supportedEvents
{
    return @[
        @"onLoad",
        @"onLoadStart",
        @"onLoadEnd",
        @"onLoadError",
        @"onLogin",
        @"onShare",
        @"onReady",
        @"onAddToCart",
        @"onBuyNow",
        @"onAddUp",
        @"onPaymentFinished"
    ];
}

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}


#pragma mark Export Instance Methods

RCT_EXPORT_METHOD(reload:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,YouzanBrowser *> *viewRegistry) {
            YouzanBrowser *browser = viewRegistry[reactTag];
            if ([browser isKindOfClass:YouzanBrowser.class]) {
                [browser reload];
            }
    }];
}

RCT_EXPORT_METHOD(stopLoading:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,YouzanBrowser *> *viewRegistry) {
            YouzanBrowser *browser = viewRegistry[reactTag];
            if ([browser isKindOfClass:YouzanBrowser.class]) {
                [browser stopLoading];
            }
    }];
}

RCT_EXPORT_METHOD(goBack:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,YouzanBrowser *> *viewRegistry) {
            YouzanBrowser *browser = viewRegistry[reactTag];
            if ([browser isKindOfClass:YouzanBrowser.class]) {
                [browser goBack];
            }
    }];
}

RCT_EXPORT_METHOD(goForward:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,YouzanBrowser *> *viewRegistry) {
            YouzanBrowser *browser = viewRegistry[reactTag];
            if ([browser isKindOfClass:YouzanBrowser.class]) {
                [browser goForward];
            }
    }];
}

RCT_EXPORT_METHOD(goBackWithStep:(nonnull NSNumber *)reactTag step:(NSInteger)step) {
    [self.bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,YouzanBrowser *> *viewRegistry) {
            YouzanBrowser *browser = viewRegistry[reactTag];
            if ([browser isKindOfClass:YouzanBrowser.class]) {
                [browser goBackWithStep:step];
            }
    }];
}


#pragma mark Export Static Methods

RCT_EXPORT_METHOD(login:(nonnull NSDictionary *)loginInfo
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *openUserId = [loginInfo objectForKey:@"openUserId"];
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
