FROM tomcat
LABEL maintainer="jovanibrasil@gmail.com"
USER root

RUN apt-get -y update && apt-get -y install netcat
#COPY dist/ /app
ARG NOTES_MONGO_URL
ARG ENVIRONMENT
ARG VAULT_TOKEN

ENV NOTES_MONGO_URL=$NOTES_MONGO_URL
ENV ENVIRONMENT=$ENVIRONMENT
ENV FILE_NAME=${FILE_NAME}
ENV VAULT_TOKEN=$VAULT_TOKEN

COPY ./target/${FILE_NAME} /usr/local/tomcat/webapps/${FILE_NAME}
COPY ./scripts ./scripts
RUN if [ "$ENVIRONMENT" = "stage" ]; \
	then cp ./scripts/startup-dev.sh /startup.sh; \
	else cp ./scripts/startup-prod.sh /startup.sh;\
	fi
RUN rm ./scripts -rf
EXPOSE 8082

CMD ["/bin/bash", "/startup.sh"]
