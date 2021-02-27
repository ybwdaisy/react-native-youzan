### React Native Youzan Getting Started Guide

#### 1. Add react-native-youzan to your dependencies

`$ npm install react-native-youzan --save`

#### 2. Link Native dependencies

If you are using React Native <= 0.59.x, link the native project:

`$ react-native link react-native-youzan`

#### 3. Import the YouzanBrowser into your Component

```javascript
import React, { Component } from 'react';
import { YouzanBrowser } from 'react-native-youzan';

class MyView extends Component {
  render() {
    return (
      <YouzanBrowser
        source={{ uri: 'https://github.com/ybwdaisy/react-native-youzan' }}
      />
    );
  }
}
```
