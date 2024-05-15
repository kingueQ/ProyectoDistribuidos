# Usa una imagen base de RabbitMQ
FROM rabbitmq:latest

# Exponer el puerto predeterminado de RabbitMQ (5672)
EXPOSE 5672

# Opcional: si necesitas configurar plugins o usuarios en RabbitMQ, puedes hacerlo aquí
# COPY rabbitmq.conf /etc/rabbitmq/
# COPY rabbitmq-plugins /etc/rabbitmq/

# Comando de inicio para iniciar RabbitMQ cuando se ejecute el contenedor
CMD ["rabbitmq-server"]
