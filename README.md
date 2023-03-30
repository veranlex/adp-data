# adp-data
## Локальный запуск

- запускаем докер компоуз  [ibm-mq](ibm-mq/docker-compose.yml)
- список очередей которые создаются можно глянуть тут  [config-mq](ibm-mq/20-config.mqsc). 
- Доступ к веб интерфейсу IBM MQ тут https://localhost:9442/ibmmq/console/#/

## запуск конкретных очередей
Пример 
http://localhost:8080/switcher?queues=COMMONDATA.OUT,ROSTERS.OUT