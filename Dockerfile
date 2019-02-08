FROM tomcat
LABEL maintainer="jovanibrasil@gmail.com"
USER root
    #COPY dist/ /app

    COPY ./target/notes-api.war /usr/local/tomcat/webapps/notes-api.war
    #COPY ./target/notes-api /usr/local/tomcat/webapps/notes-api
    EXPOSE 8082

#USER jenkins
