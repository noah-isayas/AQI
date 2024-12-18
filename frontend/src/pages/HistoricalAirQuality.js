import React, { useState } from 'react';
import { fetchHistoricalAirQuality } from '../api';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import TopBar from '../components/TopBar';
import './HistoricalAirQuality.css';

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
            <TopBar /> {/* Add TopBar here */}
            <div className="historical-container">
                <h1>Historical Air Quality</h1>
                <p>View historical air quality trends for your selected location.</p>
                <div className="search-mode-buttons">
                    <button onClick={() => setSearchMode('coordinates')}>Search by Coordinates</button>
                    <button onClick={() => setSearchMode('location')}>Search by Location Name</button>
                </div>
                {searchMode === 'coordinates' && (
                    <div className="input-fields">
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
                    <div className="input-fields">
                        <input
                            type="text"
                            value={location}
                            onChange={(e) => setLocation(e.target.value)}
                            placeholder="Enter location name"
                        />
                    </div>
                )}
                <button className="fetch-button" onClick={handleFetchData}>Fetch Historical Data</button>
                {error && <p className="error-message">{error}</p>}
                {data.length > 0 && (
                    <div className="chart-container">
                        <ResponsiveContainer width="100%" height={400}>
                            <LineChart data={data}>
                                <CartesianGrid strokeDasharray="3 3" />
                                <XAxis dataKey="timestamp" />
                                <YAxis label={{ value: 'Values', angle: -90, position: 'insideLeft' }} />
                                <Tooltip />
                                <Line type="monotone" dataKey="aqi" stroke="#8884d8" activeDot={{ r: 8 }} name="AQI" />
                                <Line type="monotone" dataKey="pm2_5" stroke="#82ca9d" name="PM2.5" />
                                <Line type="monotone" dataKey="no" stroke="#387908" name="NO" />
                                <Line type="monotone" dataKey="no2" stroke="#003f5c" name="NO2" />
                                <Line type="monotone" dataKey="o3" stroke="#bc5090" name="O3" />
                                <Line type="monotone" dataKey="so2" stroke="#ffa600" name="SO2" />
                            </LineChart>
                        </ResponsiveContainer>
                    </div>
                )}
            </div>
        </div>
    );
}

export default HistoricalAirQuality;
