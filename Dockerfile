FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
ARG SKIP_TESTS=false
RUN if [ "$SKIP_TESTS" = "true" ]; then \
      ./gradlew --no-daemon clean bootJar -x test; \
    else \
      ./gradlew --no-daemon clean test bootJar; \
    fi

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
