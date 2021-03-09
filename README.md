# React Native Youzan Getting Started Guide

## 1. 安装依赖包

`$ npm install react-native-youzan --save`

## 2. 对于 `0.59` 以下版本需要手动 `link`

`$ react-native link react-native-youzan`

## 3. 初始化 SDK

【重要❗️❗️❗️】在有赞云控制台获取 `client_id`，并配置好 `App` 安全码（配置教程参考[官方文档](https://doc.youzanyun.com/resource/develop-guide/35675/38923)）

注意以下代码中 `yourClientId` 和 `yourAppKey` 即为此处配置的值。

### iOS

#### 1. 在 `Podfile` 中引入 `YZAppSDK` 并执行 `pod install`

```objectivec
pod 'YZAppSDK'
```

#### 2. 在 AppDelegate.m 文件中添加如下代码

```objectivec
...
#import <YZBaseSDK/YZBaseSDK.h>

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  ...

  YZConfig *yzConfig = [[YZConfig alloc] initWithClientId:@"yourClientId" andAppKey:@"yourAppKey"];
  [YZSDK.shared initializeSDKWithConfig:yzConfig];
  YZSDK.shared.delegate = self;

  ...
}
```

### Android

#### 1. 在 `app/build.gradle` dependencies 添加依赖

```java
dependencies {
  ...
  implementation 'com.youzanyun.open.mobile:x5sdk:7.1.11', {
    exclude group: 'com.android.support'
  }
}
```

#### 2. 在 `MainApplication` 中添加如下代码

```java
...
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdkx5.YouZanSDKX5Adapter;
import com.youzan.androidsdkx5.YouzanPreloader;


@Override
public void onCreate() {
  ...
  YouzanSDK.init(this, "yourClientId", "yourAppKey", new YouZanSDKX5Adapter());
}

```

## 3. 引入并使用组件

```javascript
import React, { Component } from 'react';
import { Browser } from 'react-native-youzan';

class MyView extends Component {
  render() {
    return (
      <Browser
        style={{ flex: 1 }}
        width={375}
        height={600}
        source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
      />
    );
  }
}
```
