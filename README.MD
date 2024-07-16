# Getting Started

## Клиент:

https://github.com/2immons/ps-parser-v2-client

# Админка

Логин: adminadmin
Пароль: rootroot

Для тестирования запуска процессов нужно регать новых пользователей и тестировать под ними


# Запросы

## Добавление нового пользователя

POST localhost:8085/user/register

{
"userName": "chak3",
"email": "testtest",
"password": "chak3",
"role": "ADMIN"
}

Запрос открытый и не требует авторизации

## Запуск нового процесса

POST localhost:8085/process/start

{
"provider": "MEGA_MARKET",
"category": "TECH",
"bonusPerc": 0,
"pageLimit": 5,
"minProductPrice": 0,
"maxProductPrice": 100000000000
}

Надо быть авторизованым для запуска (токен отправить)

## Получение всех процессов пользователя

GET localhost:8085/process/getMyProcesses

Надо быть авторизованым для получения (токен отправить)
