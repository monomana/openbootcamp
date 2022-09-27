FROM openjdk:11
EXPOSE 8080
ENV JAVA_OPTS="-Xss256k"
ADD target/ranti-main-1.1.0.jar ranti-main-1.1.0.jar
ENTRYPOINT ["java","-jar","/ranti-main-1.1.0.jar"]