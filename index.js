import React, { Component } from 'react';
import {
    requireNativeComponent,
} from 'react-native';
import PropTypes from 'prop-types';

const YouzanBrowser = requireNativeComponent('YouzanBrowser');

class Browser extends Component {
    render() {
        return <YouzanBrowser {...this.props} />;
    }
}

Browser.propTypes = {
    source: PropTypes.object,
}

export default Browser;