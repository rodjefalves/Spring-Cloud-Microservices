# Aplicação Spring Cloud na arquitetura de microsserviços.

## Sobre:

Trata-se de um sistema dividido em seis microsserviços:

  - **Serviço de autentivação:** responsável pela autorização e autenticação dos usuários;
  - **Serviço CRUD:** responsável por toda operação de cadastro, leitura, atualização e exclusão de produtos;
  - **Serviço de pagamento:** responsável pelo controle de vendas, pagamentos, geração de relatórios e atua como Producer do message broker RabbitMQ;
  - **Serviço de estoque:** atua como Consumer do message broker RabbitMQ atualizando estoque;
  - **Serviço de registro:** responsável pelo registro dos microsserviços utilizando Netflix Eureka;
  - **Serviço de Gateway:** atua no roteamento dinâmico utilizando Netflix Zuul.
 

## Arquitetura:

![Arquitetura do sistema](https://raw.githubusercontent.com/rodjefalves/Spring-Cloud-Microservices/main/img/arq1.png)
Fonte: autoria própria.

## Tecnologias utilizadas:

  - Spring Cloud;
  - Netflix Zuul (Intelligent Routing - Gateway Service);
  - Netflix Eureka (Discovery Service);
  - OpenFeing;
  - Spring Security (JWT);
  - Swagger;
  - PostgreSQL;
  - RabbitMQ (Message Broker).
  
## API's:

![API's do sistema](https://raw.githubusercontent.com/rodjefalves/Spring-Cloud-Microservices/main/img/rest.png)
