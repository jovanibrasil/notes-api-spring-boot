FROM tomcat
LABEL maintainer="jovanibrasil@gmail.com"
USER root
    #COPY dist/ /app
	ARG NOTES_MONGO_URL
	ENV NOTES_MONGO_URL=$NOTES_MONGO_URL
    COPY ./target/notes-api.war /usr/local/tomcat/webapps/notes-api.war
    #COPY ./target/notes-api /usr/local/tomcat/webapps/notes-api
    EXPOSE 8082

#USER jenkins
