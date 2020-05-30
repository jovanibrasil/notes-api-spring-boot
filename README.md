[![Build Status](https://travis-ci.org/jovanibrasil/notes-api.svg?branch=develop)](https://travis-ci.org/jovanibrasil/notes-api)
![Codecov branch](https://img.shields.io/codecov/c/github/jovanibrasil/notes-api/develop)

# API para um aplicativo de notas/lembretes

Esta API permite operações básicas para uma aplicação de lembretes/notes.

Para rodar o projeto você deve primeiro subir o MongoDB. Para tanto basta ir ao diretório /mongo no projeto e executar o comando make start. Então basta executa o comando mvn -pl web spring-boot:run na raíz do projeto e a aplicação deve iniciar. Uma vez rodando é possível acessar a documentação Swagger em http://localhost:8082/api/swagger-ui.html.
