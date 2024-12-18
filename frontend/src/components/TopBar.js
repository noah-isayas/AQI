import React from 'react';
import { Link } from 'react-router-dom';
import './TopBar.css';

const TopBar = () => {
    return (
        <nav className="topbar">
            <div className="logo">Air Quality App</div>
            <ul className="nav-links">
                <li><Link to="/">Home</Link></li>
                <li><Link to="/fetch-airquality">Fetch Air Quality</Link></li>
                <li><Link to="/historical-view">Historical View</Link></li>
                <li><Link to="/stored-data">View Stored Data</Link></li>
            </ul>
        </nav>
    );
};

export default TopBar;
