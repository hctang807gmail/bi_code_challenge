docker build -t broker_insights:code_challenge .
docker stop broker_insights
docker rm broker_insights
docker run -d --name broker_insights -e MYSQL_ROOT_PASSWORD=my-secret-pw -v %CD%/app/upload:/broker_insights/upload -v %CD%/app/config:/broker_insights/config broker_insights:code_challenge
#docker exec -it broker_insights /bin/bash
docker exec -it broker_insights /usr/bin/java -jar /broker_insights/codechallenge/target/codechallenge-1.0-SNAPSHOT-jar-with-dependencies.jar
docker stop broker_insights
docker rm broker_insights