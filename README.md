# Множество на основе односвязного списка

[![Build Status](https://travis-ci.com/ITMO-MPP-2018/linked-list-set-<your_GitHub_account>.svg?token=B2yLGFz6qwxKVjbLm9Ak&branch=master)](https://travis-ci.com/ITMO-MPP-2018/linked-list-set-<your_GitHub_account>)

## Описание
Проект включает в себя следующие исходные файлы:

* `Set.java` содержит интерфейс множества.
* `SetImpl.java` содержит реализацию множества на основе односвязного списка для однопоточного случая. Данная реализация небезопасна для использования из нескольких потоков одновременно.
* `pom.xml` содержит описание проекта для системы сборки Maven.

## Задание
Необходимо доработать реализую `SetImpl` так, чтобы она стала безопасной для использования из множества потоков одновременно. Используйте неблокирующую синхронизацию для всех операций. 

## Сборка и тестирование
Для тестирования используйте команду `mvn test`. При этом автоматически будут запущены следующие тесты:

* `FunctionalTest.java` проверяет базовую корректность множества.
* `LinearizabilityTest.java` проверяет реализацию множества на корректность в многопоточной среде.

Обратите внимание, что тесты не покрывают все возможные ошибки синхронизации, поэтому прохождение тестов не означает корректность реализации.

## Формат сдачи

Выполняйте задание в этом репозитории. По готовности добавьте "+" в таблицу с оценками в столбец "Готово" текущего задания. 

В случае необходимости доработки домашнего задания после проверки, "+" в таблице замененяется на "?" и создается issue на GitHub-е. Как только необходимые исправления произведены, заменяйте "?" обратно на "+" и закрывайте issue. После этого задание будет проверено ещё раз.

Перед сдачей задания замените `<your_GitHub_account>` в начале данного файла на свой логин в GitHub для получения информации о сборке в Travis. Это нужно сделать в двух местах: ссылка на картинку и на билд в Travis-е.