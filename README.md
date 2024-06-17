# clients-api

API desenvolvida com o intuito de lidar com informações referentes a clientes, bem como sua lista de produtos favoritos.

## Pré-requisitos

Para rodar este projeto é recomendado o uso das seguintes ferramentas:

- [Docker](https://www.docker.com/products/docker-desktop/)
- [Intellij](https://www.jetbrains.com/idea/download)
- [Postman](https://www.postman.com/downloads/)

## Inicializando container docker

O banco de dados utilizado para este projeto foi o **MySQL** na versão 8.0.

- Com o terminal na raiz do projeto execute o comando abaixo:

```
$ docker-compose up -d
```

Com o comando acima, temos nosso container do mysql rodando.

## Intellij

Este projeto utiliza as seguintes tecnologias:

- Java 17
- SpringBoot 3.1.1
- Maven 3.9.6

**Observação:**

- Antes de iniciar o projeto, execute o passo anterior **Inicializando Docker**.
- Certifique-se de que as tecnologias citadas acima estejam instaladas em sua máquina.
- As tabelas referentes ao projeto serão criadas automaticamente.
- Lembre-se de configurar seu Intellij para a versão 17 do sdk do java.

## Postman

Abaixo está um tutorial de como importar uma collection no postman:

- Coleção com as requisições está na pasta **docs** na raiz do projeto.

[Postman - Como usar e importar uma Collection](https://suporte.agoraos.com.br/hc/pt-br/articles/5671239767579-Postman-Como-usar-e-importar-uma-Collection)

## Execução dos teste

Para rodar os testes, utilize a própria IDE (Intellij), seguindo os seguintes passos:

- Antes de rodar os testes, certifique-se de estar com o docker instalado.
- Abra o diretório **src\test\java\com\luizalabs\api\clients\usecase\impl**.
- Com o diretório aberto, clique em um dos casos de teste com o botão direito do mouse.
- Procure pela opção **More / Run Debug** ou **Mais / Rodar Debug**.
- Selecione a opção **Run 'usecase' with Coverage** ou **Rodar 'usecase' com Cobertura**.

## Ambiente

Para rodar o projeto é preciso configurar as variáveis de ambiente do projeto:

| Variável                   | Valor                                                               |
|----------------------------|---------------------------------------------------------------------|
| APPLICATION_ENV            | development                                                         |
|                            |
| FEIGN_CHALLENGEAPI_NAME    | challenge-api                                                       |
| FEIGN_CHALLENGEAPI_URL     | http://challenge-api.luizalabs.com/api                              |
|                            |
| SERVER_PORT                | 8080                                                                |
|                            |                                                                     |
| SPRING_DATASOURCE_NAME     | db_clients                                                          |
| SPRING_DATASOURCE_PASSWORD | password_clients                                                    |
| SPRING_DATASOURCE_URL      | jdbc:mysql://localhost:3306/db_clients?allowPublicKeyRetrieval=true |
| SPRING_DATASOURCE_USERNAME | user_clients                                                        |
|                            |                                                                     |
| SPRING_FLYWAY_PASSWORD     | password_clients                                                    |
| SPRING_FLYWAY_URL          | jdbc:mysql://localhost:3306/db_clients                              |
| SPRING_FLYWAY_USERNAME     | user_clients                                                        |

Caso esteja utilizando o Intellij (recomendado), utilize as variáveis abaixo:

```
APPLICATION_ENV=development;FEIGN_CHALLENGEAPI_NAME=challenge-api;FEIGN_CHALLENGEAPI_URL=http://challenge-api.luizalabs.com/api;SERVER_PORT=8080;SPRING_DATASOURCE_NAME=db_clients;SPRING_DATASOURCE_PASSWORD=password_clients;SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/db_clients?allowPublicKeyRetrieval=true;SPRING_DATASOURCE_USERNAME=user_clients;SPRING_FLYWAY_PASSWORD=password_clients;SPRING_FLYWAY_URL=jdbc:mysql://localhost:3306/db_clients;SPRING_FLYWAY_USERNAME=user_clients;
```

## Execução do projeto

Para rodar o projeto, utilize a própria IDE (Intellij), seguindo os seguintes passos:

**OBS:** Execute o passo **Ambiente** antes de iniciar o projeto.

- Abra o diretório **src\main\java\com\luizalabs\api\clients**.
- Com o diretório aberto, clique com o botão direito do mouse em cima de **ClientsApplication**.
- Selecione a opção **Run ClientsApplication.main()**.