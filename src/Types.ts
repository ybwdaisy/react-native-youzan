import {
    ViewProps,
    NativeSyntheticEvent,
} from 'react-native';

export interface BrowserSource {
    uri: string,
}

export interface BaseEvent {
    url: string,
    title?: string,
    canGoBack: boolean,
    canGoForward: boolean,
}

export interface ShareEventData {
    title: string,
    desc: string,
    link: string,
}

export interface ShareEvent extends BaseEvent {
    data?: ShareEventData,
}

export interface ChooserEvent extends BaseEvent {
    requestCode: number,
    acceptType: string,
}

export interface SKUItem {
    itemId: string,
    skuId: string,
    alias: string,
    title: string,
    num: string,
    payPrice: string,
}

export interface SKUItemEvent extends BaseEvent {
    data?: SKUItem,
}

export interface AddUpEventData extends SKUItem {
    selected: boolean,
}

export interface AddUpEvent extends BaseEvent {
    data?: AddUpEventData[],
}

/**
 * -----------
 * payType 枚举
 * 0 其他
 * 1 微信支付
 * 2 支付宝支付
 * 3 储蓄卡支付
 * 4 信用卡支付
 * 5 储值支付（E卡 礼品卡 储值卡等）
 * 6 他人代付
 * -----------
 * status 枚举
 * 0 支付失败
 * 1 支付成功
 */
export interface PaymentFinishedData {
    tid: string,
    status: string,
    payType: string,
}

export interface PaymentFinishedEvent extends BaseEvent {
    data?: PaymentFinishedData,
}

/**
 * code 枚举
 * 1001 当前App不支持授权
 * 1002 未开启手机号授权
 * 1003 未找到手机号
 * 1004 点击授权失败
 */
export interface AuthorizationFailedData {
    code: number,
    message: string,
}

export interface AuthorizationFailedEvent extends BaseEvent {
    data?: AuthorizationFailedData,
}

export declare type BaseEventType = NativeSyntheticEvent<BaseEvent>;
export declare type ShareEventType = NativeSyntheticEvent<ShareEvent>;
export declare type ChooserEventType = NativeSyntheticEvent<ChooserEvent>;
export declare type SKUItemEventType = NativeSyntheticEvent<SKUItemEvent>;
export declare type AddUpEventType = NativeSyntheticEvent<AddUpEvent>;
export declare type PaymentFinishedEventType = NativeSyntheticEvent<PaymentFinishedEvent>;
export declare type AuthorizationSucceedEventType = NativeSyntheticEvent<BaseEvent>;
export declare type AuthorizationFailedEventType = NativeSyntheticEvent<AuthorizationFailedEvent>;

export interface BrowserProps extends ViewProps {
    source: BrowserSource,
    width: number,
    height: number,
    onLoad?: (event: BaseEventType) => void,
    onLoadStart?: (event: BaseEventType) => void,
    onLoadEnd?: (event: BaseEventType) => void,
    onLoadError?: (event: BaseEventType) => void,
    onReady?: (event: BaseEventType) => void,
    onLogin: (event: BaseEventType) => void,
    onChooser?: (event: ChooserEventType) => void,
    onShare?: (event: ShareEventType) => void,
    onAddToCart?: (event: SKUItemEventType) => void,
    onAddUp?: (event: AddUpEventType) => void,
    onBuyNow?: (event: SKUItemEventType) => void,
    onPaymentFinished?: (event: PaymentFinishedEventType) => void,
    onAuthorizationSucceed?: (event: BaseEventType) => void,
    onAuthorizationFailed?: (event: AuthorizationFailedEvent) => void,
}

export interface LoginInfo {
    openUserId: string,
    avatar?: string,
    extra?: string,
    nickName?: string,
    gender?: string,
}

export interface YouzanToken {
    yzOpenId: string,
    accessToken?: string,
    cookieKey?: string,
    cookieValue?: string,
}

export interface AccountResult {
    code: number,
    data: YouzanToken,
}
