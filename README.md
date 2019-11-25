[![Build Status](http://jovanibrasil.com:8085/buildStatus/icon?job=notes-api)](http://13.58.51.172:8085/job/notes-api/)

# API para um aplicativo de notas/lembretes

Esta API permite operações básicas para uma aplicação de lembretes/notes. Operações como criar, atualizar, deletar, e buscar lembretes estão disponíveis nesta API. O aplicativo web [notes.jovanibrasil.com](https://notes.jovanibrasil.com) (limitações dos recursos de hardware do servidor podem tornar o blog lento ou indisponível) é um exemplo da utilização desta API.

## Executando

É possível subir a API em um servidor Tomcat em um container Docker. Através de comandos que estão no 
arquivo Makefile podemos executar tarefas básicas para controle do container. Dentre os comandos 
existentes no arquivo temos comandos para rodar o docker-compose, onde temos o ambiente completo para
rodar a API, e também para rodar a API isoladamente.

Para rodar o docker-compose é necessário que já tenha sido feito build da imagem da API de autorização, 
assim como da imagem do MongoDb. 

## Documentação

Em breve será apresentada a documentação gerada com Swagger.

## Licença

Em breve será definido qual tipo de licença será utilizada. 



