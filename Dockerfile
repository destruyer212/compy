FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:17-jre-jammy
RUN groupadd --system celucheck && useradd --system --gid celucheck --home-dir /app celucheck
WORKDIR /app
COPY --from=build /workspace/target/celucheck-*.jar app.jar

USER celucheck
EXPOSE 10000
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:+UseSerialGC"
ENTRYPOINT ["sh", "-c", "db_endpoint=${DATABASE_URL##*@}; db_host_port=${db_endpoint%%/*}; case $db_host_port in *:*) export DB_HOST=${db_host_port%%:*}; export DB_PORT=${db_host_port#*:} ;; *) export DB_HOST=$db_host_port; export DB_PORT=5432 ;; esac; exec java $JAVA_OPTS -jar app.jar"]
