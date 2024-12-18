import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Layout from '../components/Layout';
import './LandingPage.css';

const LandingPage = () => {
    const navigate = useNavigate();
    const [hoveredButton, setHoveredButton] = useState(null);

    const navigateTo = (path) => {
        navigate(path);
    };

    return (
        <Layout>
            <div className="landing-container">
                <h1>Welcome to the Air Quality Monitoring App</h1>
                <p>This application allows you to monitor air quality in various locations.</p>
                <div className="button-container">
                    <div
                        className="button-wrapper"
                        onMouseEnter={() => setHoveredButton('fetch')}
                        onMouseLeave={() => setHoveredButton(null)}
                    >
                        <button onClick={() => navigateTo('/fetch-airquality')}>Fetch Air Quality</button>
                        {hoveredButton === 'fetch' && (
                            <div className="description-box">
                                Fetch real-time air quality data by coordinates or name.
                            </div>
                        )}
                    </div>
                    <div
                        className="button-wrapper"
                        onMouseEnter={() => setHoveredButton('historical')}
                        onMouseLeave={() => setHoveredButton(null)}
                    >
                        <button onClick={() => navigateTo('/historical-view')}>Historical View</button>
                        {hoveredButton === 'historical' && (
                            <div className="description-box">View historical air quality data.</div>
                        )}
                    </div>
                    <div
                        className="button-wrapper"
                        onMouseEnter={() => setHoveredButton('saved')}
                        onMouseLeave={() => setHoveredButton(null)}
                    >
                        <button onClick={() => navigateTo('/stored-data')}>View Stored Data</button>
                        {hoveredButton === 'saved' && (
                            <div className="description-box">View stored air quality records.</div>
                        )}
                    </div>
                </div>
            </div>
        </Layout>
    );
};

export default LandingPage;
