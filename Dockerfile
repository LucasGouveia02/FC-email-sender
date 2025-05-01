FROM eclipse-temurin:21-jdk

WORKDIR /app

# Instala o netcat
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

# Copia apenas o necessário inicialmente para cache eficiente
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Baixa as dependências primeiro (melhora cache)
RUN ./mvnw dependency:go-offline -B

# Agora copia o restante do código
COPY src ./src
COPY wait-for-redis.sh /wait-for-redis.sh

# Torna o script executável
RUN chmod +x /wait-for-redis.sh

# Constrói o projeto
RUN ./mvnw clean package -DskipTests

# Copie o JAR gerado para a imagem final
RUN cp target/email-sender-0.0.1-SNAPSHOT.jar /app/email-sender.jar

# Exponha a porta explicitamente (sem usar variável)
EXPOSE 8080

# Comando de inicialização que aguarda o Redis
ENTRYPOINT ["/wait-for-redis.sh"]
