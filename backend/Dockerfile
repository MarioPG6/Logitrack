# ========================
# Etapa 1: Build
# ========================
FROM maven:3.9.7-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -B -e -C dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# ========================
# Etapa 2: Runtime
# ========================
FROM eclipse-temurin:21.0.3-jre-alpine


RUN apk update && apk upgrade --no-cache

# Crear usuario no-root
RUN addgroup -S app && adduser -S app -G app

WORKDIR /app

# Copiar solo el JAR
COPY --from=build /app/target/*.jar app.jar

# Permisos mínimos necesarios
RUN chown -R app:app /app

# Ejecutar como usuario no-root
USER app

EXPOSE 8080

ENTRYPOINT ["java", \
  "-XX:MaxRAMPercentage=75", \
  "-XX:+UseContainerSupport", \
  "-XX:+ExitOnOutOfMemoryError", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", \
  "app.jar"]
