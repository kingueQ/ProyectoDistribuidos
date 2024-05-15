# Usa una imagen base de Java
FROM openjdk:latest

# Define el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
COPY SecretariaSaludServer.jar /app/

# Expone el puerto en el que tu aplicación escucha
EXPOSE 8080

# Comando de inicio para ejecutar tu aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "SecretariaSaludServer.jar"]

