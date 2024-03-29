import { Component } from 'react'
import {
    BrowserProps,
    LoginInfo,
    AccountResult,
} from './src/Types';

declare class YouzanBrowser<P = {}> extends Component<BrowserProps & P> {
    /**
     * Reloads the current page.
     */
    reload: () => void;

    /**
     * Stop loading the current page.
     */
    stopLoading: () => void;

    /**
     * Go forward one page in the browser's history.
     */
    goForward: () => void;

    /**
     * Go back one page in the browser's history.
     */
    goBack: () => void;

     /**
      * Go back to init page.
      */
    goBackHome: () => void;

    /**
     * Go back steps page in the browser's history
     */
    goBackWithStep: (step: number) => void;

    /**
     * Android only
     * Select the file and send local uri path back to the browser.
     */
    receiveFile: (requestCode: number, uri?: string) => void;

    /**
     * Load url in the browser
     */
    loadUrl: (url: string) => void;

    browserRef: React.RefObject<BrowserProps>;
    getCommands: () => {
        reload: number;
        stopLoading: number;
        goForward: number;
        goBack: number;
        goBackWithStep: number;
        receiveFile: number;
        loadUrl: number;
    };
    findNodeHandle: (current: null | number | React.Component<any, any> | React.ComponentClass<any>) => null | number;
    render(): JSX.Element;
}

declare class YouzanAccount {
    static login: (info: LoginInfo) => Promise<AccountResult | void>;
    static logout: () => Promise<AccountResult | void>;
}

export {
    YouzanAccount,
    YouzanBrowser,
}