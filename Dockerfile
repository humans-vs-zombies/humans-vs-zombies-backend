FROM gradle:jdk17 AS gradle
WORKDIR /app
COPY . .
RUN gradle bootJar

FROM openjdk:17 as runtime
WORKDIR /app
ENV PORT $SPRING_DOCKER_PORT
ENV SPRING_PROFILE production
ENV DATABASE_URL ""
COPY --from=gradle /app/build/libs/*.jar /app/app.jar
RUN chown -R 1000:1000 /app
USER 1000:1000
ENTRYPOINT ["java","-jar","-Dserver.port=${PORT}","-Dspring.profiles.active=${SPRING_PROFILE}", "-Dspring.datasource.url=jdbc:${DATABASE_URL}","app.jar"]