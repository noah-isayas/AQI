import React, { useState, useEffect } from 'react';
import { fetchHistoricalAirQuality } from '../api';

function HistoricalAirQuality() {
    const [location, setLocation] = useState('New York');
    const [data, setData] = useState(null);

    useEffect(() => {
        const getData = async () => {
            const result = await fetchHistoricalAirQuality(location);
            setData(result);
        };
        getData();
    }, [location]);

    return (
        <div>
            <h1>Historical Air Quality</h1>
            <input
                type="text"
                value={location}
                onChange={(e) => setLocation(e.target.value)}
                placeholder="Enter location"
            />
            {data && (
                <div>
                    <p>Location: {data.location}</p>
                    <p>Historical Data: {JSON.stringify(data.history)}</p>
                </div>
            )}
        </div>
    );
}

export default HistoricalAirQuality;