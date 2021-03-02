import React, { Component } from 'react';
import { requireNativeComponent } from 'react-native';
import PropTypes from 'prop-types';

const YouzanBrowser = requireNativeComponent('YouzanBrowser');

class Browser extends Component {
    render() {
        return <YouzanBrowser {...this.props} />;
    }
}

Browser.propTypes = {
    source: PropTypes.shape({
        uri: PropTypes.string,
    }).isRequired,
    containerSize: PropTypes.shape({
        width: PropTypes.number,
        height: PropTypes.number
    }).isRequired,
    onLoad: PropTypes.func,
    onLoadStart: PropTypes.func,
    onLoadEnd: PropTypes.func,
    onLoadError: PropTypes.func,
}

export default Browser;