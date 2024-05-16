# Usa una imagen base de MySQL
FROM mysql:latest

# Establece la contraseña para el usuario root
ENV MYSQL_ROOT_PASSWORD=Blaziquen_01

# Establece el nombre de la base de datos
ENV MYSQL_DATABASE=secretariadesalud

# Copia el script de inicialización SQL para crear la base de datos y las tablas
COPY init.sql /docker-entrypoint-initdb.d/

# Expone el puerto en el que MySQL está escuchando
EXPOSE 3306