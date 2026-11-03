# NuclearQuest

**Challenge FIAP – AMAZUL**

## Integrantes do grupo

- André Souza Matsuda – RM 566733
- Guilherme Barca – RM 568517
- Guilherme Oliveira – RM 566842
- Kaliel Conceição – RM 567587
- Paulo Henrique Muniz Diedrich – RM 567618

## Sobre o projeto

O NuclearQuest é uma API REST desenvolvida em Java com Spring Boot, voltada para uma plataforma educacional de perguntas e respostas sobre tecnologia nuclear.

Este repositório apresenta a implementação da solução utilizando Docker, Docker Compose e Microsoft Azure, permitindo executar a aplicação e seu banco de dados em containers hospedados em uma máquina virtual na nuvem.

## Como Docker e Cloud Computing são utilizados na solução?

O Docker permite empacotar a aplicação e suas dependências em containers, garantindo maior consistência entre os ambientes de desenvolvimento e execução.

O Docker Compose permite gerenciar os serviços da aplicação, incluindo a API e o banco de dados PostgreSQL, além de configurar a comunicação entre eles, as portas e o armazenamento persistente.

O Cloud Computing permite hospedar esses serviços em uma infraestrutura disponibilizada pela Microsoft Azure, possibilitando o acesso remoto à aplicação pela internet.

## Arquitetura da solução

A solução é composta por dois containers Docker:

- **API:** aplicação Java com Spring Boot, responsável pelas operações e regras de negócio.
- **Banco de dados:** PostgreSQL, responsável pelo armazenamento das informações.

Os containers se comunicam por meio de uma rede Docker criada pelo Docker Compose.

O banco de dados utiliza um volume nomeado (`nuclearquest-data`), garantindo a persistência dos registros mesmo após a remoção e recriação dos containers.

A aplicação está hospedada em uma máquina virtual Ubuntu 24.04 na Microsoft Azure, com acesso externo pela porta 8080.

![Arquitetura da solução](docs/arquitetura.png)

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL 17
- Docker
- Docker Compose
- Microsoft Azure
- Ubuntu 24.04

## Executando com Docker Compose

Na raiz do repositório, execute:

### Construir e iniciar os serviços

```bash
docker compose up -d --build
```

### Verificar os containers

```bash
docker compose ps
```

### Consultar os logs

```bash
docker compose logs -f
```

### Parar os serviços

```bash
docker compose down
```

**Importante:** o comando `docker compose down` não remove o volume de dados. Para preservar os registros do banco, não utilize a opção `-v`.

## Configurações dos serviços

| Serviço | Configuração |
|---|---|
| API | Porta 8080 |
| PostgreSQL | Porta interna 5432 |
| Banco de dados | nuclearquest |
| Usuário do banco | nuclearquest |
| Rede Docker | nuclearquest-network |
| Volume persistente | nuclearquest-data |

As credenciais e configurações de conexão são definidas pelas variáveis de ambiente no arquivo `compose.yaml`.

## Banco de dados e dados de teste

O script SQL com os registros de teste está localizado em:

`NuclearQuest/src/main/resources/data.sql`

Após iniciar os containers, execute os comandos abaixo na raiz do projeto para inserir os dados:

```bash
docker cp NuclearQuest/src/main/resources/data.sql nuclearquest-db:/tmp/data.sql
```

```bash
docker compose exec db psql -U nuclearquest -d nuclearquest -f /tmp/data.sql
```

O script insere registros de categorias, usuários, perguntas, alternativas e respostas de usuários.

## API e operações CRUD

A API disponibiliza operações CRUD para o recurso de perguntas:

| Operação | Método | Endpoint |
|---|---|---|
| Criar pergunta | POST | `/perguntas` |
| Listar perguntas | GET | `/perguntas` |
| Consultar pergunta por ID | GET | `/perguntas/{id}` |
| Atualizar pergunta | PUT | `/perguntas/{id}` |
| Excluir pergunta | DELETE | `/perguntas/{id}` |

A documentação interativa da API é disponibilizada pelo Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

## Provisionamento na Microsoft Azure

A infraestrutura foi provisionada utilizando Azure CLI.

### Configurações da infraestrutura

| Recurso | Configuração |
|---|---|
| Resource Group | rg-nuclearquest |
| Região | Brazil South |
| Máquina virtual | vm-nuclearquest |
| Sistema operacional | Ubuntu 24.04 |
| Tamanho | Standard_D2s_v4 |
| Usuário administrativo | azureuser |
| Porta da aplicação | 8080 |

### Provisionar a infraestrutura

O script de provisionamento está disponível em:

`azure/script.sh`

Para executá-lo em uma assinatura Azure com o Azure CLI configurado:

```bash
chmod +x azure/script.sh
./azure/script.sh
```

### Executar a aplicação na VM Azure

Após acessar a máquina virtual e clonar este repositório, entre na pasta do projeto:

```bash
cd Sprint-Amazul-DevOps
```

Inicie os serviços:

```bash
docker compose up -d --build
```

Insira os dados de teste utilizando os comandos da seção Banco de dados e dados de teste.

A aplicação poderá ser acessada externamente pelo endereço:

```text
http://IP_PUBLICO_DA_VM:8080/swagger-ui/index.html
```

## Persistência dos dados

A persistência é realizada por meio do volume Docker `nuclearquest-data`, associado ao serviço PostgreSQL.

Ao executar `docker compose down` e posteriormente `docker compose up -d`, os containers são recriados, mas os dados permanecem armazenados no volume.

Dessa forma, o banco de dados mantém seus registros mesmo após a recriação dos containers.

## Encerramento da infraestrutura

Após a utilização da infraestrutura na Azure, os recursos podem ser removidos por meio do Azure CLI:

```bash
az group delete --name rg-nuclearquest --yes --no-wait
```

Esse comando remove o Resource Group e os recursos associados.