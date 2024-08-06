# Используем официальный образ OpenJDK 17
FROM openjdk:22-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app


# Копируем JAR файл в образ
COPY target/translateT-0.0.1-SNAPSHOT.jar app.jar


# Открываем порт, на котором будет работать приложение
EXPOSE 8080

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]