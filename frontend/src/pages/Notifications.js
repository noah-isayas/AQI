import React, { useState } from 'react';
import { subscribeToNotifications } from '../api';

function Notifications() {
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');

    const handleSubscribe = async () => {
        const result = await subscribeToNotifications(email);
        setMessage(result.message);
    };

    return (
        <div>
            <h1>Notifications</h1>
            <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Enter your email"
            />
            <button onClick={handleSubscribe}>Subscribe</button>
            {message && <p>{message}</p>}
        </div>
    );
}

export default Notifications;