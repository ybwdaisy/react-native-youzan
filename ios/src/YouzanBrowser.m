//
//  YouzanBrowser.m
//  react-native-youzan
//
//  Created by ybw-macbook-pro on 2021/3/1.
//

#import "YouzanBrowser.h"
#import <YZBaseSDK/YZBaseSDK.h>

@interface YouzanBrowser() <YZWebViewDelegate, YZWebViewNoticeDelegate>

@property (nonatomic, weak) RCTBridge *bridge;
@property (nonatomic, strong) YZWebView *webView;
@property (nonatomic, copy, nonnull) NSDictionary *source;

@end

@implementation YouzanBrowser

- (instancetype)initWithBridge:(RCTBridge *)bridge {
    self = [super init];
    if (self) {
        self.bridge = bridge;
        self.webView = [[YZWebView alloc]initWithWebViewType:YZWebViewTypeWKWebView];
        self.webView.delegate = self;
        self.webView.noticeDelegate = self;
        self.webView.frame = self.bounds;
        [self addSubview:self.webView];
    }
    return self;
}

- (void)setWidth:(NSNumber *)width {
    if (width != nil) {
        _webView.frame = CGRectMake(0, 0, width.floatValue, _webView.frame.size.height);
    } else {
        _webView.frame = CGRectMake(0, 0, 375, _webView.frame.size.height);
    }
}

- (void)setHeight:(NSNumber *)height {
    if (height != nil) {
        _webView.frame = CGRectMake(0, 0, _webView.frame.size.width, height.floatValue);
    } else {
        _webView.frame = CGRectMake(0, 0, _webView.frame.size.width, 600);
    }
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

- (void)goBackHome {
    if ([_webView canGoBack]) {
        NSInteger step = [_webView countOfHistory];
        [_webView gobackWithStep:step];
    }
}

- (void)goBackWithStep:(NSInteger)step {
    if ([_webView canGoBack]) {
        [_webView gobackWithStep:step];
    }
}

- (NSMutableDictionary<NSString *, id> *)baseEvent {
    NSDictionary *event = @{
        @"url": _webView.URL.absoluteString ? : @"",
        @"canGoBack": @(_webView.canGoBack),
        @"canGoForward": @(_webView.canGoForward)
    };
    return [[NSMutableDictionary alloc] initWithDictionary:event];
}

#pragma mark YZWebViewDelegate

- (BOOL)webView:(id<YZWebView>)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(WKNavigationType)navigationType {
    if (self.onLoad) {
        self.onLoad([self baseEvent]);
    }
    return YES;
}
- (void)webViewDidStartLoad:(id<YZWebView>)webView {
    if (self.onLoadStart) {
        self.onLoadStart([self baseEvent]);
    }
}
- (void)webViewDidFinishLoad:(id<YZWebView>)webView {
    if (self.onLoadEnd) {
        NSMutableDictionary<NSString *, id> *event = [self baseEvent];
        NSString *title = [self.webView stringByEvaluatingJavaScriptFromString:@"document.title"];
        [event setObject:title forKey:@"title"];
        self.onLoadEnd(event);
    }
}
- (void)webView:(id<YZWebView>)webView didFailLoadWithError:(NSError *)error {
    if (self.onLoadError) {
        self.onLoadError([self baseEvent]);
    }
}
- (void)webViewWebContentProcessDidTerminate:(id<YZWebView>)webView {
    NSLog(@"webViewWebContentProcessDidTerminate");
}

#pragma mark YZWebViewNoticeDelegate

- (void)webView:(nonnull id<YZWebView>)webView didReceiveNotice:(nonnull YZNotice *)notice {
    switch (notice.type) {
            // 收到登陆请求
        case YZNoticeTypeLogin: {
            if (self.onLogin) {
                NSMutableDictionary<NSString *, id> *event = [self baseEvent];
                if (notice.response != nil) {
                    [event setObject:notice.response forKey:@"data"];
                }
                self.onLogin(event);
            }
            break;
        }
            // 收到分享的回调数据
        case YZNoticeTypeShare: {
            if (self.onShare) {
                NSMutableDictionary<NSString *, id> *event = [self baseEvent];
                if (notice.response != nil) {
                    NSMutableDictionary<NSString *, id> *data = [[NSMutableDictionary alloc]init];
                    [data setObject:[notice.response objectForKey:@"title"] forKey:@"title"];
                    [data setObject:[notice.response objectForKey:@"desc"] forKey:@"desc"];
                    [data setObject:[notice.response objectForKey:@"link"] forKey:@"link"];
                    [data setObject:[notice.response objectForKey:@"img_url"] forKey:@"imgUrl"];
                    [event setObject:data forKey:@"data"];
                }
                self.onShare(event);
            }
            break;
        }
            // Web页面已准备好，此时可以分享
        case YZNoticeTypeReady: {
            if (self.onReady) {
                NSMutableDictionary<NSString *, id> *event = [self baseEvent];
                if (notice.response != nil) {
                    [event setObject:notice.response forKey:@"data"];
                }
                self.onReady(event);
            }
            break;
        }
            // 加入购物车的时候调用
        case YZNoticeTypeAddToCart: {
            if (self.onAddToCart) {
                NSMutableDictionary<NSString *, id> *event = [self baseEvent];
                if (notice.response != nil) {
                    NSMutableDictionary<NSString *, id> *data = [[NSMutableDictionary alloc]init];
                    [data setObject:[notice.response objectForKey:@"item_id"] forKey:@"itemId"];
                    [data setObject:[notice.response objectForKey:@"sku_id"] forKey:@"skuId"];
                    [data setObject:[notice.response objectForKey:@"alias"] forKey:@"alias"];
                    [data setObject:[notice.response objectForKey:@"title"] forKey:@"title"];
                    [data setObject:[notice.response objectForKey:@"num"] forKey:@"num"];
                    [data setObject:[notice.response objectForKey:@"pay_price"] forKey:@"payPrice"];
                    [event setObject:data forKey:@"data"];
                }
                self.onAddToCart(event);
            }
            break;
        }
            // 商品详情立即购买
        case YZNoticeTypeBuyNow: {
            if (self.onBuyNow) {
                NSMutableDictionary<NSString *, id> *event = [self baseEvent];
                if (notice.response != nil) {
                    NSMutableDictionary<NSString *, id> *data = [[NSMutableDictionary alloc]init];
                    [data setObject:[notice.response objectForKey:@"item_id"] forKey:@"itemId"];
                    [data setObject:[notice.response objectForKey:@"sku_id"] forKey:@"skuId"];
                    [data setObject:[notice.response objectForKey:@"alias"] forKey:@"alias"];
                    [data setObject:[notice.response objectForKey:@"title"] forKey:@"title"];
                    [data setObject:[notice.response objectForKey:@"num"] forKey:@"num"];
                    [data setObject:[notice.response objectForKey:@"pay_price"] forKey:@"payPrice"];
                    [event setObject:data forKey:@"data"];
                }
                self.onBuyNow(event);
            }
            break;
        }
            // 购物车结算
        case YZNoticeTypeAddUp: {
            if (self.onAddUp) {
                NSMutableDictionary<NSString *, id> *event = [self baseEvent];
                if (notice.response != nil) {
                    NSString *goodsJson = [notice.response objectForKey:@"goodsList"];
                    NSMutableArray *goodsList = [self arrayWithJsonString:goodsJson];
                    NSMutableArray *data = [[NSMutableArray alloc]init];
                    if (goodsList.count != 0) {
                        for (NSMutableDictionary<NSString *, id> *good in goodsList) {
                            [data addObject:@{
                                @"itemId": [good objectForKey:@"item_id"],
                                @"skuId": [good objectForKey:@"sku_id"],
                                @"alias": [good objectForKey:@"alias"],
                                @"title": [good objectForKey:@"title"],
                                @"num": [good objectForKey:@"num"],
                                @"payPrice": [good objectForKey:@"pay_price"],
                                @"selected": [good objectForKey:@"selected"]
                            }];
                        }
                        [event setObject:data forKey:@"data"];
                    }
                }
                self.onAddUp(event);
            }
            break;
        }
            // 支付成功回调结果页
        case YZNoticeTypePaymentFinished: {
            if (self.onPaymentFinished) {
                NSMutableDictionary<NSString *, id> *event = [self baseEvent];
                if (notice.response != nil) {
                    NSMutableDictionary<NSString *, id> *data = [[NSMutableDictionary alloc]init];
                    [data setObject:[notice.response objectForKey:@"tid"] forKey:@"tid"];
                    [data setObject:[notice.response objectForKey:@"status"] forKey:@"status"];
                    [data setObject:[notice.response objectForKey:@"pay_type"] forKey:@"payType"];
                    [event setObject:data forKey:@"data"];
                }
                self.onPaymentFinished(event);
            }
            break;
        }
        case YZNoticeTypeOther: {
            NSLog(@"response: %@", notice.response);
        }
        default:
            break;
    }
}

#pragma mark Utils
- (id)arrayWithJsonString:(NSString *)jsonString {
    if (jsonString == nil) {
        return nil;
    }
    NSData *jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
    NSError *error;
    NSMutableArray *array = [NSJSONSerialization JSONObjectWithData:jsonData
                                                                options:NSJSONReadingMutableContainers
                                                                  error:&error];
    if (error) {
        return nil;
    }
    return array;
}

@end
