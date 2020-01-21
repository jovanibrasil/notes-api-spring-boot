run-tests:
	mvn clean test -Ptest

stop:
	- docker stop notes-api
clean: stop
	- docker rm notes-api
build: clean
	mvn clean package -DskipTests
	FILE_NAME=notes-api\#\#$(shell find target/*.war -type f | grep -Eo '[0-9]+)
	docker build --build-arg NOTES_MONGO_URL --build-arg FILE_NAME --build-arg ENVIRONMENT=stage -t notes-api .
	chmod -R ugo+rw target/
run: clean
	docker run -d -p 8082:8080 -m 128m --memory-swap 256m -e "SPRING_PROFILES_ACTIVE=stage" \
		-e VAULT_TOKEN=${VAULT_TOKEN} --name=notes-api --network net notes-api
start: stop
	docker start notes-api
bash:
	docker container exec -i -t --user root notes-api bash
logs:
	docker logs notes-api

compose-down:
	#docker network disconnect -f notes-api_net notes-api
	docker-compose down -v --remove-orphans

compose-up-dev: compose-down
	echo "PROFILE=dev" > .env
	docker-compose -f docker-compose.yml --compatibility up -d --no-recreate
	rm .env

compose-up-stage: compose-down
	echo "PROFILE=stage" > .env
	docker-compose -f docker-compose.yml -f docker-compose.stage.yml --compatibility up -d --no-recreate
	rm .env

deploy-production:
	/bin/sh scripts/deploy.sh VAULT_TOKEN=${VAULT_TOKEN} SPRING_PROFILES_ACTIVE=${PROFILE}

	
