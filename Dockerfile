# Usar una imagen oficial de OpenJDK como base
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado por Gradle/Maven
COPY build/libs/*.jar ms-clients.jar

# Exponer el puerto en el que correrá la app
EXPOSE 8080

# Definir el comando de inicio de la aplicación
CMD ["java", "-jar", "ms-clients.jar"]