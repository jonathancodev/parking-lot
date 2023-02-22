FROM maven:3.6.3-jdk-11-slim
WORKDIR /app
COPY . /app
EXPOSE 8080
RUN mvn clean package
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Xms2048m -Xmx4096m", "-Dspring-boot.run.arguments=--custom.spots.motorcycle=10 --custom.spots.car=10 --custom.spots.van=10"]