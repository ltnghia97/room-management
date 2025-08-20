# 1. Build ứng dụng Spring Boot
./gradlew clean build

# 2. Build Docker image
docker build -t spring-boot-docker-rentalroom .

# 3. Chạy container với port mapping
docker run -p 9998:9998 --name rental-room-container spring-boot-docker-rentalroom

# 4. Test API
curl --location 'http://localhost:9998/v1/api/hello'