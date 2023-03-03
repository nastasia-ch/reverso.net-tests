# Проект по автоматизации тестирования поисковой системы для перевода в контексте [Reverso Context](https://context.reverso.net/translation/)

<p align="center">
<img src="media/icons/context-reverso-logo.png">
</p>

## Содержание

- [Технологии и инструменты](#технологии-и-инструменты)
- [Реализованныe проверки](#реализованные-проверки)
- [Запуск тестов из терминала](#запуск-тестов-из-терминала)
- [Запуск тестов в Jenkins](#запуск-тестов-в-jenkins)
- [Отчет о результатах тестирования в Allure Report](#отчет-о-результатах-тестирования-в-Allure-report)
- [Интеграция с Allure TestOps](#интеграция-с-allure-testops)
- [Уведомления в Telegram с использованием бота](#уведомления-в-telegram-с-использованием-бота)
- [Пример запуска теста](#пример-запуска-теста)
     + [в Selenoid](#в-Selenoid)
     + [в Browserstack](#в-Browserstack)

## Технологии и инструменты

<p  align="center">

<img width="5%" title="Java" src="media/icons/java-logo.svg">
<img width="5%" title="IntelliJ IDEA" src="media/icons/IDEA-logo.svg">
<img width="5%" title="Gradle" src="media/icons/gradle-logo.svg">
<img width="5%" title="Junit5" src="media/icons/junit5-logo.svg">
<img width="5%" title="Selenide" src="media/icons/selenide-logo.svg">
<img width="5%" title="Rest-Assured" src="media/icons/rest-assured-logo.svg">
<img width="5%" title="Allure Report" src="media/icons/allure-report-logo.svg">
<img width="5%" title="GitHub" src="media/icons/github-logo.svg">
<img width="5%" title="Jenkins" src="media/icons/jenkins-logo.svg">
<img width="5%" title="Selenoid" src="media/icons/selenoid-logo.svg">
<img width="5%" title="Android" src="media/icons/android-logo.svg">
<img width="5%" title="Android Studio" src="media/icons/android-studio-logo.svg">
<img width="5%" title="Appium" src="media/icons/appium-server-logo.svg">
<img width="5%" title="Browserstack" src="media/icons/browserstack-logo.svg">
<img width="5%" title="Allure TestOps" src="media/icons/allure-testops-logo.svg">
<img width="5%" title="Telegram" src="media/icons/telegram-logo.svg">
</p>

## <a name="Тест кейсы"></a> Тест кейсы
- ### WEB
<p align="center">
<img title="Web" src="media/screenshots/Test_cases_UI.png">
</p>

- ### API
<p align="center">
<img title="Api" src="media/screenshots/Test_cases_API.png">
</p>

- ### MOBILE
<p align="center">
<img title="Mobile" src="media/screenshots/Test_cases_mobile.png">
</p>

## Команда Gradle для запуска тестов

Для запуска локально и в Jenkins используется следующая команда:
```bash
gradle clean <TASK>
-DenvironmentWeb=<ENVIRONMENT_WEB>
-DenvironmentMobile=<ENVIRONMENT_MOBILE>
-Dtestdata=<TEST_DATA>
```
## Параметры запуска

<code>TASK</code> – определяет набор тестов для запуска:
>- *test* (_по умолчанию_)
>- *UI_tests*
>- *API_tests*
>- *mobile_tests*
>- *translate_tests*
>- *favourites_tests*
>- *history_tests*
>- *auth_tests*
>- *menu_tests*

<code>ENVIRONMENT_WEB</code> – определяет окружение для запуска веб-тестов:
>- *chrome-local* (_по умолчанию_)
>- *firefox-local*
>- *chrome-v99-selenoid*
>- *chrome-v100-selenoid*
>- *firefox-v97-selenoid*
>- *firefox-v98-selenoid*

<code>ENVIRONMENT_MOBILE</code> – определяет окружение для запуска тестов мобильного приложения:
>- *google-pixel-4-v11.0-emulator* (_по умолчанию_)
>- *google-pixel-4-v11.0-browserstack*
>- *samsung-galaxy-S21-v11.0-browserstack*

<code>TEST_DATA</code> - определяет набор тестовых данных:
>- *data-quality-assurance* (_по умолчанию_)
>- *data-software-testing*

## <img width="4%" title="Jenkins" src="media/logo/Jenkins.svg"> [Jenkins](https://jenkins.autotests.cloud/job/016-anastasia_chernega-context_reverso.net_tests/)
Для запуска сборки необходимо перейти в раздел <code>Собрать с параметрами</code>, выбрать параметры сборки и нажать кнопку <code>Собрать</code>.

<p align="center">
  <img src="media/screen/start_jenkins.png" alt="Jenkins" width="800">
</p>

После выполнения сборки, в блоке <code>История сборок</code> напротив номера сборки появятся
значки:
>- <img width="2%" align="center" title="Allure Report" src="media/icons/allure-report-logo.svg"> <code>Allure Report</code>
>- <img width="2%" align="center" title="Allure TestOps" src="media/icons/allure-testops-logo.svg"> <code>Allure TestOps</code>
<p> Нажав на них можно посмотреть результаты сборки.</p>

## <img width="4%" title="Allure Report" src="media/logo/Allure_Report.svg"> Отчет о результатах тестирования в [Allure Report]()

#### Главная страница Allure Report

<p align="center">
  <img src="media/screen/allure_main.PNG" alt="allure_main" width="800">
</p>

#### Тесты

<p align="center">
  <img src="media/screen/allure-tests.PNG" alt="allure_tests" width="800">
</p>

#### Графики

<p align="center">
  <img src="media/screen/allure_graphs.PNG" alt="allure_graphs" width="800">
</p>

## <img width="4%" title="Allure TestOPS" src="media/logo/Allure_TO.svg"> Интеграция с [Allure TestOps]()

В <code><strong>*Allure TestOps*</strong></code> есть возможность наблюдать за выполнением тестов в реальном времени.

#### Ход выполнения теста

<p align="center">
  <img src="media/screen/testops_launches.png" alt="testops_launches" width="800">
</p>

#### Тест-кейсы

<p align="center">
  <img src="media/screen/testops_tests.PNG" alt="testops_tests" width="800">
</p>

#### Дашборды

<p align="center">
  <img src="media/screen/dashboards.PNG" alt="dashboards" width="800">
</p>

## <img width="4%" title="Telegram" src="media/logo/Telegram.svg"> Уведомления в Telegram
После завершения сборки специальный бот, созданный в <code>Telegram</code>, автоматически обрабатывает и отправляет сообщение с отчетом о прогоне тестов.

<p align="center">
<img title="Telegram Notifications" src="media/screen/notifications.png">

## Видео с прогоном тестов

В отчетах Allure для каждого теста прикреплен не только скриншот, но и видео прохождения теста

#### Видео прогона веб-теста в Selenoid
  
<p align="center">
  <img title="Video" src="media/video/Lamoda_tests.gif">
</p>

#### Видео прогона теста в мобильном приложении в Browserstack
  
<p align="center">
  <img title="Video" src="media/video/Lamoda_tests.gif">
</p>
