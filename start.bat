docker build -t broker_insights:code_challenge .
docker stop broker_insights
docker rm broker_insights
#docker run -d --name broker_insights -e MYSQL_ROOT_PASSWORD=my-secret-pw -v %CD%/mysql/data:/var/lib/mysql -v %CD%/mysql/docker-entrypoint-initdb.d:/docker-entrypoint-initdb.d broker_insights:code_challenge
docker run -d --name broker_insights -e MYSQL_ROOT_PASSWORD=my-secret-pw -v %CD%/app:/broker_insights -v %CD%/mysql/docker-entrypoint-initdb.d:/docker-entrypoint-initdb.d broker_insights:code_challenge