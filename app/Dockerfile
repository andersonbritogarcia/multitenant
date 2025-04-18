FROM eclipse-temurin:21-jre

WORKDIR /app

# Criar um usuário não root
RUN useradd -ms /bin/bash appuser

# Copiar o arquivo JAR da aplicação para o contêiner
COPY target/app-0.0.1.jar /app/app.jar

# Alterar o proprietário do arquivo para o usuário não root
RUN chown -R appuser:appuser /app

# Trocar para o usuário não root
USER appuser

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]