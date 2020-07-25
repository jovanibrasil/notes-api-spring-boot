FROM tomcat:9.0.37-jdk11-openjdk
LABEL maintainer="jovanibrasil@gmail.com"
USER root

RUN apt-get -y update && apt-get -y install netcat
ARG ENVIRONMENT
ARG AUTH_API_URL
ARG NOTES_API_PASSWORD
ARG NOTES_API_USERNAME
ARG NOTES_DB_URL
ARG FILE_NAME

ENV ENVIRONMENT=$ENVIRONMENT
ENV AUTH_API_URL=$AUTH_API_URL
ENV NOTES_API_PASSWORD=$NOTES_API_PASSWORD
ENV NOTES_API_USERNAME=$NOTES_API_USERNAME
ENV NOTES_DB_URL=$NOTES_DB_URL
ENV FILE_NAME=$FILE_NAME

COPY ./target/${FILE_NAME} /usr/local/tomcat/webapps/${FILE_NAME}
COPY ./scripts ./scripts
RUN if [ "$ENVIRONMENT" = "dev" ]; \
	then cp ./scripts/startup-dev.sh /startup.sh; \
	else cp ./scripts/startup-prod.sh /startup.sh;\
	fi
RUN rm ./scripts -rf
EXPOSE 8082

RUN ["chmod", "+x", "/startup.sh"]
CMD ["sh", "-c", "/startup.sh $DBSERVICENAME $DBSERVICEPORT $AUTHSERVICENAME $AUTHSERVICEPORT"]