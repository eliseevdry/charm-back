# Charm Dating App

## Как запустить проект?

1) Устанавливаем JDK-21;
2) Скачиваем postgres (либо postgres в docker), запускаем, выполняем скрипт `/resources/sql/init.sql`;
3) Прописываем логин (`postgres`), пароль (`postgres`), порт (`5432`), репозиторий (`charm-repository`) в `back/pom.xml:16`;
4) Скачиваем и запускаем Redis (либо Redis в docker);
5) Прописываем настройки для Redis (host, port) в `back/pom.xml:16`;
6) Запустите `./mvnw clean package` или для windows `./mvnw.cmd clean package`;
7) Запустите `java -jar back/target/back-1.0-SNAPSHOT-jar-with-dependencies.jar`
   или в Intellij Idea запустите класс `./back/src/main/java/ru/eliseev/charm/back/CharmBackApplication.java`;
8) Остановите приложение `control + c`.

Далее планируется перейти на docker-compose

#### docker:

`docker run -p 5432:5432 --name some-postgres -e POSTGRES_PASSWORD=postgres -d postgres`
`docker run -p 6379:6379 -it redis/redis-stack:latest`