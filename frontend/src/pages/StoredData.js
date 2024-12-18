import React, { useState, useEffect } from 'react';
import { fetchStoredData } from '../api';
import TopBar from '../components/TopBar';
import './StoredData.css';

function StoredData() {
    const [data, setData] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const getData = async () => {
            try {
                const result = await fetchStoredData();// Sort data by timestamp (newest first)
                const sortedData = result.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
                setData(sortedData);
            } catch (err) {
                setError('Failed to fetch stored data');
            }
        };
        getData();
    }, []);

    return (
        <div>
            <TopBar /> {/* Add TopBar here */}
            <div className="stored-container">
                <h1>Stored Data</h1>
                {error && <p className="error-message">{error}</p>}
                {data.length > 0 ? (
                    <div className="data-list">
                        {data.map((item) => (
                            <div className="data-card" key={item.id}>
                                <h2>{item.city}</h2>
                                <p><span>Latitude:</span> {item.latitude}</p>
                                <p><span>Longitude:</span> {item.longitude}</p>
                                <p><span>Timestamp:</span> {new Date(item.timestamp).toLocaleString()}</p>
                                <p><span>AQI:</span> {item.aqi}</p>
                                <p><span>Message:</span> {item.message}</p>
                                <h3>Pollutants</h3>
                                <ul className="pollutant-list">
                                    <li><span>CO:</span> {item.pollutants.co}</li>
                                    <li><span>NO:</span> {item.pollutants.no}</li>
                                    <li><span>NO2:</span> {item.pollutants.no2}</li>
                                    <li><span>O3:</span> {item.pollutants.o3}</li>
                                    <li><span>SO2:</span> {item.pollutants.so2}</li>
                                    <li><span>PM2.5:</span> {item.pollutants.pm2_5}</li>
                                    <li><span>PM10:</span> {item.pollutants.pm10}</li>
                                    <li><span>NH3:</span> {item.pollutants.nh3}</li>
                                </ul>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p className="no-data">No data available</p>
                )}
            </div>
        </div>
    );
}

export default StoredData;
