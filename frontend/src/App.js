import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LandingPage from './components/LandingPage';
import RealTimeAirQuality from './pages/RealTimeAirQuality';
import HistoricalAirQuality from './pages/HistoricalAirQuality';
import Notifications from './pages/Notifications';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/" element={<LandingPage />} />
                    <Route path="/fetch-airquality" element={<RealTimeAirQuality />} />
                    <Route path="/historical-view" element={<HistoricalAirQuality />} />
                    <Route path="/saved-data" element={<Notifications />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;