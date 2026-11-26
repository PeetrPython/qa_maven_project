# QA Automation: UI и API автотесты

UI-тесты проверяют оформление заказа в SauceDemo.
API-тесты проверяют работу с постами и комментариями в JSONPlaceholder.

## Стек
Java 17, Gradle Wrapper (Maven сохранён), JUnit 5, Selenide, Datafaker, Logback, Allure, REST Assured, JSON Schema

## Требования
JDK 17. Основной запуск — через Gradle Wrapper. Для локальных UI-тестов нужен Chrome. При использовании Selenium Grid браузер работает на удалённом узле, например в контейнере. Для API-тестов нужен доступ к JSONPlaceholder, для UI — к SauceDemo.

## Команды Gradle

Все команды выполняются из корня проекта. Gradle Wrapper закрепляет версию 8.14.8; отдельная установка Gradle не требуется. При первом запуске нужны интернет и загрузка дистрибутива и зависимостей. На Windows используйте `gradlew.bat` вместо `./gradlew`.

```bash
# Проверить версию Gradle и используемую Java
./gradlew --version

# Скомпилировать код и тесты без их выполнения
./gradlew testClasses

# Очистить build и запустить все тесты без окна браузера
./gradlew clean test -Dselenide.headless=true

# Запустить unit- и учебные тесты
./gradlew test --tests 'tests.unit.*' --tests 'learning.*'

# Запустить API-тесты
./gradlew test --tests 'tests.api.*'

# Запустить UI-тесты без окна браузера
./gradlew test --tests 'tests.ui.*' -Dselenide.headless=true

# Запустить один метод теста
./gradlew test --tests 'tests.api.PostsApiTest.deletesPostById'
```

Для повторного выполнения актуальных задач добавьте `--rerun-tasks`. Статус `UP-TO-DATE` не означает новый запуск тестов.

## Отчёты

```bash
# Сгенерировать HTML-отчёт Allure по сохранённым результатам
./gradlew allureReport

# Открыть Allure через локальный сервер; остановка — Ctrl+C
./gradlew allureServe
```

Эти команды Allure сами по себе не запускают тесты. Результаты нескольких запусков могут накапливаться; для отчёта одного свежего запуска сначала выполните `./gradlew clean test -Dselenide.headless=true`. `clean` удаляет весь `build/`, включая отчёты и скриншоты.

- XML-результаты Gradle: `build/test-results/test/`.
- HTML-отчёт Gradle: `build/reports/tests/test/index.html`.
- Исходные результаты Allureg: `build/allure-results/`.
- HTML-отчёт Allure: `build/reports/allure-report/allureReport/`.
- Скриншоты UI: `build/screenshots/`.

## Maven как альтернативный вариант

`pom.xml` сохранён. Для Maven требуется установленный Maven; его кеш удалять для работы Gradle не нужно.

```bash
# Запустить все тесты через Maven без окна браузера
mvn clean -Dselenide.headless=true test

# Запустить API-тесты через Maven
mvn -Dtest=PostsApiTest test

# Открыть отчёт по результатам Maven
mvn allure:serve
```

Maven сохраняет тестовые результаты в `target/surefire-reports/`, результаты Allure — в `target/allure-results/`. Не смешивайте их с результатами Gradle.

## Окружения и удалённый браузер

`Config.load()` выбирает файл UI-настроек через `-Denv`; по умолчанию используется `dev`. SauceDemo — один сайт, поэтому dev/stage здесь демонстрируют механизм конфигурации, а не отдельные стенды. URL API задаётся в `PostsApiTest`.

```bash
# Запустить LoginTest с конфигурацией stage
./gradlew test --tests 'tests.ui.LoginTest' -Denv=stage -Dselenide.headless=true

# Пример запуска через уже работающий Selenium Grid по локальному адресу
./gradlew test --tests 'tests.ui.*' -Dselenide.remote=http://localhost:4444/wd/hub
```

Удалённый сервер нужно запустить отдельно. Moon/Kubernetes пока не настроены.

## CI

Workflow `.github/workflows/api-tests.yml` запускает три отдельных задания Gradle: unit/учебные, API и UI. Задания сохраняют XML, HTML-отчёты Gradle и исходные результаты Allure; UI-задание также сохраняет PNG-скриншоты. Успешность конкретного запуска подтверждается его логами и артефактами GitHub Actions.

В папке src/test/resources хранятся файлы:
- config-dev.properties - данные для авторизации и получения ссылки на сайт на контуре dev
- config-stage.properties - данные для авторизации и получения ссылки на сайт на контуре stage
- customers.csv - валидные данные для заполнения формы на сайте
- products.csv - данные продуктов с сайта
- create-post.json - тело запроса на создание поста
- create-post-response.json — схема ответа

## Реализованные тестовые сценарии
Тесты:
- Успешная авторизация
- Получение ошибки при авторизации
- Проверка работы сортировки на главной странице
- Отображение выбранного товара в корзине
- Появление ошибки на форме оформления, если пустое имя, фамилия или индекс
- Отображение суммы заказа при добавлении нескольких товаров
- Отображение заказа на странице оформления
- Успешное оформление заказа
- unit тесты: валидация моделей, чтение CSV, настройки и окружения, поиск данных, генератор данных
- API: получение поста по ID, ответ 404 для отсутствующего поста, фильтрация комментариев, создание, обновление и удаление поста. Для ответа на создание проверяются значения полей и соответствие JSON Schema. JSONPlaceholder имитирует операции записи.


## Структура проекта
- src/test/java/exceptions - кастомные исключения
- src/test/java/models - модели данных
- src/test/java/pages - описание методов страниц
- src/test/java/tests - ui, api, unit тесты
- src/test/java/utils  -  утилиты для проекта 
- src/test/resources - файлы с данными 
- src/main/java/learning - учебные файлы 1-2 этапа
- src/test/java/learning - учебные тесты 1-2 этапа
