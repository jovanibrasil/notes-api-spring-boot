stop:
	- docker stop notes-api
clean: stop
	- docker rm notes-api
build: clean
	mvn clean package
	docker build --build-arg NOTES_MONGO_URL -t notes-api .
run: clean
	docker run -d -p 8082:8080 --name=notes-api --network net notes-api
start: stop
	docker start notes-api
bash:
	docker container exec -i -t --user root notes-api bash
logs:
	docker logs notes-api
compose-down:
	docker-compose down -v
compose-up: compose-down
	docker-compose up -d
	