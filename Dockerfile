# Imagem base com Java 21
FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo customer-service.jar gerado pelo Maven
COPY target/customer-service.jar app.jar

# Porta utilizada pela aplicação Spring Boot
EXPOSE 8080

# Comando executado quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]