FROM node:14.5.0-alpine3.10

COPY ./index.js /home/node/app/
COPY ./package.json /home/node/app/

WORKDIR /home/node/app/
RUN npm install