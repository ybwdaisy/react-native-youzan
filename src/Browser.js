import React, { Component } from 'react';
import {
    UIManager,
    findNodeHandle,
    requireNativeComponent,
    Platform,
} from 'react-native';
import PropTypes from 'prop-types';

const YouzanBrowser = requireNativeComponent('YouzanBrowser');

class Browser extends Component {
    static propTypes = {
        source: PropTypes.shape({
            uri: PropTypes.string,
        }).isRequired,
        width: PropTypes.number.isRequired,
        height: PropTypes.number.isRequired,
        onLoad: PropTypes.func,
        onLoadStart: PropTypes.func,
        onLoadEnd: PropTypes.func,
        onLoadError: PropTypes.func,
        onReady: PropTypes.func,
        onLogin: PropTypes.func.isRequired,
        onShare: PropTypes.func,
        onChooser: PropTypes.func,
        onAddToCart: PropTypes.func,
        onBuyNow: PropTypes.func,
        onAddUp: PropTypes.func,
        onPaymentFinished: PropTypes.func,
    }

    constructor(props) {
        super(props);
        this.browserRef = React.createRef();
    }

    getCommands = () => UIManager.getViewManagerConfig('YouzanBrowser').Commands;

    findNodeHandle = () => findNodeHandle(this.browserRef.current)

    reload = () => {
        UIManager.dispatchViewManagerCommand(
            this.findNodeHandle(),
            this.getCommands().reload,
            undefined,
        )
    }

    stopLoading = () => {
        UIManager.dispatchViewManagerCommand(
            this.findNodeHandle(),
            this.getCommands().stopLoading,
            undefined,
        )
    }

    goForward = () => {
        UIManager.dispatchViewManagerCommand(
            this.findNodeHandle(),
            this.getCommands().goForward,
            undefined,
        )
    }

    goBack = () => {
        UIManager.dispatchViewManagerCommand(
            this.findNodeHandle(),
            this.getCommands().goBack,
            undefined,
        )
    }

    goBackHome = () => {
        UIManager.dispatchViewManagerCommand(
            this.findNodeHandle(),
            this.getCommands().goBackHome,
            undefined,
        )
    }

    goBackWithStep = (step) => {
        UIManager.dispatchViewManagerCommand(
            this.findNodeHandle(),
            this.getCommands().goBackWithStep,
            [step],
        )
    }

    receiveFile = (requestCode, uri) => {
        if (Platform.OS === 'android') {
            UIManager.dispatchViewManagerCommand(
                this.findNodeHandle(),
                this.getCommands().receiveFile,
                [requestCode, uri],
            )
        }
    }

    loadUrl = (url) => {
        UIManager.dispatchViewManagerCommand(
            this.findNodeHandle(),
            this.getCommands().loadUrl,
            [url],
        )
    }

    render() {
        const {
            style,
            width,
            height
        } = this.props;
        return (
            <YouzanBrowser
                ref={this.browserRef}
                {...this.props}
                style={[style, { width, height }]}
            />
        );
    }
}

export default Browser;