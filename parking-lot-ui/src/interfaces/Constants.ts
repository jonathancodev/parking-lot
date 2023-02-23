export default {
    API_URL: process.env.REACT_APP_API_URL || 'http://localhost:8080/api',
    HEADERS: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    }
};