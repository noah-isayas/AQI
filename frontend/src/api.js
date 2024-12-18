const API_BASE_URL = 'http://localhost:8000';
// Fetch real-time air quality data
export const fetchRealTimeAirQuality = async (latitude, longitude, location) => {
    try {
        let url = `${API_BASE_URL}/air-quality?`;
        if (location) {
            url += `location=${encodeURIComponent(location)}`;
        } else if (latitude && longitude) {
            url += `lat=${latitude}&lon=${longitude}`;
        } else {
            throw new Error('Either location or coordinates (latitude, longitude) must be provided');
        }

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Error fetching real-time air quality: ${response.statusText}`);
        }

        return await response.json();
    } catch (error) {
        console.error('fetchRealTimeAirQuality:', error.message);
        throw error; // Re-throw for handling in frontend
    }

};

export const fetchStoredData = async () => {
    try {
        const response = await fetch(`${API_BASE_URL}/api/history/stored-data`);
        if (!response.ok) {
            throw new Error(`Error fetching stored data: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error('fetchStoredData:', error.message);
        throw error;
    }
};
// Fetch historical air quality data
export const fetchHistoricalAirQuality = async (latitude, longitude, location) => {
    try {
        let url = `${API_BASE_URL}/air-quality/history?`;
        if (location) {
            url += `location=${encodeURIComponent(location)}`;
        } else if (latitude && longitude) {
            url += `lat=${latitude}&lon=${longitude}`;
        } else {
            throw new Error('Either location or coordinates must be provided for historical data.');
        }

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Error fetching historical air quality: ${response.statusText}`);
        }

        return await response.json();
    } catch (error) {
        console.error('fetchHistoricalAirQuality:', error.message);
        throw error;
    }
};

// Subscribe to notifications
export const subscribeToNotifications = async (email) => {
    try {
        if (!email) {
            throw new Error('Email is required for subscription');
        }

        const response = await fetch(`${API_BASE_URL}/notification/subscribe`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email }),
        });

        if (!response.ok) {
            throw new Error(`Error subscribing to notifications: ${response.statusText}`);
        }

        return await response.json();
    } catch (error) {
        console.error('subscribeToNotifications:', error.message);
        throw error;
    }
};