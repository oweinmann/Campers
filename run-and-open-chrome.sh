#!/bin/bash

# Kill any existing Spring Boot process on port 8080
lsof -ti:8080 | xargs kill -9 2>/dev/null

# Start the Spring Boot application in the background
mvn spring-boot:run &

# Wait for the application to start
echo "Waiting for application to start..."
sleep 10

# Open Chrome
open -a "Google Chrome" http://localhost:8080

echo "Application started and Chrome opened!"
echo "To stop the application, use: lsof -ti:8080 | xargs kill"
