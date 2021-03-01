//
//  YouzanBrowserManager.m
//  react-native-youzan
//
//  Created by ybw-macbook-pro on 2021/3/1.
//

#import "YouzanBrowserManager.h"

@implementation YouzanBrowserManager

RCT_EXPORT_VIEW_PROPERTY(onLoad, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onLoadStart, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onLoadEnd, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onLoadProgress, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onLoadError, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onMessage, RCTDirectEventBlock);

@end
