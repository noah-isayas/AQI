import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import RealTimeAirQuality from './pages/RealTimeAirQuality';
import HistoricalAirQuality from './pages/HistoricalAirQuality';
import Notifications from './pages/Notifications';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/real-time" element={<RealTimeAirQuality />} />
                    <Route path="/historical" element={<HistoricalAirQuality />} />
                    <Route path="/notifications" element={<Notifications />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;