FROM ubuntu

ARG TC_AGENT_ZIP="https://teamcity.frundh.local/update/buildAgent.zip"

ENV JRE_HOME "/usr/lib/jvm/java-8-openjdk-amd64/"
ENV CONFIG_FILE "/data/teamcity_agent/conf/buildAgent.properties"

VOLUME /data/teamcity_agent/conf

COPY run-agent.sh /run-agent.sh
COPY run-services.sh /run-services.sh

RUN set -ex; \ 
    apt-get update && apt-get install -y --no-install-recommends \  
        ca-certificates curl git openssh-client openjdk-8-jre-headless \ 
        bsdtar gnupg-agent apt-transport-https software-properties-common; \
    #
    # Install Docker CE CLI
    curl -fsSL https://download.docker.com/linux/$(lsb_release -is | tr '[:upper:]' '[:lower:]')/gpg | (OUT=$(apt-key add - 2>&1) || echo $OUT); \ 
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/$(lsb_release -is | tr '[:upper:]' '[:lower:]') $(lsb_release -cs) stable"; \ 
    apt-get update; apt-get install -y --no-install-recommends docker-ce-cli; \ 
    #
    # Install Docker Compose
    curl -SL https://github.com/docker/compose/releases/download/1.20.1/docker-compose-Linux-x86_64 -o /usr/local/bin/docker-compose; \ 
    chmod +x /usr/local/bin/docker-compose; \ 
    #
    # Install TeamCity Agent
    mkdir -p /opt/buildagent/; curl -SL ${TC_AGENT_ZIP} | bsdtar -xf- -C /opt/buildagent/; \ 
    useradd -m buildagent; \ 
    chmod +x /run-agent.sh /run-services.sh && sync; \  
    #
    # Clean up
    apt-get remove -y --purge bsdtar gnupg-agent apt-transport-https software-properties-common; \ 
    apt-get autoremove -y; apt-get clean -y; rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*; 

CMD ["/run-services.sh"]

EXPOSE 9090