# Câmara Transparente BH – API
API RESTful construída com Spring Boot para servir informações para a aplicação [Câmara Transparente – BH](https://github.com/leoaugustov/camara-transparente-bh-frontend). Além de expor informações, também realiza a coleta (web scraping) e processamento dos dados através de uma tarefa agendada.

## Endpoints
A URL base é: http://localhost:8080/api/v1/
### Vereadores
| Endpoint | Método | Descrição |
|--|--|--|
| /vereadores | GET | Lista todos os vereadores |
| /vereadores/:uuid/foto | GET | Retorna a foto do vereador referente ao `uuid` informado |
### Status Scrap
| Endpoint | Método | Descrição |
|--|--|--|
| /status-scrap | GET | Retorna a data do último scrap |
### Custeio Parlamentar
| Endpoint | Método | Descrição |
|--|--|--|
| /custeio-parlamentar/por-partido | GET | Retorna os valores de custeio agrupados por partido |
## Para instalar e rodar o projeto na sua máquina
Os pré-requisitos para rodar o projeto são ter um ambiente básico de desenvolvimento Java configurado, um servidor MySQL rodando e o Maven instalado.
### Clonando o repositório
    $ git clone https://github.com/leoaugustov/camara-transparente-bh-backend.git
    $ cd camara-transparente-bh-backend
### Preparando o banco de dados e rodando o projeto
Crie um banco de dados com o nome `camara_transparente`.
Altere as credenciais do banco de dados, caso necessário, no arquivo [application-dev.properties](https://github.com/leoaugustov/camara-transparente-bh-backend/blob/master/src/main/resources/application-dev.properties).
### Na pasta do projeto execute:

    $ mvn spring-boot:run
### Populando o banco de dados
Execute o script SQL [dados-visualizacao.sql](https://github.com/leoaugustov/camara-transparente-bh-backend/blob/master/dados-visualizacao.sql).
> Esses dados são apenas para visualização e estão desatualizados. Para realizar o scrap configure a tarefa agendada na classe [ServicoScrapAgendado](https://github.com/leoaugustov/camara-transparente-bh-backend/blob/master/src/main/java/camaratransparente/servico/ServicoScrapAgendado.java).