# Usa una imagen base de Tomcat
FROM tomcat:9.0-jre11-slim

# Copia el archivo WAR de la aplicación web desde la carpeta dist al directorio webapps de Tomcat
COPY dist/SecretariaSaludClient.war /usr/local/tomcat/webapps/

# Expone el puerto en el que Tomcat está ejecutando tu aplicación
EXPOSE 8080

# Comando de inicio para iniciar Tomcat cuando se ejecute el contenedor
CMD ["catalina.sh", "run"]
