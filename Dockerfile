FROM tomcat:9.0.37-jdk11-openjdk
LABEL maintainer="jovanibrasil@gmail.com"
USER root

RUN apt-get -y update && apt-get -y install netcat
ARG ENVIRONMENT
ARG AUTH_API_URL
ARG NOTES_API_PASSWORD
ARG NOTES_API_USERNAME
ARG NOTES_DB_URL
ARG VERSION

ENV ENVIRONMENT=$ENVIRONMENT
ENV AUTH_API_URL=$AUTH_API_URL
ENV NOTES_API_PASSWORD=$NOTES_API_PASSWORD
ENV NOTES_API_USERNAME=$NOTES_API_USERNAME
ENV NOTES_DB_URL=$NOTES_DB_URL

COPY ./target/notes-api##${VERSION}.war /usr/local/tomcat/webapps/ROOT##${VERSION}.war
COPY ./target/notes-api##${VERSION} /usr/local/tomcat/webapps/ROOT##${VERSION}

COPY ./scripts ./scripts
RUN if [ "$ENVIRONMENT" = "dev" ]; \
	then cp ./scripts/startup-dev.sh /startup.sh; \
	else cp ./scripts/startup-prod.sh /startup.sh;\
	fi
RUN rm ./scripts -rf
EXPOSE 8080

RUN ["chmod", "+x", "/startup.sh"]
CMD ["sh", "-c", "/startup.sh $DBSERVICENAME $DBSERVICEPORT $AUTHSERVICENAME $AUTHSERVICEPORT"]