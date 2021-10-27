## API Reference

### Props Index

- [`source`](Reference.md#source)
- [`width`](Reference.md#width)
- [`height`](Reference.md#height)
- [`onLoad`](Reference.md#onLoad)
- [`onLoadStart`](Reference.md#onLoadStart)
- [`onLoadEnd`](Reference.md#onLoadEnd)
- [`onLoadError`](Reference.md#onLoadError)
- [`onReady`](Reference.md#onReady)
- [`onLogin`](Reference.md#onLogin)
- [`onShare`](Reference.md#onShare)
- [`onAddToCart`](Reference.md#onAddToCart)
- [`onAddUp`](Reference.md#onAddUp)
- [`onBuyNow`](Reference.md#onBuyNow)
- [`onPaymentFinished`](Reference.md#onPaymentFinished)

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
<YouzanBrowser
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
<YouzanBrowser
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
<YouzanBrowser
  source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
  onLoadEnd={({ nativeEvent }) => {
    this.url = nativeEvent.url;
  }}
/>
```

Function passed to `onLoadEnd` is called with a Event wrapping a nativeEvent with these properties:

```javascript
url
title
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
<YouzanBrowser
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
<YouzanBrowser
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
<YouzanBrowser
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
<YouzanBrowser
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
<YouzanBrowser
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
<YouzanBrowser
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
<YouzanBrowser
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
<YouzanBrowser
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
