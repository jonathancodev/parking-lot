# Parking Lot UI

## Run without Docker
### Install Node.js
- Install Node.js 14.18.1 or above in your PC

### Steps to run the application
- Run this command to install the dependencies: `npm install`
- Run this command to start the application: `npm start`

Check if the application is running accessing: `http://localhost:3000`

## Run with Docker
### Install Docker
- Install Docker in your PC

### Build image
- `docker build -t parking-lot-ui .`

### Run the app on container
- `docker run -p 3000:3000 --name parking-lot-ui -d parking-lot-ui`

Check if the application is running accessing: `http://localhost:3000`