import React, { useState } from 'react';
import { fetchHistoricalAirQuality } from '../api';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

function HistoricalAirQuality() {
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');
    const [location, setLocation] = useState('');
    const [data, setData] = useState([]);
    const [error, setError] = useState(null);
    const [searchMode, setSearchMode] = useState('coordinates'); // 'coordinates' or 'location'

    const handleFetchData = async () => {
        setError(null);
        setData([]);
        try {
            const result = await fetchHistoricalAirQuality(latitude, longitude, location);
            // Format the historical API response for the graph
            const formattedData = result.list.map((item) => ({
                timestamp: new Date(item.dt * 1000).toLocaleDateString(),
                aqi: item.main.aqi,
                pm2_5: item.components.pm2_5,
                co: item.components.co,
                no: item.components.no,
                no2: item.components.no2,
                o3: item.components.o3,
                so2: item.components.so2,

            }));
            setData(formattedData);
        } catch (err) {
            setError('Failed to fetch historical data');
        }
    };

    return (
        <div>
            <h1>Historical Air Quality</h1>
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
            <button onClick={handleFetchData}>Fetch Historical Data</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {data.length > 0 && (
                <ResponsiveContainer width="80%" height={400}>
                    <LineChart data={data}>
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey="timestamp" />
                        <YAxis label={{ value: 'Values', angle: -90, position: 'insideLeft' }} />
                        <Tooltip />

                        {/* AQI Line */}
                        <Line type="monotone" dataKey="aqi" stroke="#8884d8" activeDot={{ r: 8 }} name="AQI" />

                        <Line type="monotone" dataKey="pm2_5" stroke="#82ca9d" name="PM2.5" />

                        {/*<Line type="monotone" dataKey="co" stroke="#ff7300" name="CO" />*/}

                        <Line type="monotone" dataKey="no" stroke="#387908" name="NO" />

                        <Line type="monotone" dataKey="no2" stroke="#003f5c" name="NO2" />

                        <Line type="monotone" dataKey="o3" stroke="#bc5090" name="O3" />

                        <Line type="monotone" dataKey="so2" stroke="#ffa600" name="SO2" />
                    </LineChart>
                </ResponsiveContainer>

            )}
        </div>
    );
}

export default HistoricalAirQuality;
