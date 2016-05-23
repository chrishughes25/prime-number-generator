FROM java:8-jre
COPY config.yml /opt/dropwizard/
COPY target/pngweb-1.0.jar /opt/dropwizard/
EXPOSE 8080 8081
WORKDIR /opt/dropwizard
CMD ["java", "-jar", "-Done-jar.silent=true", "pngweb-1.0.jar", "server", "config.yml"]