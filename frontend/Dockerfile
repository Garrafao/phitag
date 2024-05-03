FROM debian:11

SHELL ["/bin/bash", "--login", "-c"]

# Install dependencies
RUN apt -y update && \
    apt -y install apt-transport-https ca-certificates curl gnupg2 software-properties-common python3 python3-pip openjdk-17-jre-headless maven

# Install Node.js and npm using nvm
# Install nvm
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.35.3/install.sh | bash && \
    export NVM_DIR="$HOME/.nvm" && \
    [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh" && \
    [ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"

# Install Node.js 18 using nvm
RUN /bin/bash -c "source /root/.bashrc && \
                 nvm install 18 && \
                 nvm use 18 && \
                 npm install -g npm && \
                 npm install -g pm2"

# Build backend

# Change working directory for frontend
WORKDIR /phitag/frontend

# Copy frontend source code
COPY . .

# Remove existing node_modules if any
RUN rm -rf package-lock.json
RUN rm -rf node_modules

# Install frontend dependencies
RUN /bin/bash -c "source /root/.bashrc && npm install"

# Expose ports for backend and frontend
EXPOSE 8080 3000

# Start backend and frontend
# Start backend and frontend using pm2
CMD /bin/bash -c "source /root/.bashrc && \
                  pm2 start 'npm run dev' --name frontend && \
                  pm2 save && \
                  pm2 logs"