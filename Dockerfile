FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

# Copia o script para o container
COPY wait-for-redis.sh /wait-for-redis.sh

# Torna o script executável
RUN chmod +x /wait-for-redis.sh

# Modifica o comando de inicialização para usar o script
CMD ["/wait-for-redis.sh"]

RUN ./mvnw clean package -DskipTests

EXPOSE ${PORT}

# Copie o JAR para o container
COPY target/email-sender-0.0.1-SNAPSHOT.jar /app/email-sender.jar

# Defina o comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "/app/email-sender.jar"]