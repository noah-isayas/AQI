const API_BASE_URL = 'http://localhost:8000';

export const fetchRealTimeAirQuality = async (latitude, longitude) => {
    const response = await fetch(`${API_BASE_URL}/air-quality?lat=${latitude}&lon=${longitude}`);
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return response.json();
};

export const fetchHistoricalAirQuality = async (location) => {
    const response = await fetch(`${API_BASE_URL}/api/history?location=${location}`);
    return response.json();
};

export const subscribeToNotifications = async (email) => {
    const response = await fetch(`${API_BASE_URL}/notification/subscribe`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email }),
    });
    return response.json();
};