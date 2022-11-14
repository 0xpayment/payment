FROM arm64v8/openjdk:11-oraclelinux8
ARG JAR_FILE=build/libs/Payment-0.0.1-SNAPSHOT.jar
RUN mkdir applib
COPY ${JAR_FILE} /applib/app.jar
COPY libs/*.jar /applib/
ENTRYPOINT ["java","-jar","/applib/app.jar", "--spring.config.location=classpath:/application-opc.properties"]
