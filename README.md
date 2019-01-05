# microservices-cliente-app-sandbox
exemplo de arquitetura de micro serviços. 

## Componentes
- `eureka-service` - The Eureka service which is the Service Registry
- `zuul-service` - This is the Gateway/Edge Service which is registered with Eureka and routes the requests to Client and Server using Eureka Service.
- `db-cliente-server` - Server que vai entregar dados para os clients.
- `cliente-client` - Service que busca dados do Server via Service Discovery do Service Registry (`eureka-service`).
- `cliente-entity` - Entity como dependencia para padronizar os DTOs
- `cliente-ui` -  Camanda visual feita em angular


## Cliente-ui
via angular-CLI
`ng serve` - para rodar aplicação

## MySql
Configurar as properties `application.properties` para rodar com mysql (ou outro banco, basta configurar). 
