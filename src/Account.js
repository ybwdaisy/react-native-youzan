import { NativeModules } from 'react-native';

const { YouzanBrowserManager } = NativeModules;

class Account {
    /**
     * 登录
     * @param {*} info
     */
    static login(info) {
        return YouzanBrowserManager.login(info)
    }

    /**
     * 退出登录
     */
    static logout() {
        return YouzanBrowserManager.logout();
    }
}

export default Account;