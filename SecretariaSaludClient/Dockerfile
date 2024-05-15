# Usa una imagen base de Tomcat
FROM tomcat:latest

# Elimina la aplicación de ejemplo predeterminada de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia tu archivo WAR de la aplicación web al directorio webapps de Tomcat
COPY SecretariaSaludClient.war /usr/local/tomcat/webapps/

# Expone el puerto en el que Tomcat está ejecutando tu aplicación
EXPOSE 8080

# Comando de inicio para iniciar Tomcat cuando se ejecute el contenedor
CMD ["catalina.sh", "run"]
