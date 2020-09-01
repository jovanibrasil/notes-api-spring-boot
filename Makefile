PKG_VERSION_PATH := "./src/main/resources/buildNumber.properties"
LAST_VERSION := $(shell (grep buildNumber= | cut -d= -f2) < $(PKG_VERSION_PATH))
$(eval VERSION=$(shell echo $$(($(LAST_VERSION)+1))))

run-tests:
	mvn clean test -Ptest
stop:
	- docker stop notes-api
clean: stop
	- docker rm notes-api
build: clean
	mvn clean package -DskipTests
	docker build --build-arg VERSION=$(VERSION) --build-arg ENVIRONMENT=dev -t notes-api .
	chmod -R ugo+rw target/
run: clean
	docker run -m 256m --memory-swap 512m --env-file ./.env  \
		 -e JAVA_OPTS='-XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m' --name=notes-api --network net notes-api
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
