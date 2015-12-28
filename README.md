# vote-no-restaurante

# Tecnologias:
- Spring boot
- Spring Data
- Hsqldb
- Log4j
- Google Guava
- Lombok
- Apache commons lang
- JUnit
- Mockito
- Fixture Factory
- AngularJS
- Bower
- Node.js
- NPM
- Maven
- Java 8
- Swagger

# How to use
- Para rodar o projeto é necessário ter o Java 8 e Mave instalados. Feito isso, vá até a raiz do projeto onde o pom.xml se encontra e execute o seguinte comando: mvn clean package spring-boot:run. Ok, agora a API está no ar. Para rodar o client do projeto, por favor, siga as instruções no link abaixo:

https://github.com/viniciusluisr/vote-no-restaurante-app

- O projeto também está disponível em: https://vote-no-restaurante-app.herokuapp.com

#ENDPOINTS:
- Ranking API:
- [GET] http://127.0.0.1:7000/v1/rankings/{userId} - Consultar os rankings de um usuário específico;
- [GET] http://127.0.0.1:7000/v1/rankings - Consulta o ranking geral de restaurantes

- Restaurant API
- [GET] http://127.0.0.1:7000/v1/restaurants - Consulta todos os restaurantes cadastrados

- User API
- [POST] http://127.0.0.1:7000/v1/users - Cadastra um novo usuário com os seus respectivos votos
- [GET] http://127.0.0.1:7000/v1/users/{email} - Consulta um usuário existente a partir de um email

- Voting API
- [GET] http://127.0.0.1:7000/v1/votings/{restaurantId} - Realiza um voto escolhendo o restaurante a partir do Id
- [GET] http://127.0.0.1:7000/v1/votings - Inicia uma votação com os dois restaurantes iniciais

# SWAGGER
Para utilizar a api com o Swagger basta acessar:
- http://localhost:7000/api-docs para a documentação básica da API
- http://localhost:7000/dist/index.html para utilizar a API
- Também disponível em: https://vote-no-restaurante-apis.herokuapp.com/dist/index.html



