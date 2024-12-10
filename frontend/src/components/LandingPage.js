import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './LandingPage.css';

const LandingPage = () => {
    const navigate = useNavigate();
    const [hoveredButton, setHoveredButton] = useState(null);

    const navigateTo = (path) => {
        navigate(path);
    };

    return (
        <div style={{ textAlign: 'center', padding: '50px' }}>
            <h1>Welcome to the Air Quality Monitoring App</h1>
            <p>This application allows you to monitor air quality in various locations.</p>
            <p>Choose an option below to get started:</p>
            <div className="button-container">
                <div
                    className="button-wrapper"
                    onMouseEnter={() => setHoveredButton('fetch')}
                    onMouseLeave={() => setHoveredButton(null)}
                >
                    <button onClick={() => navigateTo('/fetch-airquality')}>Fetch Air Quality</button>
                    {hoveredButton === 'fetch' && (
                        <div className="description-box">Fetch real-time air quality data by coordinates or name.</div>
                    )}
                </div>
                <div
                    className="button-wrapper"
                    onMouseEnter={() => setHoveredButton('historical')}
                    onMouseLeave={() => setHoveredButton(null)}
                >
                    <button onClick={() => navigateTo('/historical-view')}>Historical View (Last 5 Days)</button>
                    {hoveredButton === 'historical' && (
                        <div className="description-box">View historical air quality data for the last 5 days.</div>
                    )}
                </div>
                <div
                    className="button-wrapper"
                    onMouseEnter={() => setHoveredButton('saved')}
                    onMouseLeave={() => setHoveredButton(null)}
                >
                    <button onClick={() => navigateTo('/saved-data')}>View Saved Data</button>
                    {hoveredButton === 'saved' && (
                        <div className="description-box">View data saved in the database.</div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default LandingPage;