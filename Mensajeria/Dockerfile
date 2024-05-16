# Usa una imagen base de Maven para construir el proyecto
FROM maven:3.8.4-openjdk-11 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo pom.xml para descargar las dependencias
COPY pom.xml .

# Descarga las dependencias del proyecto
RUN mvn dependency:go-offline

# Copia todo el código fuente al directorio de trabajo
COPY src ./src

# Compila la aplicación
RUN mvn package -DskipTests

# Usa una imagen base de OpenJDK para ejecutar la aplicación
FROM openjdk:11-jre-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación construida en la etapa anterior
COPY --from=build /app/target/Mensajeria-1.0-SNAPSHOT.jar .

# Instala RabbitMQ
RUN apt-get update && \
    apt-get install -y rabbitmq-server && \
    apt-get clean

# Expone el puerto en el que tu aplicación escucha (ajústalo según tu aplicación)
EXPOSE 8080

# Comando de inicio para ejecutar tu aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "Mensajeria-1.0-SNAPSHOT.jar"]
