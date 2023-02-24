# Parking Lot UI

## Run without Docker
### Install Node.js
- Install Node.js 14.18.1 or above in your PC

The default value of the environment variable REACT_APP_API_URL is http://localhost:8080/api.
In order to change that value you should rename or copy the .env.sample to .env and replace the value, for example: REACT_APP_API_URL=http://localhost:8000/api
You have to do that also if you would run with Docker.

### Steps to test the application
- Run this command to install the dependencies: `npm install`
- Run this command to start the application: `npm run test`

### Steps to build the application
- Run this command to install the dependencies: `npm install`
- Run this command to start the application: `npm run build`

### Steps to run the application
- Run this command to install the dependencies: `npm install`
- Run this command to start the application: `npm run start`

### Steps to run the application for production environment
- Run this command to install the dependencies: `npm install`
- Run this command to start the application: `npm run start:production`

Check if the application is running accessing: `http://localhost:3000`

## Run with Docker
### Install Docker
- Install Docker in your PC

### Build image
- `docker build -t parking-lot-ui .`

### Run the app on container
- `docker run -p 3000:3000 --name parking-lot-ui -d parking-lot-ui`

Check if the application is running accessing: `http://localhost:3000`