import React from 'react';
import TopBar from './TopBar';

const Layout = ({ children }) => {
    return (
        <div>
            <TopBar />
            <div>{children}</div>
        </div>
    );
};

export default Layout;
