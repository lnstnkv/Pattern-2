# Запуск посредством **Docker**

1) Открываем командную строку в текущей директории и выполняем:
    ```
    docker compose up
    ```
2) [Swagger](http://localhost:8081/swagger-ui/index.html#/) для сервиса пользователей.
3) [Swagger](http://localhost:8082/swagger-ui/index.html#/) сервиса кредитования.

Установка keycloak
```
docker run -p 8181:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.0.2 start-dev
```
Для получения токена
```
http://localhost:8181/realms/bank-application-realm/protocol/openid-connect/token
```
Для logout
```
http://localhost:8181/realms/bank-application-realm/protocol/openid-connect/logout
```