
# clients-api

API desenvolvida com o intuito de lidar com informações referentes a clientes, bem como sua lista de produtos favoritos.


## Pré-requisitos

Para rodar este projeto é recomendado o uso das seguintes ferramentas:

- Docker
- Intellij
- Postman

**OBS**: A coleção com as requisições está no diretório **src\main\java\com\luizalabs\api\clients\docs**.


## Inicializando container docker

O banco de dados utilizado para este projeto foi o **MySQL** na versão 8.0.

- Com o terminal na raiz do projeto execute o comando abaixo:
```
$ docker build -t mysql_image .
```

- Após finalizar a execução do comando anterior, execute o seguinte comando:
```
$ docker run -d --name mysql_container -p 3306:3306 mysql_image
```

Com os comando acima, temos nosso container com o mysql já funcionado.

## Intellij

Este projeto foi desenvolvido utilzando a versão 17 do java, aliado a versão 3.1.1 do spring boot.

Lembre-se de configurar seu Intellij para a versão adequada do sdk do java.

**OBS:** Antes de iniciar o projeto, execute o passo anterior **Inicilaizando Docker**.

As tabelas referentes ao projeto serão criadas automáticamente.
## Postman

Abaixo está um tutorial de como importar uma collection no postman:

[Postman - Como usar e importar uma Collection](https://suporte.agoraos.com.br/hc/pt-br/articles/5671239767579-Postman-Como-usar-e-importar-uma-Collection)

