# Charm Dating App

### Локальный запуск:
1) Запускаем postgres (local или docker), скачиваем Tomcat Server;
2) Прописываем логин (`postgres`), пароль (`postgres`), порт (`5432`), репозиторий (`charm-repository`) в `application.properties`;
3) Меняем путь к папке 'downloads' в `application.properties` (windows: `C:/Users/Andrey_Eliseev/Downloads`, linux: `/Users/andrey.s.eliseev/Downloads`);
4) Собираем war-архив либо в ручную (см. курс), либо с помощью IDE (варианты запуска с Intellij Idea показаны ниже) и помещаем в Apache Tomcat Server.

Далее планируется перейти на docker-compose

### Запуск с Intellij Idea Community:
1) Скачиваем plugin `Smart Tomcat`;
2) Добавляем конфигурацию запуска `Smart Tomcat`;
3) Ставим `/` в `Context Path`, в `Deployment directory` указываем директорию `web` или `webapp`;
4) Если не находит classpath в `Extra JVM classpath` указываем сбилженную директорию `out`.

### Запуск с Intellij Idea Ultimate:
1) Переходим в настройки -> `Project Structure...`;
2) Создаем модуль `web`;
3) Проверяем чтобы везде в модуле был путь до директории `web` или `webapp` в корне проекта;
4) `WEB-INF`, `img` и `favicon.ico` должны находиться в директории `web` или `webapp`;
5) Создаем артифакт `Web Application: Exploded` из этого модуля;
6) Добавляем конфигурацию запуска `Tomcat Server: Local`;
7) В `Deployment` ставим `/` в `Application Context` и добавляем наш артифакт, помимо `build`.

#### docker:
`docker run -p 5432:5432 --name some-postgres -e POSTGRES_PASSWORD=postgres -d postgres`