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
RCT_EXPORT_VIEW_PROPERTY(width, NSNumber);
RCT_EXPORT_VIEW_PROPERTY(height, NSNumber);
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

RCT_EXPORT_METHOD(goBackHome:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,YouzanBrowser *> *viewRegistry) {
            YouzanBrowser *browser = viewRegistry[reactTag];
            if ([browser isKindOfClass:YouzanBrowser.class]) {
                [browser goBackHome];
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
    if (openUserId == nil) {
        reject(@"-1", @"登陆失败, openUserId 为空", nil);
        return;
    }
    NSString *avatar = [loginInfo objectForKey:@"avatar"];
    NSString *extra = [loginInfo objectForKey:@"extra"];
    NSString *nickName = [loginInfo objectForKey:@"nickName"];
    NSString *gender = [loginInfo objectForKey:@"gender"];
    [YZSDK.shared loginWithOpenUserId:openUserId
                               avatar:avatar
                                extra:extra
                             nickName:nickName
                               gender:gender.intValue
                        andCompletion:^(BOOL isSuccess, NSString * _Nullable yzOpenId) {
        if (isSuccess) {
            resolve(@{
                @"code": @0,
                @"data": @{
                    @"yzOpenId": yzOpenId
                }
            });
        } else {
            reject(@"-1", @"YZSDK 返回登陆失败，请联系有赞", nil);
        }
    }];
    
}

RCT_EXPORT_METHOD(logout:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    dispatch_sync(dispatch_get_main_queue(), ^{
        [YZSDK.shared logout];
        resolve(@{
            @"code": @0
        });
    });
}

@end
