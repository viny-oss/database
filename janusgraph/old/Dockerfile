FROM phusion/baseimage:latest
LABEL maintainer "Sylvain Corsini <sylvain.corsini@pm.me>"

ENV BUILD_DEPS="maven git unzip openjdk-8-jdk"
ENV RUN_DEPS="openjdk-8-jre"

CMD ["/sbin/my_init"]

RUN echo "Installing installation dependencies" && \
  apt-get -qq update && \
  apt-get -qq install -y $BUILD_DEPS --no-install-recommends > /dev/null && \
  echo "Starting installation" && \
  echo "Clonning JanusGraph sources" && \
  git clone -q https://github.com/JanusGraph/janusgraph.git /tmp/janusgraph && \
  cd /tmp/janusgraph && \
  git checkout -q tags/v0.2.0 && \
  echo "Compiling JanusGraph"
  mvn -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
     -T $(getconf _NPROCESSORS_ONLN) clean install -Pjanusgraph-release -Dgpg.skip=true \
     -DskipTests=true && \
  mkdir /viny && \
  cd /viny && \
  echo "Unzipping JanusGraph archive" && \
  unzip -qq /tmp/janusgraph/janusgraph-dist/janusgraph-dist-hadoop-2/target/janusgraph-0.2.0-hadoop2.zip && \
  mv janusgraph-0.2.0-hadoop2 janusgraph && \
  echo "Installing running dependencies" && \
  apt-get -qq install -y $RUN_DEPS --no-install-recommends > /dev/null && \
  echo "Cleaning" && \
  apt-get -qq purge -y --auto-remove $BUILD_DEPS > /dev/null && \
  apt-get -qq clean > /dev/null && \
  rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* /root/.m2/

EXPOSE 8182

COPY conf /viny/janusgraph/conf/gremlin-server

CMD ["/viny/janusgraph/bin/gremlin-server.sh"]
