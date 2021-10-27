
### SDK Methods Index

- [`login`](SDK.md#login)
- [`logout`](SDK.md#logout)

#### `login`[⬆](#sdk-methods-index)

```javascript
import { YouzanAccount } from 'react-native-youzan';

YouzanAccount.login({
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
data
```

`data` with properties:

```javascript
yzOpenId
accessToken
cookieKey
cookieValue
```

#### `logout`[⬆](#sdk-methods-index)

```javascript
import { YouzanAccount } from 'react-native-youzan';

YouzanAccount.logout().then((res) => {
  // Logout Success
});
```

Global logout Youzan SDK.
