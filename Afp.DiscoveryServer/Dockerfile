FROM fabric8/java-alpine-openjdk8-jdk
VOLUME /tmp
COPY target/discovery-*.jar app.jar
EXPOSE 8761
## Add the wait script to the image
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.4.0/wait /wait
RUN chmod +x /wait
ENTRYPOINT /wait && java -Xmx$MEM_USAGE -Djava.security.egd=file:/dev/./urandom -jar /app.jar