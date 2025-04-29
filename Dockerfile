FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE ${PORT}

# Copie o JAR para o container
COPY target/email-sender-0.0.1-SNAPSHOT.jar /app/email-sender.jar

# Defina o comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "/app/email-sender.jar"]