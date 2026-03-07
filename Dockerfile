# ==========================================
# ESTÁGIO 1: BUILD (Compilação)
# ==========================================
FROM eclipse-temurin:24-jdk-alpine AS build
LABEL authors="Ruan Rickelme"

# Ajuste 1: Definir a pasta de trabalho antes de copiar os arquivos
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

# ==========================================
# ESTÁGIO 2: RUN (Execução)
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Ajuste 2: Iniciar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]