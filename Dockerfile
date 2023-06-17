FROM eclipse-temurin:17-jdk AS build
WORKDIR /temp
COPY . /temp
RUN chmod +x ./gradlew
RUN ./gradlew clean test build

FROM eclipse-temurin:17-jre
EXPOSE 8080
COPY --from=build /temp/app/build/libs/*.jar app.jar
ENTRYPOINT ["java", \
"-Dspring.profiles.active=docker-local", \
"-jar", \
"/app.jar"]