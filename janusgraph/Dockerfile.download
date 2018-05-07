FROM phusion/baseimage:latest
LABEL maintainer "Sylvain Corsini <sylvain.corsini@pm.me>"

ENV BUILD_DEPS="unzip wget"
ENV RUN_DEPS="openjdk-8-jre"

CMD ["/sbin/my_init"]

RUN echo "Installing installation dependencies" && \
  apt-get -qq update && \
  apt-get -qq install -y $BUILD_DEPS --no-install-recommends > /dev/null && \
  echo "Starting installation" && \
  mkdir /viny && \
  cd /viny && \
  echo "Downloading JanusGraph archive" && \
  wget -q https://github.com/JanusGraph/janusgraph/releases/download/v0.2.0/janusgraph-0.2.0-hadoop2.zip -O /tmp/janusgraph.zip && \
  echo "Unzipping JanusGraph archive" && \
  unzip -qq /tmp/janusgraph.zip && \
  mv janusgraph-0.2.0-hadoop2 janusgraph && \
  echo "Installing running dependencies" && \
  apt-get -qq install -y $RUN_DEPS --no-install-recommends > /dev/null && \
  echo "Cleaning" && \
  apt-get -qq purge -y --auto-remove $BUILD_DEPS > /dev/null && \
  apt-get -qq clean > /dev/null && \
  rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* /root/.m2/

EXPOSE 8182

COPY conf /viny/janusgraph/conf/gremlin-server

HEALTHCHECK --interval=1m --timeout=5s --retries=10 \
  CMD curl -f elasticsearch:9200/_cluster/health?wait_for_status=yellow \
    || exit 1

CMD ["/viny/janusgraph/bin/gremlin-server.sh"]
