# Stage 1: Build stage
FROM gradle:8.6-jdk17 AS builder

WORKDIR /app

# Copy gradle files
COPY build.gradle.kts settings.gradle.kts gradlew gradlew.bat ./
COPY gradle ./gradle

# Copy source code
COPY src ./src

# Build the project
RUN ./gradlew build -x test --no-daemon

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Install curl for health checks (optional)
RUN apk add --no-cache curl

# Create necessary directories
RUN mkdir -p /app/mp3Song /app/user-images

# Copy the built JAR from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Create a user to run the application (security best practice)
RUN addgroup -g 1000 appuser && \
    adduser -D -u 1000 -G appuser appuser && \
    chown -R appuser:appuser /app

USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/hello || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
