# 1st stage, build the app
FROM registry.hub.docker.com/library/maven:3.6.1-jdk-11-slim as build

WORKDIR /app

# Create a first layer to cache the "Maven World" in the local repository.
# Incremental docker builds will always resume after that, unless you update
# the pom
ADD pom.xml .
RUN mvn package -DskipTests

# Do the Maven build!
# Incremental docker builds will resume here when you change sources
ADD src src
RUN mvn package -DskipTests
RUN echo "done!"

# 2nd stage, build the runtime image
FROM registry.hub.docker.com/adoptopenjdk/openjdk11-openj9:alpine-slim
RUN apk add --no-cache redis sed bash busybox-suid && addgroup -S remitpulse && adduser -S remitpulse -G remitpulse && mkdir -p /home/remitpulse/app

# Copy the binary built in the 1st stage
COPY --from=build /app/target/remitpulse-countries.jar /home/remitpulse/app/
COPY --from=build /app/target/libs /home/remitpulse/app/libs
RUN  chown -Rf remitpulse:remitpulse /home/remitpulse/app
USER remitpulse:remitpulse
WORKDIR /home/remitpulse/app

ENV JWT_KEY="RHQ0UkhqanFqVlhwTzlqZ0R0NFJIampxalZYcE85amcK"
ENV ORIGINATOR="dev"
ENV APP_VERSION="2.0.0"
ENV JWT_TOKEN_EXPIRE="10"

ENV DB_USER_NAME="developer"
ENV DB_PASSWORD="developer@123"
ENV DB_URL="jdbc:postgresql://172.16.128.14:5432/remitpulse?stringtype=unspecified"

ENV DEPLOYMENT_NAMESPACE="remitpulse-dev"
ENV GATEWAY_URL="https://remitpulse.citytech.global/remitpulse/web-api"
ENV MASTER_SERVICE_URL="https://remit-dev.citytech.global/admin"
ENV COUNTRY_SERVICE_URL="https://remit-dev.citytech.global/admin"
ENV CURRENCY_SERVICE_URL="https://remit-dev.citytech.global/admin"
ENV HUB_SERVICE_URL="https://remit-dev.citytech.global/admin"
ENV AUTH_SERVICE_URL="https://remit-dev.citytech.global/admin"
ENV PARTNER_SERVICE_URL="https://remit-dev.citytech.global/admin"
ENV IDENTITY_SERVICE_URL="https://remit-dev.citytech.global/admin"
ENV MODULE_CONFIG_SERVICE_URL="https://remit-dev.citytech.global/admin"
ENV CUSTOMFIELD_SERVICE_URL="https://remit-dev.citytech.global/admin"
CMD ["java", "-jar", "remitpulse-countries.jar"]

EXPOSE 8080