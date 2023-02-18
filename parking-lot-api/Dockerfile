FROM maven:3.6.3-jdk-8-slim
WORKDIR /app
COPY . /app
EXPOSE 8080
RUN mvn clean package
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Xms1024m -Xmx2048m"]