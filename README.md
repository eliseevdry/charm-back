# Charm Dating App

## Как запустить проект?

1) Устанавливаем JDK-21;
2) Скачиваем postgres (либо postgres в docker), запускаем, выполняем скрипт `/resources/sql/init.sql`;
3) Прописываем логин (`postgres`), пароль (`postgres`), порт (`5432`), репозиторий (`charm-repository`) в `application.properties`;
4) Скачиваем и запускаем Redis (либо Redis в docker);
5) Прописываем настройки для Redis (host, port) в `application.properties`;
6) Меняем путь к папке 'downloads' в `application.properties` (windows: `C:/Users/Andrey_Eliseev/Downloads`,
   linux: `/Users/andrey.s.eliseev/Downloads`);
7) Запустите `./mvnw clean package` или для windows `./mvnw.cmd clean package`.

### без Intellij Idea (cargo mvn plugin)

7) Перейдите в директорию back;
8) Запустите `../mvnw cargo:run` или для windows `../mvnw.cmd cargo:run`;
9) Остановите приложение `control + c`.

### без Intellij Idea (свой Tomcat Server)
7) Скачиваем на компьютер Tomcat Server;
8) Перенести war-архив в директорию `/путь/к/tomcat/webapps`;
9) Запустить сервер с помощью `/путь/к/tomcat/bin/startup.sh` или для windows `/путь/к/tomcat/bin/startup.bat`;
10) Остановить сервер с помощью `/путь/к/tomcat/bin/shutdown.sh` или для windows `/путь/к/tomcat/bin/shutdown.bat`.

### Запуск с Intellij Idea Community:
7) Скачиваем plugin `Smart Tomcat`;
8) Добавляем конфигурацию запуска `Smart Tomcat`;
9) Ставим `/` в `Context Path`, в `Deployment directory` указываем директорию `/путь/к/проекту/back/src/main/webapp`;
10) Если не находит classpath в `Extra JVM classpath` указываем сбилженную директорию `target`.

### Запуск с Intellij Idea Ultimate:
7) Переходим в настройки -> `Project Structure...`;
8) Создаем модуль `web`;
9) Проверяем чтобы везде в модуле был путь до директории `/путь/к/проекту/back/src/main/webapp` в корне проекта;
10) Создаем артифакт `Web Application: Exploded` из этого модуля;
11) Добавляем конфигурацию запуска `Tomcat Server: Local`;
12) В `Deployment` ставим `/` в `Application Context` и добавляем наш артифакт, помимо `build`.

Далее планируется перейти на docker-compose

#### docker:
`docker run -p 5432:5432 --name some-postgres -e POSTGRES_PASSWORD=postgres -d postgres`
`docker run -p 6379:6379 -it redis/redis-stack:latest`