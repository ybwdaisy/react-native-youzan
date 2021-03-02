//
//  YouzanBrowser.h
//  react-native-youzan
//
//  Created by ybw-macbook-pro on 2021/3/1.
//

#import "RCTBridgeModule.h"
#import "RCTComponent.h"
#import "RCTView.h"

@interface YouzanBrowser : RCTView <RCTBridgeModule>
 
@property(nonatomic, copy) RCTDirectEventBlock onLoad;
@property(nonatomic, copy) RCTDirectEventBlock onLoadStart;
@property(nonatomic, copy) RCTDirectEventBlock onLoadEnd;
@property(nonatomic, copy) RCTDirectEventBlock onLoadError;

- (instancetype)init;

- (void)reload;
- (void)stopLoading;
- (void)goForward;
- (void)goBack;
- (void)goBackWithStep:(NSInteger)step;

- (void)login:(nonnull NSDictionary *)loginInfo;

@end
