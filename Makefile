stop:
	- docker stop notes-api
clean: stop
	- docker rm notes-api
build: clean
	mvn clean package -DskipTests
	docker build --build-arg NOTES_MONGO_URL --build-arg ENVIRONMENT=dev -t notes-api .
	chmod -R ugo+rw target/
run: clean
	docker run -d -p 8082:8080 -m 128m --memory-swap 256m -e "SPRING_PROFILES_ACTIVE=dev" --name=notes-api --network net notes-api
start: stop
	docker start notes-api
bash:
	docker container exec -i -t --user root notes-api bash
logs:
	docker logs notes-api
compose-down:
	docker-compose down -v
compose-up: compose-down
	docker-compose up --no-recreate -d
	
deploy-production:
	/bin/sh scripts/deploy.sh VAULT_TOKEN=${VAULT_TOKEN} SPRING_PROFILES_ACTIVE=${PROFILE}

	
