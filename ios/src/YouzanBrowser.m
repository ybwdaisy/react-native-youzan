//
//  YouzanBrowser.m
//  react-native-youzan
//
//  Created by ybw-macbook-pro on 2021/3/1.
//

#import "YouzanBrowser.h"
#import <YZBaseSDK/YZBaseSDK.h>

@interface YouzanBrowser() <YZWebViewDelegate, YZWebViewNoticeDelegate>

@property (nonatomic, strong) YZWebView *webView;
@property (nonatomic, copy, nonnull) NSDictionary *source;

@end

@implementation YouzanBrowser

- (instancetype)init {
    self = [super init];
    if (self) {
        self.webView = [[YZWebView alloc]initWithWebViewType:YZWebViewTypeWKWebView];
        self.webView.delegate = self;
        self.webView.noticeDelegate = self;
    }
    return self;
}

- (void)setSource:(NSDictionary *)source {
    if (![_source isEqualToDictionary:source]) {
        _source = [source copy];
        if (_webView != nil) {
            NSURL *url = [NSURL URLWithString:[_source objectForKey:@"uri"]];
            NSURLRequest *urlRequest = [NSURLRequest requestWithURL:url];
            [_webView loadRequest:urlRequest];
        }
    }
}

- (NSArray<NSString *> *)supportedEvents {
    return @[
        @"yzObserveLogin",
        @"yzObserveShare",
        @"yzObserveReady",
        @"yzObserveAddToCart",
        @"yzObserveBuyNow",
        @"yzObserveAddUp",
        @"yzObservePaymentFinished",
    ];
}

#pragma mark Static Methods

- (void)reload {
    [_webView reload];
}

- (void)stopLoading {
    [_webView stopLoading];
}

- (void)goForward {
    if ([_webView canGoForward]) {
        [_webView goForward];
    }
}

- (void)goBack {
    if ([_webView canGoBack]) {
        [_webView goBack];
    }
}

- (void)goBackWithStep:(NSInteger)step {
    if ([_webView canGoBack]) {
        [_webView gobackWithStep:step];
    }
}

- (void)login:(NSDictionary *)loginInfo {
    NSString *openUserId = [loginInfo objectForKey:@"openUserId"];
    // openUserId 是必传参数
    if ([openUserId isEqual:[NSNull null]]) {
        return;
    }
    
    [YZSDK.shared loginWithOpenUserId:openUserId
                               avatar:[loginInfo objectForKey:@"avatar"]
                                extra:[loginInfo objectForKey:@"extra"]
                             nickName:[loginInfo objectForKey:@"nickName"]
                               gender:[loginInfo objectForKey:@"gender"]
                        andCompletion:^(BOOL isSuccess, NSString * _Nullable yzOpenId) {
        if (isSuccess) {
            NSLog(@"登陆成功, yzOpenId: %@", yzOpenId);
            [self.webView reload];
        }
    }];
}

#pragma mark YZWebViewDelegate

- (BOOL)webView:(id<YZWebView>)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(WKNavigationType)navigationType {
    NSLog(@"shouldStartLoadWithRequest");
    return YES;
}
- (void)webViewDidStartLoad:(id<YZWebView>)webView {
    NSLog(@"webViewDidStartLoad");
}
- (void)webViewDidFinishLoad:(id<YZWebView>)webView {
    NSLog(@"webViewDidFinishLoad");
}
- (void)webView:(id<YZWebView>)webView didFailLoadWithError:(NSError *)error {
    NSLog(@"didFailLoadWithError");
}
- (void)webViewWebContentProcessDidTerminate:(id<YZWebView>)webView {
    NSLog(@"webViewWebContentProcessDidTerminate");
}

#pragma mark YZWebViewNoticeDelegate

- (void)webView:(nonnull id<YZWebView>)webView didReceiveNotice:(nonnull YZNotice *)notice {
    switch (notice.type) {
            // 收到登陆请求
        case YZNoticeTypeLogin: {
            NSLog(@"需要登陆 --- %@", notice.response);
//            [self sendEventWithName:@"yzObserveLogin" body:notice.response];
            break;
        }
            // 收到分享的回调数据
        case YZNoticeTypeShare: {
            NSLog(@"收到分享的回调数据 --- %@", notice.response);
//            [self sendEventWithName:@"yzObserveShare" body:notice.response];
            break;
        }
            // Web页面已准备好
            // 此时可以分享，但注意此事件并不作为是否可分享的标志事件
        case YZNoticeTypeReady: {
            NSLog(@"Web页面已准备好 --- %@", notice.response);
//            [self sendEventWithName:@"yzObserveReady" body:notice.response];
            break;
        }
            // 加入购物车的时候调用
        case YZNoticeTypeAddToCart: {
            NSLog(@"购物车 --- %@", notice.response);
//            [self sendEventWithName:@"yzObserveAddToCart" body:notice.response];
            break;
        }
            // 立即购买
        case YZNoticeTypeBuyNow: {
            NSLog(@"立即购买 -- %@", notice.response);
//            [self sendEventWithName:@"yzObserveBuyNow" body:notice.response];
            break;
        }
            // 购物车结算时调用
        case YZNoticeTypeAddUp: {
            NSLog(@"购物车结算 --- %@", notice.response);
//            [self sendEventWithName:@"yzObserveAddUp" body:notice.response];
            break;
        }
            // 支付成功回调结果页
        case YZNoticeTypePaymentFinished: {
            NSLog(@"支付成功回调结果页 --- %@", notice.response);
//            [self sendEventWithName:@"yzObservePaymentFinished" body:notice.response];
            break;
        }
        default:
            break;
    }
}

@end
