FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY target/payment-1.0.0.jar payment-1.0.0.jar

EXPOSE 8083
CMD ["java","-jar","payment-1.0.0.jar"]