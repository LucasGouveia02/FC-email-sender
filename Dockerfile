FROM eclipse-temurin:21-jdk

# Instale o Redis e o supervisord
RUN apt-get update && \
    apt-get install -y redis-server supervisor && \
    rm -rf /var/lib/apt/lists/*

# Defina o diretório de trabalho
WORKDIR /app

# Copie os arquivos da aplicação
COPY . .

# Compile a aplicação
RUN ./mvnw clean package -DskipTests

# Copie o JAR da aplicação para o container
COPY target/email-sender-0.0.1-SNAPSHOT.jar /app/email-sender.jar

# Crie diretórios necessários para o supervisor e o Redis
RUN mkdir -p /etc/supervisor/conf.d

# Adicione a configuração do supervisor
COPY supervisord.conf /etc/supervisor/supervisord.conf

# Exponha as portas necessárias
EXPOSE 6379 8080

# Defina o comando para iniciar o supervisor
CMD ["/usr/bin/supervisord"]
