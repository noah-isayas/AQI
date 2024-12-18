import React, { useState } from 'react';
import { fetchRealTimeAirQuality } from '../api';
import TopBar from '../components/TopBar';
import './RealTimeAirQuality.css';

function RealTimeAirQuality() {
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');
    const [location, setLocation] = useState('');
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);
    const [searchMode, setSearchMode] = useState('coordinates'); // 'coordinates' or 'location'

    const handleFetchData = async () => {
        try {
            const result = await fetchRealTimeAirQuality(latitude, longitude, location);
            setData(result);
            setError(null);
        } catch (err) {
            setError('Failed to fetch data');
            setData(null);
        }
    };

    return (
        <div>
            <TopBar />
            <div className="realtime-container">
                <h1>Real-Time Air Quality</h1>
                <div>
                    <button onClick={() => setSearchMode('coordinates')}>Search by Coordinates</button>
                    <button onClick={() => setSearchMode('location')}>Search by Location Name</button>
                </div>
                {searchMode === 'coordinates' && (
                    <div>
                        <input
                            type="text"
                            value={latitude}
                            onChange={(e) => setLatitude(e.target.value)}
                            placeholder="Enter latitude"
                        />
                        <input
                            type="text"
                            value={longitude}
                            onChange={(e) => setLongitude(e.target.value)}
                            placeholder="Enter longitude"
                        />
                    </div>
                )}
                {searchMode === 'location' && (
                    <div>
                        <input
                            type="text"
                            value={location}
                            onChange={(e) => setLocation(e.target.value)}
                            placeholder="Enter location name"
                        />
                    </div>
                )}
                <button onClick={handleFetchData}>Get Air Quality</button>
                {error && <p className="error">{error}</p>}
                {data && (
                    <div className="realtime-results">
                        <p><span>Message:</span> {data.message}</p>
                        <p><span>City name:</span> {location || "N/A"}</p>
                        <p><span>Location:</span> ({data.latitude}, {data.longitude})</p>
                        <p><span>Air Quality Index:</span> {data.aqi}</p>
                        <p><span>CO:</span> {data.co}</p>
                        <p><span>NO:</span> {data.no}</p>
                        <p><span>NO2:</span> {data.no2}</p>
                        <p><span>O3:</span> {data.o3}</p>
                        <p><span>SO2:</span> {data.so2}</p>
                        <p><span>PM2.5:</span> {data.pm2_5}</p>
                        <p><span>PM10:</span> {data.pm10}</p>
                        <p><span>NH3:</span> {data.nh3}</p>
                    </div>
                )}
            </div>
        </div>
    );
}

export default RealTimeAirQuality;
