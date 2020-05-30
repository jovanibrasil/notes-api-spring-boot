CURRENT_DIR=$(shell pwd)

stop:
	- docker stop mongo-database
clean: stop
	- docker rm mongo-database
clean-data:  clean
	- docker volume rm mongo-database-data
create-data: clean-data
	docker volume create mongo-database-data
build: clean
	docker build -t mongo-database .
run: clean 
	docker run -p 27017:27017 -p 28017:28017 --name=mongo-database --mount source=mongo-database-data,target=/data/db -d mongo-database
start:
	docker start mongo-database
bash:
	docker container exec -i -t --user root mongo-database bash

#  mongo --username admin --password --authenticationDatabase notesdb  mongodb://localhost:27017

# Run mongo on port 27017
default-mongo:
	- docker stop mongo
	- docker rm mongo
	docker run -p 27017:27017 --name=mongo -v ${CURRENT_DIR}/data:/data/db -d mongo 