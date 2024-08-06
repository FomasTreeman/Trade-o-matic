FROM gitpod/workspace-full

USER gitpod

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk list maven && \
    sdk install java 22.0.1-amzn && \
    sdk default java 22.0.1-amzn"