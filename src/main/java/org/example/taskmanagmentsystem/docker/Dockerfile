# Указываем базовый образ OpenJDK 17
FROM openjdk:17
# Определяем аргумент сборки для имени JAR файла
ARG APP_JAR=app.jar
# Копируем JAR файл в контейнер
COPY ${APP_JAR} app.jar
#Определяем команду запуска JAR файла
ENTRYPOINT ["java", "-jar", "app.jar"]
# Указываем, что контейнер прослушивает порт 8080
EXPOSE 8080
