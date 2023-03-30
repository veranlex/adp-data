FROM openjdk:17-jdk-alpine


ENV JAVA_OPTS=$JAVA_OPTS

COPY build/libs/ibmmqconsumer-*.jar /app/app.jar
WORKDIR /app


CMD java ${JAVA_OPTS} -jar /app/app.jar