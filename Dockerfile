FROM tomcat
LABEL maintainer="jovanibrasil@gmail.com"
USER root
	RUN apt-get -y update && apt-get -y install netcat
    #COPY dist/ /app
	ARG NOTES_MONGO_URL
	ENV NOTES_MONGO_URL=$NOTES_MONGO_URL
    COPY ./target/notes-api.war /usr/local/tomcat/webapps/notes-api.war
    #COPY ./target/notes-api /usr/local/tomcat/webapps/notes-api
    COPY ./scripts/startup.sh /
    EXPOSE 8082

	CMD ["/bin/bash", "/startup.sh"]

#USER jenkins
