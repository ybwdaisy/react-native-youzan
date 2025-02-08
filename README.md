# React Native Youzan Getting Started Guide

[![NPM Version](https://img.shields.io/npm/v/react-native-youzan.svg)](https://www.npmjs.com/package/react-native-youzan) [![Downloads](https://img.shields.io/npm/dm/react-native-youzan.svg?sanitize=true)](https://www.npmjs.com/package/react-native-youzan) [![License](https://img.shields.io/npm/l/vue.svg?sanitize=true)](https://www.npmjs.com/package/react-native-youzan)

## 安装依赖包

```bash
yarn add react-native-youzan
```

或

```bash
npm install react-native-youzan --save
```

## 对于 `React Native` `0.59` 以下版本需要手动 `link`

```bash
react-native link react-native-youzan
```

## 初始化 `SDK`

【❗️❗️❗️】在有赞云控制台获取 `client_id`，并配置好 `App` 安全码（配置教程参考[官方文档](https://doc.youzanyun.com/resource/develop-guide/35675/38923)）

注意以下代码中 `yourClientId` 和 `yourAppKey` 即为此处配置的值。

### iOS

#### 1. 在 `AppDelegate.m` 文件中添加如下代码

```objectivec
...
#import <YZBaseSDK/YZBaseSDK.h>

...

@interface AppDelegate () <YZSDKDelegate>
@end

...

@implementation AppDelegate

...

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  ...

  YZConfig *yzConfig = [[YZConfig alloc] initWithClientId:@"yourClientId" andAppKey:@"yourAppKey"];
  NSArray *URLTypes = [NSBundle mainBundle].infoDictionary[@"CFBundleURLTypes"];
  NSString *scheme = [[URLTypes firstObject][@"CFBundleURLSchemes"] firstObject];
  yzConfig.scheme = scheme;
  [YZSDK.shared initializeSDKWithConfig:yzConfig];
  YZSDK.shared.delegate = self;

  ...
}
```

### Android

#### 1. 在 `android/build.gradle` 添加有赞 `maven` 仓库地址

```java
allprojects {
    repositories {
      ...
      maven { url 'https://maven.youzanyun.com/repository/maven-releases' }
    }
}
```

#### 2. 在 `app/build.gradle` 添加依赖

```java
dependencies {
  ...
  implementation "com.youzanyun.open.mobile:x5sdk:7.14.14", {
    exclude group: "com.android.support", module: "x5official"
  }
}
```

#### 3. 在 `MainApplication` 中添加如下代码

```java
...
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdkx5.YouZanSDKX5Adapter;


@Override
public void onCreate() {
  ...
  YouzanSDK.init(this, "yourClientId", "yourAppKey", new YouZanSDKX5Adapter());
}

```

## 使用组件

```javascript
import React from 'react';
import { Dimensions } from 'react-native';
import { YouzanBrowser } from 'react-native-youzan';
const { width, height } = Dimensions.get('screen');

const App = (): JSX.Element => {
  return (
    <YouzanBrowser
      width={width}
      height={height}
      source={{uri: 'https://github.com/ybwdaisy/react-native-youzan'}}
    />
  );
};

export default App;
```

## 更多参考

- Props 详情：[API Reference](./docs/Reference.md)
- 实例方法：[Method](./docs/Method.md)
- SDK 静态方法：[SDK Method](./docs/SDK.md)
