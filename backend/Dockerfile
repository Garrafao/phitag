FROM debian:11

SHELL ["/bin/bash", "--login", "-c"]

# Install dependencies
RUN apt -y update && \
    apt -y install apt-transport-https ca-certificates curl gnupg2 software-properties-common python3 python3-pip openjdk-17-jre-headless maven

# Set up working directory for backend
WORKDIR /phitag/backend

# Copy backend pom.xml
COPY . .

RUN rm -rf target
RUN rm -rf ./src/main/java/resource/application.properties
RUN rm -rf ./src/main/java/resource/db.changelog/db.changelog-master.xml
# Copy backend source code
#COPY ./src ./src


# Expose port for backend
EXPOSE 8080

