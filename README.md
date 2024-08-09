# Task Management System

Этот проект представляет собой систему управления задачами, разработанную с использованием Java 17+, Spring Boot и PostgreSQL. Приложение поддерживает создание, редактирование, удаление и просмотр задач, а также их назначение пользователям и добавление комментариев к задачам. В системе реализована аутентификация и авторизация с использованием JWT.

## Функциональные возможности

- Аутентификация и авторизация пользователей с использованием JWT.
- Управление задачами (создание, редактирование, удаление, просмотр).
- Возможность оставлять комментарии к задачам.
- Фильтрация и пагинация списка задач.
- Поддержка ролей пользователей ("USER" и "ADMIN").
- Возможность назначения исполнителей для задач.
- Просмотр задач других пользователей для роли "ADMIN".

## Технологии

- Java 17+
- Spring Boot
- Spring Security (с JWT)
- PostgreSQL
- Hibernate/JPA
- Docker и Docker Compose
- Maven

## Требования

- Docker 20.10.0+
- Docker Compose 1.27.0+
- JDK 17+
- Maven 3.8.1+

## Локальный запуск проекта

### 1. Клонирование репозитория

Клонируйте репозиторий на вашу локальную машину:

git clone https://github.com/RasimAlimgulov/TaskManagmentSystem.git
cd task-management-system

### 2. Используйте Docker для сборки и запуска контейнеров:
docker-compose up --build

Приложение будет доступно по адресу:
http://localhost:8080

Swagger UI доступен по следующему адресу для тестирования API:
http://localhost:8080/swagger-ui.html

Для выполнения базовых тестов выполните следующую команду:
 -        mvn test

Полная документация API доступна через Swagger UI по адресу:
http://localhost:8080/swagger-ui.html

Автор: https://github.com/RasimAlimgulov


