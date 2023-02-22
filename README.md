# Parking Lot Application

Goals:
- Design a parking lot API and dashboard
  Here are a few endpoints that you should implement:
- Tell us how many spots are remaining
- Tell us when the parking lot is full
- Tell us how many spots vans are taking up
- Take in a vehicle to park
- Remove a vehicle from the lot
  
Assumptions:
- The parking lot is a single row of spots
- The parking lot can hold motorcycles, cars, and vans
- The parking lot has motorcycle spots, car spots, and van spots
- A motorcycle can park in any spot
- A car can park in a single-car spot or a van spot
- A van can park a van spot or car spot, but it will take up 3 adjacent car spots

## Parking Lot API

The application API is storing all data in memory, therefore when you stop the application all the data will be lost.

### Run without Docker
#### Install JDK
- Install Java 11 or above in your PC

#### Go to working folder
- `cd parking-lot-api`

#### Steps to build the application API
- Build using pre-install maven on Linux: `./mvnw clean package`
- Build using pre-install maven on Windows: `mvnw.cmd clean package`
- Build using your maven install: `mvn clean package`
- The build package will be available in this path: `./target/parking-lot-api-0.0.1.jar`

#### Steps to run the application API
- Run using pre-install maven on Linux: `./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xms1024m -Xmx2048m"`
- Run using pre-install maven on Windows: `mvnw.cmd spring-boot:run -Dspring-boot.run.jvmArguments="-Xms1024m -Xmx2048m"`
- Run using your maven install: `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xms1024m -Xmx2048m"`

The application API by default supports at max 2048mb of memory usage.
In order to increase the limit you must increase also the JVM memory when you run the application, for example: `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xms2048m -Xmx4096m"`

Also by default, the application API has 1 Motorcycle spot, 3 Car spot and 1 Van spot.
In order to change those values, you should pass the arguments when you run the application, for example: 
`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xms2048m -Xmx4096m" -Dspring-boot.run.arguments="--custom.spots.motorcycle=10 --custom.spots.car=10 --custom.spots.van=10"`

Check if the API is running accessing: `http://localhost:8080/api/`

### Run with Docker
#### Install Docker
- Install Docker in your PC

#### Go to working folder
- `cd parking-lot-api`

#### Build image
- `docker build -t parking-lot-api .`

#### Run the app on container
- `docker run -p 8080:8080 --name parking-lot-api -d parking-lot-api`

The application API by default supports at max 2048mb of memory usage.
In order to increase the limit you must increase also the JVM memory on the Dockerfile before build the image of the application: `CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Xms2048m -Xmx4096m"]`

Also by default, the application API has 1 Motorcycle spot, 3 Car spot and 1 Van spot.
In order to change those values, you should pass the arguments on the Dockerfile before build the image of the application, for example:
`CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Xms2048m -Xmx4096m", "-Dspring-boot.run.arguments=--custom.spots.motorcycle=10 --custom.spots.car=10 --custom.spots.van=10"]`

Check if the API is running accessing: `http://localhost:8080/api/`

### Swagger documentation
- After starting the API you can check the API documentation on this link: `http://localhost:8080/api/swagger-ui/`