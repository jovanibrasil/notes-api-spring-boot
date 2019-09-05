FROM tomcat
LABEL maintainer="jovanibrasil@gmail.com"
USER root
	RUN apt-get -y update && apt-get -y install netcat
    #COPY dist/ /app
	ARG NOTES_MONGO_URL
	ARG ENVIRONMENT
	ENV NOTES_MONGO_URL=$NOTES_MONGO_URL
	ENV ENVIRONMENT=$ENVIRONMENT
    COPY ./target/notes-api.war /usr/local/tomcat/webapps/notes-api.war
    COPY ./scripts ./scripts
    RUN if [ "$ENVIRONMENT" = "dev" ]; \
    	then cp ./scripts/startup-dev.sh /startup.sh; \
    	else cp ./scripts/startup-prod.sh /startup.sh;\
    	fi
    RUN rm ./scripts -rf
    EXPOSE 8082

	CMD ["/bin/bash", "/startup.sh"]

#USER jenkins
