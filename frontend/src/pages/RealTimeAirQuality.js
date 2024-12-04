import React, { useState } from 'react';
import { fetchRealTimeAirQuality } from '../api';

function RealTimeAirQuality() {
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);

    const handleFetchData = async () => {
        try {
            const result = await fetchRealTimeAirQuality(latitude, longitude);
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
            <button onClick={handleFetchData}>Get Air Quality</button>
            {error && <p>{error}</p>}
            {data && (
                <div>
                    <p>Message: {data.message}</p>
                    <p>Location: ({data.latitude}, {data.longitude})</p>
                    <p>Air Quality Index: {data.aqi}</p>
                </div>
            )}
        </div>
    );
}

export default RealTimeAirQuality;