FROM openjdk:8
MAINTAINER Amady <amady6.add@gmail.com>
VOLUME /tmp
COPY --from=build /ServerCommandHandler/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]