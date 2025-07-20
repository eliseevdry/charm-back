#!/bin/bash

# Скрипт для сборки Java EE проекта в WAR и развёртывания в Tomcat

# 1. Проверка наличия необходимых инструментов
if ! command -v javac &> /dev/null; then
    echo "Ошибка: JDK не установлен или не добавлен в PATH"
    exit 1
fi

if ! command -v jar &> /dev/null; then
    echo "Ошибка: Утилита jar не найдена (часть JDK)"
    exit 1
fi

# 2. Настройки (измените под ваш проект)
TOMCAT_WEBAPPS="$CATALINA_HOME/webapps" # Путь к webapps Tomcat
TOMCAT_START="$CATALINA_HOME/bin/startup.sh" # Скрипт запуска Tomcat
TOMCAT_STOP="$CATALINA_HOME/bin/shutdown.sh" # Скрипт остановки Tomcat
RESOURCES_DIR="resources"         # Папка с ресурсами
SRC_DIR="src"                     # Папка с исходниками
WEB_DIR="webapp"                  # Папка с веб-контентом (WEB-INF, JSP и т.д.)
LIB_DIR="lib"                     # Папка с зависимостями (JAR-файлы)

DIST_DIR="build"
DIST_WEB_DIR="$DIST_DIR/webapp"              
DIST_CLASSES_DIR="$DIST_WEB_DIR/WEB-INF/classes"       # Папка для скомпилированных классов
DIST_LIB_DIR="$DIST_WEB_DIR/WEB-INF/lib"       # Папка для скомпилированных классов

# 3. Подготовка папок
echo "Подготовка структуры папок..."
find "$DIST_DIR" -mindepth 1 -maxdepth 1 -exec rm -rf {} +
mkdir -p "$DIST_DIR"
mkdir -p "$DIST_CLASSES_DIR"
mkdir -p "$DIST_LIB_DIR"

# 4. Компиляция Java-классов
echo "Компиляция Java-классов..."
find "$SRC_DIR" -name "*.java" > sources.txt
javac -d "$DIST_CLASSES_DIR" -cp "$LIB_DIR/*" @sources.txt
rm sources.txt

# 5. Копирование классов и библиотек
echo "Копирование ресурсов..."
cp -r "$WEB_DIR"/* "$DIST_WEB_DIR"
cp -r "$RESOURCES_DIR"/* "$DIST_CLASSES_DIR"
cp -r "$LIB_DIR"/*.jar "$DIST_LIB_DIR"

# 6. Создание WAR-файла
echo "Создание WAR-архива..."
cd "$DIST_WEB_DIR" || exit
jar -cvf "../ROOT.war" *
cd ..

# 7. Остановка Tomcat перед развёртыванием
echo "Остановка Tomcat..."
if [ -f "$TOMCAT_STOP" ]; then
    "$TOMCAT_STOP"
    sleep 5  # Даём Tomcat время на корректную остановку
    echo "Tomcat остановлен"
else
    echo "Внимание: Скрипт остановки Tomcat не найден по пути $TOMCAT_STOP"
    echo "Продолжение без остановки Tomcat..."
fi

# 8. Очистка папки webapps
echo "Очистка папки webapps..."
if [ -d "$TOMCAT_WEBAPPS" ]; then
    # Удаляем все файлы и папки в webapps, кроме самого каталога
    find "$TOMCAT_WEBAPPS" -mindepth 1 -maxdepth 1 -exec rm -rf {} +
    echo "Содержимое $TOMCAT_WEBAPPS успешно удалено"
else
    echo "Ошибка: Папка Tomcat webapps не найдена по пути $TOMCAT_WEBAPPS"
    exit 1
fi

# 9. Развёртывание в Tomcat
echo "Развёртывание в Tomcat..."
if [ -d "$TOMCAT_WEBAPPS" ]; then
    cp "ROOT.war" "$TOMCAT_WEBAPPS/"
    echo "WAR-файл успешно скопирован в $TOMCAT_WEBAPPS"
else
    echo "Ошибка: Папка Tomcat webapps не найдена по пути $TOMCAT_WEBAPPS"
    echo "WAR-файл доступен в папке $DIST_DIR/ROOT.war"
    exit 1
fi

# 10. Запуск Tomcat
echo "Запуск Tomcat..."
if [ -f "$TOMCAT_START" ]; then
    "$TOMCAT_START"
    echo "Tomcat запущен. Приложение должно быть доступно через несколько секунд."
else
    echo "Ошибка: Скрипт запуска Tomcat не найден по пути $TOMCAT_START"
    echo "Запустите Tomcat вручную для доступа к приложению."
    exit 1
fi

echo "Готово! Приложение развёрнуто и запущено."