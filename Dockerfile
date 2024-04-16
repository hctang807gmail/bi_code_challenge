FROM mysql:8.0-debian

RUN apt-get -y update && apt-get -y install openjdk-17-jdk maven

COPY app /broker_insights

COPY mysql/docker-entrypoint-initdb.d /docker-entrypoint-initdb.d

RUN cd /broker_insights/codechallenge && mvn package

