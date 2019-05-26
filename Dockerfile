FROM java:8
WORKDIR /
ADD springimage-1.0-SNAPSHOT.jar springimage-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD java -jar springimage-1.0-SNAPSHOT.jar