import React, { useState } from 'react';
import { fetchRealTimeAirQuality } from '../api';

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
            {error && <p>{error}</p>}
            {data && (
                <div>
                    <p>Message: {data.message}</p>
                    <p>Location: ({data.latitude}, {data.longitude})</p>
                    <p>Air Quality Index: {data.aqi}</p>
                    <p>CO: {data.co}</p>
                    <p>NO: {data.no}</p>
                    <p>NO2: {data.no2}</p>
                    <p>O3: {data.o3}</p>
                    <p>SO2: {data.so2}</p>
                    <p>PM2.5: {data.pm2_5}</p>
                    <p>PM10: {data.pm10}</p>
                    <p>NH3: {data.nh3}</p>
                </div>
            )}
        </div>
    );
}

export default RealTimeAirQuality;