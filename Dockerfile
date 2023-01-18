FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the jar file to the container
COPY target/*.jar todo-app.jar

# Expose the service on port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","todo-app.jar"]
