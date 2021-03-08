import {
    ViewProps,
    NativeSyntheticEvent,
} from 'react-native';

export interface BrowserSource {
    uri: string,
}

export interface BaseEvent {
    url: string,
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

export interface PaymentFinishedData {
    tid: string,
    status: string,
    payType: string,
}

export interface PaymentFinishedEvent extends BaseEvent {
    data?: PaymentFinishedData,
}

export declare type BaseEventType = NativeSyntheticEvent<BaseEvent>;
export declare type ShareEventType = NativeSyntheticEvent<ShareEvent>;
export declare type SKUItemEventType = NativeSyntheticEvent<SKUItemEvent>;
export declare type AddUpEventType = NativeSyntheticEvent<AddUpEvent>;
export declare type PaymentFinishedEventType = NativeSyntheticEvent<PaymentFinishedEvent>;

export interface BrowserProps extends ViewProps {
    source: BrowserSource,
    width?: number,
    height?: number,
    onLoad?: (event: BaseEventType) => void,
    onLoadStart?: (event: BaseEventType) => void,
    onLoadEnd?: (event: BaseEventType) => void,
    onLoadError?: (event: BaseEventType) => void,
    onReady?: (event: BaseEventType) => void,
    onLogin?: (event: BaseEventType) => void,
    onShare?: (event: ShareEventType) => void,
    onAddToCart?: (event: SKUItemEventType) => void,
    onAddUp?: (event: AddUpEventType) => void,
    onBuyNow?: (event: SKUItemEventType) => void,
    onPaymentFinished?: (event: PaymentFinishedEventType) => void,
}

export interface LoginInfo {
    openUserId: string,
    avatar?: string,
    extra?: string,
    nickName?: string,
    gender?: string,
}

export interface AccountResult {
    code?: number,
    yzOpenId?: string,
}
