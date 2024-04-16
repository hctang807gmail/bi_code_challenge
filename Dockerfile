FROM mysql:8.0-debian

RUN apt-get -y update && apt-get -y install openjdk-17-jdk maven

