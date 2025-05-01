#!/bin/bash

# Espera o Redis ficar disponível
until nc -z -v -w30 redis 6379
do
  echo "Esperando Redis..."
  sleep 1
done

echo "Redis está disponível! Iniciando o serviço de e-mail..."

# Agora, inicie sua aplicação (ajuste para o comando correto)
exec java -jar /app/email-sender.jar
