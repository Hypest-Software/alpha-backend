FROM openjdk:11
VOLUME /tmp
ENV JAVA_OPTIONS="-Dspring.profiles.active=production"
ADD build/libs/backend.jar /etc/spring/backend.jar
ENTRYPOINT java $JAVA_OPTIONS -jar /etc/spring/backend.jar
