//
//  YouzanBrowser.h
//  react-native-youzan
//
//  Created by ybw-macbook-pro on 2021/3/1.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTComponent.h>
#import <React/RCTView.h>

@interface YouzanBrowser : RCTView <RCTBridgeModule>
 
@property(nonatomic, copy) RCTDirectEventBlock onLoad;
@property(nonatomic, copy) RCTDirectEventBlock onLoadStart;
@property(nonatomic, copy) RCTDirectEventBlock onLoadEnd;
@property(nonatomic, copy) RCTDirectEventBlock onLoadError;

@property(nonatomic, copy) RCTDirectEventBlock onLogin;
@property(nonatomic, copy) RCTDirectEventBlock onShare;
@property(nonatomic, copy) RCTDirectEventBlock onReady;
@property(nonatomic, copy) RCTDirectEventBlock onAddToCart;
@property(nonatomic, copy) RCTDirectEventBlock onBuyNow;
@property(nonatomic, copy) RCTDirectEventBlock onAddUp;
@property(nonatomic, copy) RCTDirectEventBlock onPaymentFinished;

- (instancetype)initWithBridge:(RCTBridge *)bridge;

- (void)reload;
- (void)stopLoading;
- (void)goForward;
- (void)goBack;
- (void)goBackWithStep:(NSInteger)step;
- (void)syncToken:(NSDictionary *)token;

@end
