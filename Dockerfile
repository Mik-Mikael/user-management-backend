FROM gradle:jdk22 AS build
WORKDIR /app

# Copy build files
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew ./
# COPY gradle.properties ./

# Make gradlew executable
RUN chmod +x gradlew

# Pre-download dependencies
RUN ./gradlew build -x test --no-daemon || return 0

# Copy source code
COPY . .

# Build the app
RUN ./gradlew bootJar -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:24-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]