# React Native Youzan Getting Started Guide

## 安装依赖包

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
      maven { url 'https://dl.bintray.com/youzanyun/maven/' }
    }
}
```

#### 2. 在 `app/build.gradle` 添加依赖

```java
dependencies {
  ...
  implementation 'com.youzanyun.open.mobile:x5sdk:7.1.11', {
    exclude group: 'com.android.support'
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

## API Reference

### Props Index

- [`source`](README.md#source)
- [`width`](README.md#width)
- [`height`](README.md#height)
- [`onLoad`](README.md#onLoad)
- [`onLoadStart`](README.md#onLoadStart)
- [`onLoadEnd`](README.md#onLoadEnd)
- [`onLoadError`](README.md#onLoadError)
- [`onReady`](README.md#onReady)
- [`onLogin`](README.md#onLogin)
- [`onShare`](README.md#onShare)
- [`onAddToCart`](README.md#onAddToCart)
- [`onAddUp`](README.md#onAddUp)
- [`onBuyNow`](README.md#onBuyNow)
- [`onPaymentFinished`](README.md#onPaymentFinished)

#### `source`

##### Load uri[⬆](#props-index)

- `uri`(string) - load uri

| Type   | Required |
| ------ | -------- |
| object | Yes      |

#### `width`[⬆](#props-index)

`View` width

| Type   | Required |
| ------ | -------- |
| object | Yes      |

#### `height`[⬆](#props-index)

`View` height

| Type   | Required |
| ------ | -------- |
| object | Yes      |

#### `onLoad`[⬆](#props-index)

Function that is invoked when the `View` has finished loading.

| Type     | Required |
| -------- | -------- |
| function | No       |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onLoad={({ nativeEvent }) => {
    this.url = nativeEvent.url;
  }}
/>
```

Function passed to `onLoad` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
```

#### `onLoadStart`[⬆](#props-index)

Function that is invoked when the `View` starts loading.

| Type     | Required |
| -------- | -------- |
| function | No       |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onLoadStart={({ nativeEvent }) => {
    this.url = nativeEvent.url;
  }}
/>
```

Function passed to `onLoadStart` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
```

#### `onLoadEnd`[⬆](#props-index)

Function that is invoked when the `View` load succeeds or fails.

| Type     | Required |
| -------- | -------- |
| function | No       |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onLoadEnd={({ nativeEvent }) => {
    this.url = nativeEvent.url;
  }}
/>
```

Function passed to `onLoadEnd` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
```

#### `onLoadError`[⬆](#props-index)

Function that is invoked when the `View` load fails.

| Type     | Required |
| -------- | -------- |
| function | No       |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onLoadError={({ nativeEvent }) => {
    this.url = nativeEvent.url;
  }}
/>
```

Function passed to `onLoadError` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
```

#### `onReady`[⬆](#props-index)

Function that is invoked when the `View` is ready.

| Type     | Required |
| -------- | -------- |
| function | No       |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onReady={({ nativeEvent }) => {
    this.url = nativeEvent.url;
  }}
/>
```

Function passed to `onReady` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
data
```

#### `onLogin`[⬆](#props-index)

Function that is invoked when the `View` require login.

| Type     | Required |
| -------- | -------- |
| function | Yes      |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onLogin={({ nativeEvent }) => {
    // TODO：Login For You App
  }}
/>
```

Function passed to `onLogin` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
data
```

#### `onShare`[⬆](#props-index)

Function that is invoked when the `View` share finished.

| Type     | Required |
| -------- | -------- |
| function | No       |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onShare={({ nativeEvent }) => {}}
/>
```

Function passed to `onShare` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
data
```

`data` properties:

```javascript
title
desc
link
imgUrl
```

#### `onAddToCart`[⬆](#props-index)

Function that is invoked when user add item to cart.

| Type     | Required |
| -------- | -------- |
| function | No       |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onAddToCart={({ nativeEvent }) => {}}
/>
```

Function passed to `onAddToCart` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
data
```

`data` properties:

```javascript
itemId
skuId
alias
title
num
payPrice
```

#### `onAddUp`[⬆](#props-index)

Function that is invoked when user add up at cart page.

| Type     | Required |
| -------- | -------- |
| function | No       |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onAddUp={({ nativeEvent }) => {}}
/>
```

Function passed to `onAddUp` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
data
```

`data` is array and every item with properties:

```javascript
itemId
skuId
alias
title
num
payPrice
selected
```

#### `onBuyNow`[⬆](#props-index)

Function that is invoked when user click buy now at detail page.

| Type     | Required |
| -------- | -------- |
| function | No       |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onBuyNow={({ nativeEvent }) => {}}
/>
```

Function passed to `onBuyNow` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
data
```

`data` is array and every item with properties:

```javascript
itemId
skuId
alias
title
num
payPrice
```

#### `onPaymentFinished`[⬆](#props-index)

Function that is invoked when user pay finished.

| Type     | Required |
| -------- | -------- |
| function | No       |

Example:

```jsx
<Browser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onPaymentFinished={({ nativeEvent }) => {}}
/>
```

Function passed to `onPaymentFinished` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
canGoBack
canGoForward
data
```

`data` is array and every item with properties:

```javascript
tid
status
payType
```

### Methods Index

- [`goBack`](README.md#goBack)
- [`goForward`](README.md#goForward)
- [`reload`](README.md#reload)
- [`stopLoading`](README.md#stopLoading)
- [`goBackWithStep`](README.md#goBackWithStep)
- [`syncToken`](README.md#syncToken)

#### `goBack`[⬆](#methods-index)

```javascript
goBack();
```

Go back one page in the view's history.

#### `goForward`[⬆](#methods-index)

```javascript
goForward();
```

Go forward one page in the view's history.

### `reload()`[⬆](#methods-index)

```javascript
reload();
```

Reloads the current page.

### `stopLoading()`[⬆](#methods-index)

```javascript
stopLoading();
```

Stop loading the current page.

#### `goBackWithStep`[⬆](#methods-index)

```javascript
goBackWithStep(step);
```

Go back `step` page in the view's history.

#### `syncToken`[⬆](#methods-index)

```javascript
syncToken(token);
```

Sync token to browser.

### SDK Methods Index

- [`login`](README.md#login)
- [`logout`](README.md#logout)

#### `login`[⬆](#sdk-methods-index)

```javascript
import { Account } from 'react-native-youzan';

Account.login({
  openUserId: userId,
  avatar: '',
  extra: '',
  nickName: '',
  gender: 0,
}).then((res) => {
  // Login Success
});
```

Global login Youzan SDK. Callback data with properties:

```javascript
code
yzOpenId
```

#### `logout`[⬆](#sdk-methods-index)

```javascript
import { Account } from 'react-native-youzan';

Account.logout().then((res) => {
  // Logout Success
});
```

Global logout Youzan SDK.
