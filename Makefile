run-tests:
	mvn clean test -Ptest
stop:
	- docker stop notes-api
clean: stop
	- docker rm notes-api
build: clean
	mvn clean package -DskipTests
	FILE_NAME=notes-api\#\#$(shell find target/*.war -type f | grep -Eo '[0-9]+)
	docker build --build-arg FILE_NAME --build-arg ENVIRONMENT=dev -t notes-api .
	chmod -R ugo+rw target/
run: clean
	docker run -d -p 8082:8080 -m 256m --memory-swap 256m --env-file ./.env  \
		 --name=notes-api --network net notes-api
start: stop
	docker start notes-api
bash:
	docker container exec -i -t --user root notes-api bash
logs:
	docker logs notes-api

compose-down:
	docker-compose down -v --remove-orphans

compose-up-dev: compose-down
	docker-compose -f docker-compose.yml --compatibility up -d --no-recreate
	
notes-api-bash:
	docker container exec -i -t --user root notes-api bash

notes-api-logs:
	docker logs notes-api

mongo-bash:
	docker container exec -i -t --user root mongo-database bash

heroku-maven-deploy:
	mvn clean heroku:deploy-war -Pprod -Dmaven.test.skip=true
	chmod -R ugo+rw target/

heroku-logs:
	heroku logs --app=jb-notes-api
