import { Component } from 'react'
import {
    BrowserProps,
    LoginInfo,
    AccountResult,
} from './src/Types';

declare class Browser<P = {}> extends Component<BrowserProps & P> {
    /**
     * Go back one page in the browser's history.
     */
    goBack: () => void;

    /**
     * Go forward one page in the browser's history.
     */
    goForward: () => void;

    /**
     * Reloads the current page.
     */
    reload: () => void;

    /**
     * Stop loading the current page.
     */
    stopLoading: () => void;

    /**
     * Go back steps page in the browser's history
     */
    goBackWithStep: (step: number) => void;

    browserRef: React.RefObject<BrowserProps>;
    getCommands: () => {
        reload: number;
        stopLoading: number;
        goForward: number;
        goBack: number;
        goBackWithStep: number;
    };
    findNodeHandle: (current: null | number | React.Component<any, any> | React.ComponentClass<any>) => null | number;
    render(): JSX.Element;
}

declare class Account {
    static login: (info: LoginInfo) => Promise<AccountResult | void>;
    static logout: () => Promise<AccountResult | void>;
}

export {
    Account,
    Browser,
}