//
//  YouzanBrowser.h
//  react-native-youzan
//
//  Created by ybw-macbook-pro on 2021/3/1.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface YouzanBrowser : RCTEventEmitter <RCTBridgeModule>

@property (nonatomic, copy) NSDictionary * _Nullable source;
@property (nonatomic, copy) NSString * _Nullable userAgent;

- (void)reload;
- (void)stopLoading;
- (void)goForward;
- (void)goBack;
- (void)goBackWithStep:(NSInteger)step;

- (void)login:(nonnull NSDictionary *)loginInfo;

@end
